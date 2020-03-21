package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

public class FollowingDAO
{
    private static FakeDatabase fakeDatabase;

    public FollowingDAO()
    {
        if (fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request)
    {
        assert request.getLimit() >= 1;
        assert request.getFollower() != null;

        return fakeDatabase.getFollowees(request);
    }
}
