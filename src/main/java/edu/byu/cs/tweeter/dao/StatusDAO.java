package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest_Net;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class StatusDAO extends DAO
{
    private static DynamoDBMapper storyMapper;
    private static AmazonSQS postStatusSQS;
    private static AuthTokenDAO authTokenDAO;

    public StatusDAO()
    {
        if (storyMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            storyMapper = new DynamoDBMapper(client);
        }

        if(postStatusSQS == null)
        {
            postStatusSQS = AmazonSQSClientBuilder.standard().withRegion(region).build();
        }

        if(authTokenDAO == null)
        {
            authTokenDAO = new AuthTokenDAO();
        }
    }

    private SendMessageRequest createSQSMessage(PostStatusRequest_Net request, long statusTimeStamp)
    {
        SendMessageRequest postStatusSQSMessage = new SendMessageRequest(postStatusSQSUrl, request.request.statusText);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(request.request.postingUser.alias);
        postStatusSQSMessage.addMessageAttributesEntry(postStatusSQSPosterAliasKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(request.request.postingUser.firstName);
        postStatusSQSMessage.addMessageAttributesEntry(postStatusSQSPosterFirstNameKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(request.request.postingUser.lastName);
        postStatusSQSMessage.addMessageAttributesEntry(postStatusSQSPosterLastNameKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").setStringValue(request.request.postingUser.imageUrl);
        postStatusSQSMessage.addMessageAttributesEntry(postStatusSQSPosterImageUrlKey, messageAttributeValue);

        messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("Number").setStringValue(String.valueOf(statusTimeStamp));
        postStatusSQSMessage.addMessageAttributesEntry(postStatusSQSTimeStampKey, messageAttributeValue);

        return postStatusSQSMessage;
    }

    public PostStatusResponse postStatus(PostStatusRequest_Net request)
    {
        if(request.authToken == null ||
                request.authToken.isEmpty() ||
                request.request == null ||
                request.request.postingUser == null ||
                request.request.statusText == null)
        {
            return new PostStatusResponse(invalidRequestMessage);
        }

        //verify authToken
        if(!authTokenDAO.isValidAuthToken(request.request.postingUser.alias, request.authToken))
        {
            return new PostStatusResponse(invalidAuthTokenMessage);
        }

        //post directly to story
        StoryTableItem newStoryEntry = new StoryTableItem();
        newStoryEntry.setTimeStamp(System.currentTimeMillis());
        newStoryEntry.setAlias(request.request.postingUser.alias);
        newStoryEntry.setFirstName(request.request.postingUser.firstName);
        newStoryEntry.setLastName(request.request.postingUser.lastName);
        newStoryEntry.setImageUrl(request.request.postingUser.imageUrl);
        newStoryEntry.setStatusText(request.request.statusText);

        storyMapper.save(newStoryEntry);

        //post info to SQS Queue
        SendMessageResult postStatusResult = postStatusSQS.sendMessage(createSQSMessage(request, newStoryEntry.getTimeStamp()));

        System.out.println(postStatusResult.toString());

        return new PostStatusResponse();
    }
}
