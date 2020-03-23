package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetStoryHandlerTest
{
    private static final User testUser = new User("Test", "User", "");
    private static final StoryRequest request = new StoryRequest(testUser, 10, null);

    @Test
    void handleRequest()
    {
        StoryResponse response = new GetStoryHandler().handleRequest(request, null);

        assertNull(response.getMessage());
        assertNotNull(response.getStatuses());
    }
}