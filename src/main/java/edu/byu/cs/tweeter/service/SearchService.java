package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.SearchDAO;
import edu.byu.cs.tweeter.model.net.request.SearchRequest;
import edu.byu.cs.tweeter.model.net.request.SearchRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SearchResponse;

public class SearchService
{
    private static SearchDAO searchDAO;

    public SearchService()
    {
        if(searchDAO == null)
        {
            searchDAO = new SearchDAO();
        }
    }

    public SearchResponse search(SearchRequest_Net request_net)
    {
        return searchDAO.search(request_net);
    }
}
