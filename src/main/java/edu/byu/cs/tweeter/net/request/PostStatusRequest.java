package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest
{
    private final String statusText;
    private final User postingUser;

    public PostStatusRequest(User postingUser, String statusText)
    {
        this.statusText = statusText;
        this.postingUser = postingUser;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public User getPostingUser()
    {
        return postingUser;
    }
}
