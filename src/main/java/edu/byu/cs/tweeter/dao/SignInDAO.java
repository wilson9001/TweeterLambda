package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.SignInRequest;
import edu.byu.cs.tweeter.model.net.response.SignInResponse;

public class SignInDAO
{
    private static FakeDatabase fakeDatabase;

    public SignInDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public SignInResponse signIn(SignInRequest request)
    {
        assert request.password != null;
        assert request.userAlias != null;

        return fakeDatabase.signIn(request);
    }
}
