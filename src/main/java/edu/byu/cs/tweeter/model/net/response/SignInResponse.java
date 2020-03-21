package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInResponse
{
    private final User signedInUser;
    private final String authToken;
    private final String message;

    public SignInResponse(User signedInUser, String authToken)
    {
        this.signedInUser = signedInUser;
        this.authToken = authToken;
        this.message = null;
    }

    public SignInResponse(String message)
    {
        this.message = message;
        this.signedInUser = null;
        this.authToken = null;
    }

    public User getUser()
    {
        return signedInUser;
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
