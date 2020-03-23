package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.net.request.SignInRequest;
import edu.byu.cs.tweeter.model.net.response.SignInResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignInHandlerTest
{
    private static final String alias = "TestUser";
    private static final String password = "password";
    private static final SignInRequest request = new SignInRequest(alias, password);

    @Test
    void handleRequest()
    {
        SignInResponse response = new SignInHandler().handleRequest(request, null);

        assertNull(response.getMessage());
        assertNotNull(response.getAuthToken());
        assertNotNull(response.getUser());
    }
}