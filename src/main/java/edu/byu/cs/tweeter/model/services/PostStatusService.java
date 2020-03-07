package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.net.response.PostStatusResponse;

public class PostStatusService
{
    private static PostStatusService instance;
    private static ServerFacade serverFacade;

    public static PostStatusService getInstance()
    {
        if (instance == null)
        {
            instance = new PostStatusService();
        }

        return instance;
    }

    private PostStatusService()
    {
        serverFacade = new ServerFacade();
    }

    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest)
    {
        return serverFacade.postStatus(postStatusRequest);
    }
}
