package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest
{

    public User followee;
    public int limit;
    public User lastFollower;

    public FollowersRequest()
    {}

    public FollowersRequest(User followee, int limit, User lastFollower)
    {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public User getFollowee()
    {
        return followee;
    }

    public int getLimit()
    {
        return limit;
    }

    public User getLastFollower()
    {
        return lastFollower;
    }
}
