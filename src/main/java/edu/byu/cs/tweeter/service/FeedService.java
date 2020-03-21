package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.FeedDAO;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FeedRequest_Net;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public class FeedService
{
    private static FeedDAO feedDAO;

    public FeedService()
    {
        if (feedDAO == null)
        {
            feedDAO = new FeedDAO();
        }
    }

    public FeedResponse getFeed(FeedRequest_Net feedRequest)
    {
        return feedDAO.getFeed(feedRequest);
    }
}
