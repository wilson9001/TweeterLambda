package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetFollowersHandlerTest
{
    private static final User testUser = new User("Test", "User", "");

    private static final FollowersRequest request = new FollowersRequest(testUser, 10, null);

    @Test
    void handleRequest()
    {
        FollowersResponse response = new GetFollowersHandler().handleRequest(request, null);

        assertNull(response.getMessage());
        assertNotNull(response.getFollowers());
    }
}