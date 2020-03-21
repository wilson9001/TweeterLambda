package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.StoryDAO;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public class StoryService
{
    private static StoryDAO storyDAO;

    public StoryService()
    {
        if(storyDAO == null)
        {
            storyDAO = new StoryDAO();
        }
    }

    public StoryResponse getStory(StoryRequest request)
    {
        return storyDAO.getStory(request);
    }
}
