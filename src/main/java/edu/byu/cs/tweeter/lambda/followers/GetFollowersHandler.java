package edu.byu.cs.tweeter.lambda.followers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.service.FollowersService;

public class GetFollowersHandler implements RequestHandler<FollowersRequest, FollowersResponse>
{
    @Override
    public FollowersResponse handleRequest(FollowersRequest followersRequest, Context context)
    {
        FollowersService service = new FollowersService();
        return service.getFollowers(followersRequest);
    }
}
