package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignOutRequest
{
    private final User userToSignOut;

    public SignOutRequest(User userToSignOut)
    {
        this.userToSignOut = userToSignOut;
    }

    public User getUserToSignOut()
    {
        return userToSignOut;
    }
}
