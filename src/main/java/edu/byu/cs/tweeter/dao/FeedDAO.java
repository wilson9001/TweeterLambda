package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.domain.SimpleStatus;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest_Net;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedDAO extends DAO
{
    private static DynamoDBMapper feedTableMapper;
    private static AuthTokenDAO authTokenDAO;

    public FeedDAO()
    {
        if(feedTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            feedTableMapper = new DynamoDBMapper(client);
        }

        if(authTokenDAO == null)
        {
            authTokenDAO = new AuthTokenDAO();
        }
    }

    public FeedResponse getFeed(FeedRequest_Net request)
    {
        //first verify authToken
        if(!authTokenDAO.isValidAuthToken(request.request.owner.alias, request.authToken))
        {
            return new FeedResponse(invalidAuthTokenMessage);
        }

        //get feed starting from last position
        FeedTableItem feedTableQuery = new FeedTableItem();
        feedTableQuery.setFeedOwner(request.request.owner.alias);

        DynamoDBQueryExpression<FeedTableItem> feedQueryExpression = new DynamoDBQueryExpression<FeedTableItem>()
                .withHashKeyValues(feedTableQuery)
                .withLimit(request.request.limit)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        if(request.request.lastStatus != null)
        {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>(2);

            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setS(request.request.owner.alias);

            exclusiveStartKey.put(feedsTableHashKey, attributeValue);

            attributeValue = new AttributeValue();
            attributeValue.setN(String.valueOf(request.request.lastStatus.timeStamp));

            exclusiveStartKey.put(feedsTableRangeKey, attributeValue);

            feedQueryExpression.withExclusiveStartKey(exclusiveStartKey);
        }

        QueryResultPage<FeedTableItem> queryPage = feedTableMapper.queryPage(FeedTableItem.class, feedQueryExpression);

        List<FeedTableItem> queryResults = queryPage.getResults();
        boolean hasMorePages = queryPage.getLastEvaluatedKey() != null;

        List<SimpleStatus> resultStatuses = queryResults.stream()
                .map(feedTableItem -> new SimpleStatus(
                        feedTableItem.getStatusText(),
                        new User(
                                feedTableItem.getFirstName(),
                                feedTableItem.getLastName(),
                                feedTableItem.getAlias().substring(1),
                                feedTableItem.getImageUrl()),
                        feedTableItem.getTimeStamp()))
                .collect(Collectors.toList());

        return new FeedResponse(resultStatuses, hasMorePages);
    }
}

/*
~~~Feeds Schema~~~
feeds:
[
	"feedOwner": "<alias of user who owns this feed>", <----H
	"statusText": "<text of status>",
	"timeStamp": <UNIX timestamp>, <----R
	"firstName": "<first name>",
	"lastName": "<last name>",
	"imageUrl": "<url to image, which is going to be an S3 bucket>"
	"alias": "<user's alias>"
]
*/