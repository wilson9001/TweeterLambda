package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest
{
    private final User owner;
    private final int limit;
    private final Status lastStatus;

    public FeedRequest(User owner, int limit, Status lastStatus)
    {
        this.owner = owner;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getOwner()
    {
        return owner;
    }

    public int getLimit()
    {
        return limit;
    }

    public Status getLastStatus()
    {
        return lastStatus;
    }
}
