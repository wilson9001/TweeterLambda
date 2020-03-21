package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.SearchRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SearchResponse;
import edu.byu.cs.tweeter.service.SearchService;

public class SearchHandler implements RequestHandler<SearchRequest_Net, SearchResponse>
{

    @Override
    public SearchResponse handleRequest(SearchRequest_Net request_net, Context context)
    {
        SearchResponse response = new SearchService().search(request_net);

        if(response.getMessage() != null)
        {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
