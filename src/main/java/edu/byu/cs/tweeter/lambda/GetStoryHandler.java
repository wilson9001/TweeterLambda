package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.service.StoryService;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse>
{

    @Override
    public StoryResponse handleRequest(StoryRequest storyRequest, Context context)
    {
        return new StoryService().getStory(storyRequest);
    }
}
