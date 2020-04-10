package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static edu.byu.cs.tweeter.dao.DAO.storyTableName;

@DynamoDBTable(tableName = storyTableName)
public class StoryTableItem extends DAO
{
    @DynamoDBAttribute(attributeName = "statusText")
    public String getStatusText()
    {
        return statusText;
    }
    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    @DynamoDBRangeKey(attributeName = "timeStamp")
    public long getTimeStamp()
    {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "imageUrl")
    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    @DynamoDBHashKey(attributeName = "alias")
    public String getAlias()
    {
        return alias;
    }
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    private String statusText;
    private long timeStamp;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String alias;
}

/*
~~~stories table schema~~~
stories:
[
	"statusText": "<text of status>",
	"timeStamp": <UNIX timestamp>, <----R
	"firstName": "<first name>",
	"lastName": "<last name>",
	"imageUrl": "<url to image, which is going to be an S3 bucket>"
	"alias": "<user's alias>" <----H
]
*/