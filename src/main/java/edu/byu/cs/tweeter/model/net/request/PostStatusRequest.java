package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest
{
    public String statusText;
    public User postingUser;

    public PostStatusRequest()
    {}

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
