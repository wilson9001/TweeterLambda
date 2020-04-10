package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static edu.byu.cs.tweeter.dao.DAO.userTableName;

@DynamoDBTable(tableName = userTableName)
public class UserTableItem extends DAO
{
    @DynamoDBHashKey(attributeName = userTableHashKey)
    public String getAlias()
    {
        return alias;
    }
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    @DynamoDBIndexHashKey(attributeName = userTableIndexHashKey, globalSecondaryIndexName = userTableIndexName)
    public String getAuthToken()
    {
        return authToken;
    }
    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
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

    @DynamoDBAttribute(attributeName = "authTokenExpirationDate")
    public long getAuthTokenExpirationDate()
    {
        return authTokenExpirationDate;
    }
    public void setAuthTokenExpirationDate(long authTokenExpirationDate)
    {
        this.authTokenExpirationDate = authTokenExpirationDate;
    }

    @DynamoDBAttribute(attributeName = "hashedPassword")
    public String getHashedPassword()
    {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }

    private String firstName;
    private String lastName;
    private String alias;
    private String imageUrl;
    private String authToken;
    private String hashedPassword;
    private long authTokenExpirationDate;

    @Override
    public String toString()
    {
        return "{\nfirstName: " +
                firstName +
                "\nlastName: " +
                lastName +
                "\nalias: " +
                alias +
                "\nimageUrl: " +
                imageUrl +
                "\nauthToken: " +
                authToken +
                "\nhashedPassword: " +
                hashedPassword +
                "\nauthTokenExpirationDate: " +
                authTokenExpirationDate +
                "\n}";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserTableItem userTableItem = (UserTableItem) obj;
        return alias.equals(userTableItem.alias);
    }
}
/*
~~~DynamoDB User Schema~~~
users:
[
	{
		"alias": "<user's alias>" <----H
		"firstName": "<first name>",
		"lastName": "<last name>",
		"imageUrl": "<url to image, which is going to be an S3 bucket>"
		"passwordHash": "<server-side hash of password>"
		"authToken": "<authToken for user, which occasionally expires and has to be replaced>" <----IH
		"authTokenExpirationDate": <UNIX timestamp of when the current authToken expires>
	}
[
 */