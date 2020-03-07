package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;

public class StoryService {

    private static StoryService instance;

    private final ServerFacade serverFacade;

    public static StoryService getInstance()
    {
        if (instance == null)
        {
            instance = new StoryService();
        }

        return instance;
    }

    private StoryService() {this.serverFacade = new ServerFacade();}

    public StoryResponse getStory(StoryRequest storyRequest)
    {
        return serverFacade.getStory(storyRequest);
    }
}
