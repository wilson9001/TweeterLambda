package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.domain.SimpleStatus;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoryDAO extends DAO
{
    private static DynamoDBMapper storyTableMapper;

    public StoryDAO()
    {
        if(storyTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            storyTableMapper = new DynamoDBMapper(client);
        }
    }

    public StoryResponse getStory(StoryRequest request)
    {
        if(request.getLimit() < 1 || request.getOwner() == null)
        {
            return new StoryResponse("Invalid request");
        }

        //get story starting from last position
        StoryTableItem storyTableQuery = new StoryTableItem();
        storyTableQuery.setAlias(request.owner.alias);

        DynamoDBQueryExpression<StoryTableItem> storyQueryExpression = new DynamoDBQueryExpression<StoryTableItem>()
                .withHashKeyValues(storyTableQuery).withLimit(request.limit)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        if(request.lastStatus != null)
        {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>(2);

            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setS(request.owner.alias);

            exclusiveStartKey.put(storyTableHashKey, attributeValue);

            attributeValue = new AttributeValue();
            attributeValue.setN(String.valueOf(request.lastStatus.timeStamp));

            exclusiveStartKey.put(storyTableRangeKey, attributeValue);

            storyQueryExpression.withExclusiveStartKey(exclusiveStartKey);
        }

        QueryResultPage<StoryTableItem> queryPage = storyTableMapper.queryPage(StoryTableItem.class, storyQueryExpression);

        List<StoryTableItem> queryResults = queryPage.getResults();
        boolean hasMorePages = queryPage.getLastEvaluatedKey() != null;

        List<SimpleStatus> resultStatuses = queryResults.stream()
                .map(storyTableItem -> new SimpleStatus(
                        storyTableItem.getStatusText(),
                        new User(
                                storyTableItem.getFirstName(),
                                storyTableItem.getLastName(),
                                storyTableItem.getAlias().substring(1),
                                storyTableItem.getImageUrl()
                        ),
                        storyTableItem.getTimeStamp()))
                .collect(Collectors.toList());

        return new StoryResponse(resultStatuses, hasMorePages);
    }
}
