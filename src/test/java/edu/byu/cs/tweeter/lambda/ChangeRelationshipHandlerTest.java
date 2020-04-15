package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ChangeRelationshipHandlerTest
{
    private static final String authToken = "automatedTestingAuthToken";
    private static final User userA = new User("User", "1009", "");
    private static final User userB = new User("User", "0", "");
    private static final ChangeRelationshipRequest_Net followRequest = new ChangeRelationshipRequest_Net(authToken, new ChangeRelationshipRequest(userA, userB, ChangeRelationshipRequest.RelationshipChange.FOLLOW));
    private static final ChangeRelationshipRequest_Net unFollowRequest = new ChangeRelationshipRequest_Net(authToken, new ChangeRelationshipRequest(userA, userB, ChangeRelationshipRequest.RelationshipChange.UNFOLLOW));

    @Test
    void followAndUnfollowTest()
    {
        ChangeRelationshipHandler changeRelationshipHandler = new ChangeRelationshipHandler();

        ChangeRelationshipResponse changeRelationshipResponse = changeRelationshipHandler.handleRequest(followRequest, null);

        assertNull(changeRelationshipResponse.getMessage());
        assertEquals(changeRelationshipResponse.getRelationshipChanged(), ChangeRelationshipResponse.RelationshipChanged.FOLLOWED);

        changeRelationshipResponse = changeRelationshipHandler.handleRequest(unFollowRequest, null);

        assertNull(changeRelationshipResponse.getMessage());
        assertEquals(changeRelationshipResponse.getRelationshipChanged(), ChangeRelationshipResponse.RelationshipChanged.UNFOLLOWED);
    }
}