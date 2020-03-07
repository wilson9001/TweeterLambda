package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;

public class SignUpService
{
    private static SignUpService instance;
    private final ServerFacade serverFacade;

    public static SignUpService getInstance()
    {
        if (instance== null)
        {
            instance = new SignUpService();
        }

        return instance;
    }

    private SignUpService()
    {
        serverFacade = new ServerFacade();
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        return serverFacade.signUp(signUpRequest);
    }
}
