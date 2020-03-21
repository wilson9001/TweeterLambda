package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.SignInDAO;
import edu.byu.cs.tweeter.model.net.request.SignInRequest;
import edu.byu.cs.tweeter.model.net.response.SignInResponse;

public class SignInService
{
    private static SignInDAO signInDAO;

    public SignInService()
    {
        if(signInDAO == null)
        {
            signInDAO = new SignInDAO();
        }
    }

    public SignInResponse signIn(SignInRequest request)
    {
        return signInDAO.signIn(request);
    }
}
