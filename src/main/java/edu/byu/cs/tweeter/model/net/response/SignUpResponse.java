package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpResponse
{
    private final User signedUpUser;
    private final String message;

    public SignUpResponse(User newUser)
    {
        signedUpUser = newUser;
        message = null;
    }

    public SignUpResponse(String message)
    {
        this.message = message;
        signedUpUser = null;
    }

    public User getUser()
    {
        return signedUpUser;
    }

    public String getMessage()
    {
        return message;
    }
}
