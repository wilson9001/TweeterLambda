package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FeedRequest_Net;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.service.FeedService;

public class GetFeedHandler implements RequestHandler<FeedRequest_Net, FeedResponse>
{
    @Override
    public FeedResponse handleRequest(FeedRequest_Net feedRequest, Context context)
    {
        return new FeedService().getFeed(feedRequest);
    }
}
