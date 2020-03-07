package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;

public class SearchService
{
    private static SearchService instance;
    private static ServerFacade serverFacade;

    public static SearchService getInstance()
    {
        if (instance == null)
        {
            instance = new SearchService();
        }

        return instance;
    }

    private SearchService()
    {
        serverFacade = new ServerFacade();
    }

    public SearchResponse search(SearchRequest searchRequest)
    {
        return serverFacade.search(searchRequest);
    }
}
