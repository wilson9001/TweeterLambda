package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

//NOTE: This is a simplified version of the FollowingRequest object found in the tweeter client. It has been modified to be more compatible with AWS Lambda.
public class FollowingRequest {

    public User follower;
    public int limit;
    public User lastFollowee;

    public FollowingRequest()
    {}

    public FollowingRequest(User follower, int limit, User lastFollowee)
    {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public User getFollower()
    {
        return follower;
    }

    public int getLimit()
    {
        return limit;
    }

    public User getLastFollowee()
    {
        return lastFollowee;
    }
}
