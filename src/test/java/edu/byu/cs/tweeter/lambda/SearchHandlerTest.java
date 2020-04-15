package edu.byu.cs.tweeter.lambda;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.SearchRequest;
import edu.byu.cs.tweeter.model.net.request.SearchRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SearchResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchHandlerTest
{
    private static final String testQuery = "User0";
    private static final String authTokenStart = "dummyAuthTokenFor";
    private static final SearchRequest_Net request = new SearchRequest_Net(new SearchRequest(testQuery), authTokenStart.concat("@TestUser"));

    @Test
    void handleRequest()
    {
        SearchResponse response = new SearchHandler().handleRequest(request, null);

        assertNull(response.getMessage());
        assertNotNull(response.getSearchedUser());
    }
}