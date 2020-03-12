package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.SimpleStatus;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest
{
    private final User owner;
    private final int limit;
    private final SimpleStatus lastStatus;

    public FeedRequest(User owner, int limit, SimpleStatus lastStatus)
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

    public SimpleStatus getLastStatus()
    {
        return lastStatus;
    }
}
