package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest_Net;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostStatusHandlerTest
{
    private static final User testUser = new User("Test", "User", "");
    private static final String authTokenStart = "HorriblyInsecureAuthtokenForUser.";
    private static final PostStatusRequest_Net request = new PostStatusRequest_Net(authTokenStart.concat(testUser.getAlias()), new PostStatusRequest(testUser, "Foo"));

    @Test
    void handleRequest()
    {
        PostStatusResponse response = new PostStatusHandler().handleRequest(request, null);

        assertNull(response.getMessage());
    }
}