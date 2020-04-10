package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;

import java.util.List;

public class AuthTokenDAO extends DAO
{
    private static Index userTableIndex;
    private static DynamoDBMapper userTableMapper;

    public AuthTokenDAO()
    {
        if(userTableIndex == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            userTableIndex = new DynamoDB(client).getTable(userTableName).getIndex(userTableIndexName);
            userTableMapper = new DynamoDBMapper(client);
        }
    }

    public boolean isValidAuthToken(String alias, String authToken)
    {
        UserTableItem userItemQuery = new UserTableItem();
        userItemQuery.setAuthToken(authToken);

        DynamoDBQueryExpression<UserTableItem> userQueryExpression = new DynamoDBQueryExpression<UserTableItem>().withIndexName(userTableIndexName).withHashKeyValues(userItemQuery).withConsistentRead(false);

        List<UserTableItem> userTableItems = userTableMapper.query(UserTableItem.class, userQueryExpression);

        if (userTableItems.size() != 1)
        {
            return false;
        }

        UserTableItem userTableItemFromQuery = userTableItems.get(0);

        userItemQuery.setAlias(alias);

        return (userTableItemFromQuery.equals(userItemQuery) && userTableItemFromQuery.getAuthTokenExpirationDate() >= System.currentTimeMillis());
    }
}
