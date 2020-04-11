package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.SearchRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SearchResponse;

import java.util.List;

public class SearchDAO extends DAO
{
    private static DynamoDBMapper userTableMapper;
    private static FollowingDAO followingDAO;

    public SearchDAO()
    {
        if (userTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            userTableMapper = new DynamoDBMapper(client);
        }

        if(followingDAO == null)
        {
            followingDAO = new FollowingDAO();
        }
    }

    public SearchResponse search(SearchRequest_Net request_net)
    {
        if(request_net.request.searchQuery == null)
        {
            return new SearchResponse("Invalid request");
        }

        //determine if requester is a signed-in user, and if so check if the authToken is still valid and retrieve the user's info
        UserTableItem requestOwner = null;

        if(request_net.authToken != null)
        {
            //retrieve user info from table
            UserTableItem authTokenQuery = new UserTableItem();
            authTokenQuery.setAuthToken(request_net.authToken);

            DynamoDBQueryExpression<UserTableItem> authTokenQueryExpression = new DynamoDBQueryExpression<UserTableItem>()
                    .withIndexName(userTableIndexName)
                    .withHashKeyValues(authTokenQuery)
                    .withConsistentRead(false);

            List<UserTableItem> authTokenQueryResults = userTableMapper.query(UserTableItem.class, authTokenQueryExpression);

            if(authTokenQueryResults.isEmpty())
            {
                return new SearchResponse(invalidAuthTokenMessage);
            }

            requestOwner = authTokenQueryResults.get(0);

            //verify authToken hasn't expired
            if(requestOwner.getAuthTokenExpirationDate() <= System.currentTimeMillis())
            {
                return new SearchResponse(invalidAuthTokenMessage);
            }
        }

        //search user table for alias
        UserTableItem userQuery = new UserTableItem();
        userQuery.setAlias("@".concat(request_net.request.searchQuery));

        DynamoDBQueryExpression<UserTableItem> searchQueryExpression = new DynamoDBQueryExpression<UserTableItem>()
                .withHashKeyValues(userQuery)
                .withConsistentRead(false);

        List<UserTableItem> queryResults = userTableMapper.query(UserTableItem.class, searchQueryExpression);

        if(queryResults.isEmpty())
        {
            return new SearchResponse("@".concat(request_net.request.searchQuery).concat(" not found"));
        }

        UserTableItem searchedUserTableItem = queryResults.get(0);
        User searchedUser = new User(
                searchedUserTableItem.getFirstName(),
                searchedUserTableItem.getLastName(),
                searchedUserTableItem.getAlias().substring(1),
                searchedUserTableItem.getImageUrl());

        boolean userFollowsSearchedUser = false;

        //check for follows relationship here, if applicable
        if(requestOwner != null)
        {
            userFollowsSearchedUser = followingDAO.userAFollowsUserB(
                    new User(
                            requestOwner.getFirstName(),
                            requestOwner.getLastName(),
                            requestOwner.getAlias().substring(1),
                            requestOwner.getImageUrl()),
                    searchedUser);
        }

        return new SearchResponse(searchedUser, userFollowsSearchedUser);
    }
}
