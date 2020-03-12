package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;

public class FollowersDAO
{
    private static FakeDatabase fakeDatabase;

    public FollowersDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public FollowersResponse getFollowers(FollowersRequest followersRequest)
    {
        assert followersRequest.getLimit() >= 1;
        assert followersRequest.getFollowee() != null;

        return fakeDatabase.getFollowers(followersRequest);
    }
}
