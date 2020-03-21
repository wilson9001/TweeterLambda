package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignOutRequest
{
    public User userToSignOut;

    public SignOutRequest()
    {}

    public SignOutRequest(User userToSignOut)
    {
        this.userToSignOut = userToSignOut;
    }

    public User getUserToSignOut()
    {
        return userToSignOut;
    }
}
