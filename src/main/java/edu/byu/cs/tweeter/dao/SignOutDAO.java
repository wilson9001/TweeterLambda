package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.SignOutRequest_Net;
import edu.byu.cs.tweeter.model.net.response.SignOutResponse;

public class SignOutDAO
{
    private static FakeDatabase fakeDatabase;

    public SignOutDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public SignOutResponse signOut(SignOutRequest_Net request_net)
    {
        return fakeDatabase.signOut(request_net);
    }
}
