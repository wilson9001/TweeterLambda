package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;

public class SignUpDAO
{
    private static FakeDatabase fakeDatabase;

    public SignUpDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public SignUpResponse signUp(SignUpRequest request)
    {
        return fakeDatabase.signUp(request);
    }
}
