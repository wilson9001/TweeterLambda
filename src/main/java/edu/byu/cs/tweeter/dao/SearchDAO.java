package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.SearchRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SearchResponse;

public class SearchDAO
{
    private static FakeDatabase fakeDatabase;

    public SearchDAO()
    {
        if (fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public SearchResponse search(SearchRequest_Net request_net)
    {
        assert request_net.request.searchQuery != null;

        if(request_net.authToken == null)
        {
            request_net.authToken = "";
        }

        return fakeDatabase.search(request_net);
    }
}
