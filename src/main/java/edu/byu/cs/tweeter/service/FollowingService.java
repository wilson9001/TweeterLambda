package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.FollowingDAO;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

public class FollowingService
{
    private static FollowingDAO followingDAO;

    public FollowingService()
    {
        if (followingDAO == null)
        {
            followingDAO = new FollowingDAO();
        }
    }
    public FollowingResponse getFollowees(FollowingRequest request) {
        return followingDAO.getFollowees(request);
    }
}
