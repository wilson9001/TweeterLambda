package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import edu.byu.cs.tweeter.dao.DAO;
import edu.byu.cs.tweeter.dao.FollowsTableItem;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.service.FollowersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostStatusSQSReciever extends DAO implements RequestHandler<SQSEvent, Void>
{
    private static AmazonSQS sqs;
    private static DynamoDBMapper followsTableMapper;

    //private static final int maxResults = Integer.MAX_VALUE;

    public PostStatusSQSReciever()
    {
        if (sqs == null)
        {
            sqs = AmazonSQSClientBuilder.standard().withRegion(region).build();
        }

        if(followsTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            followsTableMapper = new DynamoDBMapper(client);
        }
    }

    private SendMessageRequest createSQSMessage(String statusText, List<String> followerAliases, String posterFirstName, String posterLastName, String posterAlias, String posterImageUrl, long timeStamp)
    {
        SendMessageRequest updateFeedSQSMessage = new SendMessageRequest(updateFeedSQSUrl, String.join(updateFeedFollowerAliasSplitCharacter, followerAliases));

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(statusText);
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedStatusTextKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(posterFirstName);
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedPosterFirstNameKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(posterLastName);
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedPosterLastNameKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(posterImageUrl);
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedPosterImageUrlKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(posterAlias);
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedPosterAliasKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("Number").setStringValue(String.valueOf(timeStamp));
        updateFeedSQSMessage.addMessageAttributesEntry(updateFeedTimeStampKey, messageAttributeValue);

        return updateFeedSQSMessage;
    }

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context)
    {
        //For each post status message...
        for (SQSEvent.SQSMessage postStatusMessage : sqsEvent.getRecords())
        {
            System.out.println("Inside SQSMessage loop");

            Map<String, SQSEvent.MessageAttribute> messageAttributeMap = postStatusMessage.getMessageAttributes();

            //get followers
            /*FollowersRequest followersRequest = new FollowersRequest(new User(
                    messageAttributeMap.get(postStatusSQSPosterFirstNameKey).getStringValue(),
                    messageAttributeMap.get(postStatusSQSPosterLastNameKey).getStringValue(),
                    messageAttributeMap.get(postStatusSQSPosterAliasKey).getStringValue().substring(1),
                    messageAttributeMap.get(postStatusSQSPosterImageUrlKey).getStringValue()),
                    maxResults, null);

            System.out.println("Follower request first name: ".concat(followersRequest.followee.firstName));
            System.out.println("Follower request last name: ".concat(followersRequest.followee.lastName));
            System.out.println("Follower request alias: ".concat(followersRequest.followee.alias));
            System.out.println("Follower request imageUrl: ".concat(followersRequest.followee.imageUrl));

            FollowersResponse followersResponse = new FollowersService().getFollowers(followersRequest);*/

            FollowsTableItem followsTableQuery = new FollowsTableItem();
            followsTableQuery.setFolloweeAlias(messageAttributeMap.get(postStatusSQSPosterAliasKey).getStringValue());

            DynamoDBQueryExpression<FollowsTableItem> followersQueryExpression = new DynamoDBQueryExpression<FollowsTableItem>()
                    .withIndexName(followsTableIndexName)
                    .withHashKeyValues(followsTableQuery)
                    .withScanIndexForward(false)
                    .withConsistentRead(false);

            List<String> followerAliases = followsTableMapper.query(FollowsTableItem.class, followersQueryExpression)
                    .stream().map(FollowsTableItem::getFollowerAlias).collect(Collectors.toList());

            //List<User> followers = followersResponse.getFollowers();

            System.out.println(String.format("This user has %d followers", followerAliases.size()));

            //List<String> followerAliases = followers.stream().map(User::getAlias).collect(Collectors.toList());

            int startIndex = 0;
            int endIndex = Math.min(followerAliases.size(), followersPerUpdateFeedMessage);

            while (startIndex != endIndex)
            {
                //extract follower's aliases into list
                List<String> followerChunk = followerAliases.subList(startIndex, endIndex);

                SendMessageRequest updateFeedMessage = createSQSMessage(
                        postStatusMessage.getBody(),
                        followerChunk,
                        messageAttributeMap.get(postStatusSQSPosterFirstNameKey).getStringValue(),
                        messageAttributeMap.get(postStatusSQSPosterLastNameKey).getStringValue(),
                        messageAttributeMap.get(postStatusSQSPosterAliasKey).getStringValue(),
                        messageAttributeMap.get(postStatusSQSPosterImageUrlKey).getStringValue(),
                        Long.parseLong(messageAttributeMap.get(postStatusSQSTimeStampKey).getStringValue()));

                sqs.sendMessage(updateFeedMessage);

                startIndex = endIndex;
                endIndex = Math.min(startIndex + followersPerUpdateFeedMessage, followerAliases.size());
            }

            DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(postStatusSQSUrl, postStatusMessage.getReceiptHandle());
            sqs.deleteMessage(deleteMessageRequest);
        }

        return null;
    }
}
