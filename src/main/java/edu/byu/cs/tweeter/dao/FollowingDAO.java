package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FollowingDAO extends DAO
{
    private static DynamoDBMapper followsTableMapper;

    public FollowingDAO()
    {
        if (followsTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            followsTableMapper = new DynamoDBMapper(client);
        }
    }

    public boolean userAFollowsUserB(User userA, User userB)
    {
        FollowsTableItem followsTableQuery = new FollowsTableItem();
        followsTableQuery.setFollowerAlias(userA.alias);

        AttributeValue rangeKeyValue = new AttributeValue();
        rangeKeyValue.setS(userB.alias);

        DynamoDBQueryExpression<FollowsTableItem> followsQueryExpression = new DynamoDBQueryExpression<FollowsTableItem>()
                .withHashKeyValues(followsTableQuery)
                .withRangeKeyCondition(
                        followsTableRangeKey,
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(rangeKeyValue))
                .withConsistentRead(false);

        List<FollowsTableItem> followResult = followsTableMapper.query(FollowsTableItem.class, followsQueryExpression);

        return !followResult.isEmpty();
    }

    public FollowingResponse getFollowees(FollowingRequest request)
    {
        if(request.getLimit() < 1 || request.getFollower() == null)
        {
            return new FollowingResponse(invalidRequestMessage);
        }

        //get followers starting from last position
        FollowsTableItem followsTableQuery = new FollowsTableItem();
        followsTableQuery.setFollowerAlias(request.follower.alias);

        DynamoDBQueryExpression<FollowsTableItem> followeesQueryExpression = new DynamoDBQueryExpression<FollowsTableItem>()
                .withHashKeyValues(followsTableQuery)
                .withLimit(request.limit)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        if(request.lastFollowee != null)
        {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>(2);

            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setS(request.follower.alias);

            exclusiveStartKey.put(followsTableHashKey, attributeValue);

            attributeValue = new AttributeValue();
            attributeValue.setS(request.getLastFollowee().alias);

            exclusiveStartKey.put(followsTableRangeKey, attributeValue);

            followeesQueryExpression.withExclusiveStartKey(exclusiveStartKey);
        }

        QueryResultPage<FollowsTableItem> queryPage = followsTableMapper.queryPage(FollowsTableItem.class, followeesQueryExpression);

        List<FollowsTableItem> queryResults = queryPage.getResults();
        boolean hasMorePages = queryPage.getLastEvaluatedKey() != null;

        List<User> followees = queryResults.stream()
                .map(followsTableItem -> new User(
                        followsTableItem.getFolloweeFirstName(),
                        followsTableItem.getFolloweeLastName(),
                        followsTableItem.getFolloweeAlias().substring(1),
                        followsTableItem.getFolloweeImageUrl()))
                .collect(Collectors.toList());

        return new FollowingResponse(followees, hasMorePages);
    }
}
/*
~~~follows table schema~~~
follows:
[
	{
		"followerAlias": "<alias of follower>", <----H, IR
		"followerFirstName": "<first name of follower>"
		"followerLastName": "<last name of follower>"
		"followerImageUrl": "<url to follower's image>"
		"followeeAlias": "<alias of followee>" <----R, IH
		"followeeFirstName": "<first name of followee>"
		"followeeLastName": "<last name of followee>"
		"followeeImageUrl": "<url to followee's image>"
	}
]
*/