package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.StatusDAO;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest_Net;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class StatusService
{
    private static StatusDAO statusDAO;

    public StatusService()
    {
        if(statusDAO == null)
        {
            statusDAO = new StatusDAO();
        }
    }

    public PostStatusResponse postStatus(PostStatusRequest_Net request)
    {
        return statusDAO.postStatus(request);
    }
}
