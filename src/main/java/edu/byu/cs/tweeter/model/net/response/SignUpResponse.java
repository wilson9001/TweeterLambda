package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpResponse
{
    private final User signedUpUser;
    private final String message;
    private final String authToken;

    public SignUpResponse(User newUser, String authToken)
    {
        signedUpUser = newUser;
        this.authToken = authToken;
        message = null;
    }

    public SignUpResponse(String message)
    {
        this.message = message;
        signedUpUser = null;
        authToken = null;
    }

    public User getUser()
    {
        return signedUpUser;
    }

    public String getMessage()
    {
        return message;
    }

    public String getAuthToken()
    {
        return authToken;
    }
}
