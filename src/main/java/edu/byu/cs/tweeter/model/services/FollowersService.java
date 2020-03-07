package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.FollowersRequest;
import edu.byu.cs.tweeter.net.response.FollowersResponse;

public class FollowersService
{

    private static FollowersService instance;

    private final ServerFacade serverFacade;

    public static FollowersService getInstance()
    {
        if (instance == null)
        {
            instance = new FollowersService();
        }

        return instance;
    }

    private FollowersService()
    {
        serverFacade = new ServerFacade();
    }

    public FollowersResponse getFollowers(FollowersRequest followersRequest)
    {
        return serverFacade.getFollowers(followersRequest);
    }
}
