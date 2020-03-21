package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.SignUpDAO;
import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;

public class SignUpService
{
    private static SignUpDAO signUpDAO;

    public SignUpService()
    {
        if(signUpDAO == null)
        {
            signUpDAO = new SignUpDAO();
        }
    }

    public SignUpResponse signUp(SignUpRequest request)
    {
        return signUpDAO.signUp(request);
    }
}
