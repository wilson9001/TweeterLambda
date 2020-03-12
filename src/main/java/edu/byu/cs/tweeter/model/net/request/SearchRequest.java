package edu.byu.cs.tweeter.model.net.request;

public class SearchRequest
{
    private final String searchQuery;

    public SearchRequest(String searchQuery)
    {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery()
    {
        return searchQuery;
    }
}
