package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;
import edu.byu.cs.tweeter.service.SignUpService;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse>
{

    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context)
    {
        SignUpResponse response = new SignUpService().signUp(request);

        if(response.getMessage() != null)
        {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
