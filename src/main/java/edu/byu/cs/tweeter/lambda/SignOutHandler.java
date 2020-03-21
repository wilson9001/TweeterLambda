package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.SignOutRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SignOutResponse;
import edu.byu.cs.tweeter.service.SignOutService;

public class SignOutHandler implements RequestHandler<SignOutRequest_Net, SignOutResponse>
{
    @Override
    public SignOutResponse handleRequest(SignOutRequest_Net request_net, Context context)
    {
        SignOutResponse response = new SignOutService().signOut(request_net);

        if(response.getMessage() != null)
        {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
