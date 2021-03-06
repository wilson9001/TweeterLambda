package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FeedRequest_Net;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetFeedHandlerTest
{
    private static final String authToken = "automatedTestingAuthToken";
    private static final User testUser = new User("User", "1009", "");

    private static final FeedRequest_Net request = new FeedRequest_Net(authToken, new FeedRequest(testUser, 10, null));

    @Test
    void handleRequest()
    {
        GetFeedHandler getFeedHandler = new GetFeedHandler();

        FeedResponse feedResponse = getFeedHandler.handleRequest(request, null);

        assertNull(feedResponse.getMessage());
        assertNotNull(feedResponse.getStatuses());
    }
}