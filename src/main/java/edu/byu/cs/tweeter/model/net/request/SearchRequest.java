package edu.byu.cs.tweeter.model.net.request;

public class SearchRequest
{
    public String searchQuery;

    public SearchRequest()
    {}

    public SearchRequest(String searchQuery)
    {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery()
    {
        return searchQuery;
    }
}
