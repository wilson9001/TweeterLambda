package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import static edu.byu.cs.tweeter.dao.DAO.followsTableName;

@DynamoDBTable(tableName = followsTableName)
public class FollowsTableItem extends DAO
{
    private String followerAlias, followeeAlias;

    @DynamoDBHashKey(attributeName = followsTableHashKey)
    @DynamoDBIndexRangeKey(attributeName = followsTableIndexRangeKey, globalSecondaryIndexName = followsTableIndexName)
    public String getFollowerAlias()
    {
        return followerAlias;
    }
    public void setFollowerAlias(String followerAlias)
    {
        this.followerAlias = followerAlias;
    }

    @DynamoDBRangeKey(attributeName = followsTableRangeKey)
    @DynamoDBIndexHashKey(attributeName = followsTableIndexHashKey, globalSecondaryIndexName = followsTableIndexName)
    public String getFolloweeAlias()
    {
        return followeeAlias;
    }
    public void setFolloweeAlias(String followeeAlias)
    {
        this.followeeAlias = followeeAlias;
    }
}

/*
~~~feeds table schema~~~
follows:
[
	{
		"followerAlias": "<alias of follower>", <----H, IR
		"followeeAlias": "<alias of followee>" <----R, IH
	}
]
*/