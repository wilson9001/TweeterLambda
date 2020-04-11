package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import static edu.byu.cs.tweeter.dao.DAO.followsTableName;

@DynamoDBTable(tableName = followsTableName)
public class FollowsTableItem extends DAO
{
    private String followerAlias;
    private String followerFirstName;
    private String followerLastName;
    private String followerImageUrl;
    private String followeeAlias;
    private String followeeFirstName;
    private String followeeLastName;
    private String followeeImageUrl;

    @DynamoDBAttribute(attributeName = "followerFirstName")
    public String getFollowerFirstName()
    {
        return followerFirstName;
    }
    public void setFollowerFirstName(String followerFirstName)
    {
        this.followerFirstName = followerFirstName;
    }

    @DynamoDBAttribute(attributeName = "followerLastName")
    public String getFollowerLastName()
    {
        return followerLastName;
    }
    public void setFollowerLastName(String followerLastName)
    {
        this.followerLastName = followerLastName;
    }

    @DynamoDBAttribute(attributeName = "followerImageUrl")
    public String getFollowerImageUrl()
    {
        return followerImageUrl;
    }
    public void setFollowerImageUrl(String followerImageUrl)
    {
        this.followerImageUrl = followerImageUrl;
    }

    @DynamoDBAttribute(attributeName = "followeeFirstName")
    public String getFolloweeFirstName()
    {
        return followeeFirstName;
    }
    public void setFolloweeFirstName(String followeeFirstName)
    {
        this.followeeFirstName = followeeFirstName;
    }

    @DynamoDBAttribute(attributeName = "followeeLastName")
    public String getFolloweeLastName()
    {
        return followeeLastName;
    }
    public void setFolloweeLastName(String followeeLastName)
    {
        this.followeeLastName = followeeLastName;
    }

    @DynamoDBAttribute(attributeName = "followeeImageUrl")
    public String getFolloweeImageUrl()
    {
        return followeeImageUrl;
    }
    public void setFolloweeImageUrl(String followeeImageUrl)
    {
        this.followeeImageUrl = followeeImageUrl;
    }

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