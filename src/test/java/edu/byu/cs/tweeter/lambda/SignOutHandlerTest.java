package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.SignOutRequest;
import edu.byu.cs.tweeter.model.net.request.SignOutRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SignOutResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignOutHandlerTest
{
    private static final String authToken = "HorriblyInsecureAuthtokenForUser.@TestUser";
    private static final User testUser = new User("Test", "User", "");
    private static final SignOutRequest_Net request = new SignOutRequest_Net(authToken, new SignOutRequest(testUser));

    @Test
    void handleRequest()
    {
        SignOutResponse response = new SignOutHandler().handleRequest(request, null);

        assertNull(response.getMessage());
    }
}