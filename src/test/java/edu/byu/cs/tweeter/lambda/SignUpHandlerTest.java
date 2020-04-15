package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SignUpHandlerTest
{

    @Test
    void handleRequest()
    {
        try
        {
            Random randomInt = new Random();
            String first = String.valueOf(randomInt.nextInt());
            String last = String.valueOf(randomInt.nextInt());
            SignUpRequest request = new SignUpRequest(first, last, "@".concat(first).concat(last), "password", "");
            new SignUpHandler().handleRequest(request, null);
        }
        catch (RuntimeException e)
        {
            assertNotNull(e.getMessage());
        }
    }
}