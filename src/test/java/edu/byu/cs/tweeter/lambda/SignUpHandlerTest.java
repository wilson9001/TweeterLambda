package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpHandlerTest
{
    private static final SignUpRequest request = new SignUpRequest("First", "Last", "@FirstLast", "password", "");

    @Test
    void handleRequest()
    {
        try
        {
            new SignUpHandler().handleRequest(request, null);
        }
        catch (RuntimeException e)
        {
            assertNotNull(e.getMessage());
        }
    }
}