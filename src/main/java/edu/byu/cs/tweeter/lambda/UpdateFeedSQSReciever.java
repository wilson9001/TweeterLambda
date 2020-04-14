package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import edu.byu.cs.tweeter.dao.DAO;
import edu.byu.cs.tweeter.dao.FeedTableItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateFeedSQSReciever extends DAO implements RequestHandler<SQSEvent, Void>
{
    private static DynamoDBMapper feedTableMapper;
    private static AmazonSQS sqs;
    //private static final int maximumEntriesInBatchWrite = 25;

    public UpdateFeedSQSReciever()
    {
        if(feedTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            feedTableMapper = new DynamoDBMapper(client);
        }

        if (sqs == null)
        {
            sqs = AmazonSQSClientBuilder.standard().withRegion(region).build();
        }
    }

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context)
    {
        for(SQSEvent.SQSMessage updateFeedMessage : sqsEvent.getRecords())
        {
            //unpack message contents
            Map<String, SQSEvent.MessageAttribute> messageAttributeMap = updateFeedMessage.getMessageAttributes();
            String[] followerAliases = updateFeedMessage.getBody().split(updateFeedFollowerAliasSplitCharacter);

            //create new table items
            List<FeedTableItem> feedTableItemsToAdd = Arrays.stream(followerAliases).map(followerAlias -> {
                FeedTableItem feedTableItem = new FeedTableItem();
                feedTableItem.setFeedOwner(followerAlias);
                feedTableItem.setFirstName(messageAttributeMap.get(updateFeedPosterFirstNameKey).getStringValue());
                feedTableItem.setLastName(messageAttributeMap.get(updateFeedPosterLastNameKey).getStringValue());
                feedTableItem.setAlias(messageAttributeMap.get(updateFeedPosterAliasKey).getStringValue());
                feedTableItem.setImageUrl(messageAttributeMap.get(updateFeedPosterImageUrlKey).getStringValue());
                feedTableItem.setStatusText(messageAttributeMap.get(updateFeedStatusTextKey).getStringValue());
                feedTableItem.setTimeStamp(Long.parseLong(messageAttributeMap.get(updateFeedTimeStampKey).getStringValue()));

                return feedTableItem;
            }).collect(Collectors.toList());

            //write to database
            feedTableMapper.batchWrite(feedTableItemsToAdd, new ArrayList<>(0));

            sqs.deleteMessage(new DeleteMessageRequest(updateFeedSQSUrl, updateFeedMessage.getReceiptHandle()));
        }

        return null;
    }
}
