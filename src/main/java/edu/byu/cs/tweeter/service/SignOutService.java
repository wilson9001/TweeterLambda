package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.SignOutDAO;
import edu.byu.cs.tweeter.model.net.request.SignOutRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SignOutResponse;

public class SignOutService
{
    private static SignOutDAO signOutDAO;

    public SignOutService()
    {
        if(signOutDAO == null)
        {
            signOutDAO = new SignOutDAO();
        }
    }

    public SignOutResponse signOut(SignOutRequest_Net request_net)
    {
        return signOutDAO.signOut(request_net);
    }
}
