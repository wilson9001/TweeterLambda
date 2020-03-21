package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest_Net;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.service.StatusService;

public class PostStatusHandler implements RequestHandler<PostStatusRequest_Net, PostStatusResponse>
{
    @Override
    public PostStatusResponse handleRequest(PostStatusRequest_Net request, Context context)
    {
        PostStatusResponse response = new StatusService().postStatus(request);

        if(response.getMessage() != null)
        {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
