package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest_Net;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class StatusDAO
{
    private static FakeDatabase fakeDatabase;

    public StatusDAO()
    {
        if (fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public PostStatusResponse postStatus(PostStatusRequest_Net request)
    {
        return fakeDatabase.postStatus(request);
    }
}
