package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.SignInRequest;
import edu.byu.cs.tweeter.model.net.response.SignInResponse;
import edu.byu.cs.tweeter.service.SignInService;

public class SignInHandler implements RequestHandler<SignInRequest, SignInResponse>
{
    @Override
    public SignInResponse handleRequest(SignInRequest request, Context context)
    {
        SignInResponse response = new SignInService().signIn(request);
        String message = response.getMessage();

        if(message != null)
        {
            throw new RuntimeException(message);
        }

        return response;
    }
}
