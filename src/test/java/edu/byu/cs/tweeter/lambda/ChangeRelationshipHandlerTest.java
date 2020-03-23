package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ChangeRelationshipHandlerTest
{
    private static final String authTokenBase = "HorriblyInsecureAuthtokenForUser.";
    private static final User userA = new User("User", "A", "");
    private static final User userB = new User("User", "B", "");
    private static final ChangeRelationshipRequest_Net followRequest = new ChangeRelationshipRequest_Net(authTokenBase.concat(userA.alias), new ChangeRelationshipRequest(userA, userB, ChangeRelationshipRequest.RelationshipChange.FOLLOW));
    private static final ChangeRelationshipRequest_Net unFollowRequest = new ChangeRelationshipRequest_Net(authTokenBase.concat(userA.alias), new ChangeRelationshipRequest(userA, userB, ChangeRelationshipRequest.RelationshipChange.UNFOLLOW));

    @Test
    void followTest()
    {
        ChangeRelationshipHandler changeRelationshipHandler = new ChangeRelationshipHandler();

        try
        {
            ChangeRelationshipResponse changeRelationshipResponse = changeRelationshipHandler.handleRequest(followRequest, null);
        }
        catch (RuntimeException e)
        {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void unfollowTest()
    {
        ChangeRelationshipHandler changeRelationshipHandler = new ChangeRelationshipHandler();

        try
        {
            ChangeRelationshipResponse changeRelationshipResponse = changeRelationshipHandler.handleRequest(unFollowRequest, null);
        }
        catch (RuntimeException e)
        {
            assertNotNull(e.getMessage());
        }
    }
}