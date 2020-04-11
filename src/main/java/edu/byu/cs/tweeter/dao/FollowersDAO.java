package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FollowersDAO extends DAO
{
    private static DynamoDBMapper followsTableMapper;

    public FollowersDAO()
    {
        if(followsTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            followsTableMapper = new DynamoDBMapper(client);
        }
    }

    public FollowersResponse getFollowers(FollowersRequest followersRequest)
    {
        if(followersRequest.getLimit() < 1 || followersRequest.getFollowee() == null)
        {
            return new FollowersResponse("Invalid request");
        }

        FollowsTableItem followsTableQuery = new FollowsTableItem();
        followsTableQuery.setFolloweeAlias(followersRequest.followee.alias);

        DynamoDBQueryExpression<FollowsTableItem> followersQueryExpression = new DynamoDBQueryExpression<FollowsTableItem>()
                .withIndexName(followsTableIndexName)
                .withHashKeyValues(followsTableQuery)
                .withLimit(followersRequest.limit)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        if(followersRequest.lastFollower != null)
        {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>(2);

            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setS(followersRequest.followee.alias);

            exclusiveStartKey.put(followsTableIndexHashKey, attributeValue);

            attributeValue = new AttributeValue();
            attributeValue.setS(followersRequest.lastFollower.alias);

            exclusiveStartKey.put(followsTableIndexRangeKey, attributeValue);

            followersQueryExpression.withExclusiveStartKey(exclusiveStartKey);
        }

        QueryResultPage<FollowsTableItem> queryPage = followsTableMapper.queryPage(FollowsTableItem.class, followersQueryExpression);

        List<FollowsTableItem> queryResults = queryPage.getResults();
        boolean hasMorePages = queryPage.getLastEvaluatedKey() != null;

        List<User> followers = queryResults.stream()
                .map(followsTableItem -> new User(
                        followsTableItem.getFollowerFirstName(),
                        followsTableItem.getFollowerLastName(),
                        followsTableItem.getFollowerAlias().substring(1),
                        followsTableItem.getFollowerImageUrl()))
                .collect(Collectors.toList());

        return new FollowersResponse(followers, hasMorePages);
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