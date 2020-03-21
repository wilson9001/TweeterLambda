package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public class StoryDAO
{
    private static FakeDatabase fakeDatabase;

    public StoryDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public StoryResponse getStory(StoryRequest request)
    {
        assert request.getLimit() >= 1;
        assert request.getOwner() != null;

        return fakeDatabase.getStory(request);
    }
}
