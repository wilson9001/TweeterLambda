package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FeedRequest_Net;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public class FeedDAO
{
    private static FakeDatabase fakeDatabase;

    public FeedDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public FeedResponse getFeed(FeedRequest_Net request)
    {
        return fakeDatabase.getFeed(request);
    }
}
