package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;

public class ChangeRelationshipDAO extends DAO
{
    private static DynamoDBMapper followsTableMapper;
    private static AuthTokenDAO authTokenDAO;

    public ChangeRelationshipDAO()
    {
        if(followsTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            followsTableMapper = new DynamoDBMapper(client);
        }

        if(authTokenDAO == null)
        {
            authTokenDAO = new AuthTokenDAO();
        }
    }

    public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest_Net request)
    {
        if(request.authToken == null
                || request.request == null
                || request.request.currentUser == null
                || request.request.otherUser == null
                || request.request.relationshipChange == null)
        {
            return new ChangeRelationshipResponse(invalidRequestMessage);
        }

        //verify authToken
        if(!authTokenDAO.isValidAuthToken(request.request.currentUser.alias, request.authToken))
        {
            return new ChangeRelationshipResponse(invalidAuthTokenMessage);
        }

        FollowsTableItem relationShipToChange = new FollowsTableItem();
        relationShipToChange.setFollowerAlias(request.request.currentUser.alias);
        relationShipToChange.setFollowerFirstName(request.request.currentUser.firstName);
        relationShipToChange.setFollowerLastName(request.request.currentUser.lastName);
        relationShipToChange.setFollowerImageUrl(request.request.currentUser.imageUrl);

        relationShipToChange.setFolloweeAlias(request.request.otherUser.alias);
        relationShipToChange.setFolloweeFirstName(request.request.otherUser.firstName);
        relationShipToChange.setFolloweeLastName(request.request.otherUser.lastName);
        relationShipToChange.setFolloweeImageUrl(request.request.otherUser.imageUrl);

        if (request.request.relationshipChange == ChangeRelationshipRequest.RelationshipChange.FOLLOW)
        {
            followsTableMapper.save(relationShipToChange);
        }
        else
        {
            followsTableMapper.delete(relationShipToChange);
        }

        return new ChangeRelationshipResponse(
                request.request.relationshipChange == ChangeRelationshipRequest.RelationshipChange.FOLLOW ?
                        ChangeRelationshipResponse.RelationshipChanged.FOLLOWED :
                        ChangeRelationshipResponse.RelationshipChanged.UNFOLLOWED);
    }
}
