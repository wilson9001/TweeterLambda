package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import edu.byu.cs.tweeter.model.net.request.SignOutRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SignOutResponse;

public class SignOutDAO extends DAO
{
    private static DynamoDBMapper userTableMapper;

    public SignOutDAO()
    {
        if(userTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            userTableMapper = new DynamoDBMapper(client);
        }
    }

    public SignOutResponse signOut(SignOutRequest_Net request_net)
    {
        UserTableItem userQuery = new UserTableItem();
        userQuery.setAlias(request_net.request.userToSignOut.alias);

        UserTableItem userRetrieved = userTableMapper.load(userQuery);

        userRetrieved.setAuthToken(null);
        userRetrieved.setAuthTokenExpirationDate(0);

        userTableMapper.save(userRetrieved);

        return new SignOutResponse();
    }
}
