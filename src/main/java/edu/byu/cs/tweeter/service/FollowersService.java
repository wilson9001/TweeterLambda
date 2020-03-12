package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.FollowersDAO;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;

public class FollowersService
{
    private FollowersDAO followersDAO;

    public FollowersService()
    {
        if (followersDAO == null)
        {
            followersDAO = new FollowersDAO();
        }
    }

    public FollowersResponse getFollowers(FollowersRequest request)
    {
        return followersDAO.getFollowers(request);
    }
}
