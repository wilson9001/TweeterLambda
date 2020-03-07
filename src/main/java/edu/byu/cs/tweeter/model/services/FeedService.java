package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;

public class FeedService {

    private static FeedService instance;

    private final ServerFacade serverFacade;

    public static FeedService getInstance()
    {
        if (instance == null)
        {
            instance = new FeedService();
        }

        return instance;
    }

    private FeedService() {this.serverFacade = new ServerFacade();}

    public FeedResponse getFeed(FeedRequest feedRequest)
    {
        return serverFacade.getFeed(feedRequest);
    }
}
