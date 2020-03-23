package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInResponse
{
    public User user;
    public String authToken;
    public String message;

    public SignInResponse(User signedInUser, String authToken)
    {
        this.user = signedInUser;
        this.authToken = authToken;
        this.message = null;
    }

    public SignInResponse(String message)
    {
        this.message = message;
        this.user = null;
        this.authToken = null;
    }

    public User getUser()
    {
        return user;
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
