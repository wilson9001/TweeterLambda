package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetFollowingHandlerTest
{
    private static final User testUser = new User("Test", "User", "");
    private static final FollowingRequest request = new FollowingRequest(testUser, 10, null);

    @Test
    void handleRequest()
    {
        FollowingResponse response = new GetFollowingHandler().handleRequest(request, null);

        assertNull(response.getMessage());
        assertNotNull(response.getFollowees());
    }
}