package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInResponse
{
    private final User signedInUser;
    private final String message;

    public SignInResponse(User signedInUser)
    {
        this.signedInUser = signedInUser;
        this.message = null;
    }

    public SignInResponse(String message)
    {
        this.message = message;
        this.signedInUser = null;
    }

    public User getUser()
    {
        return signedInUser;
    }

    public String getMessage()
    {
        return message;
    }
}
