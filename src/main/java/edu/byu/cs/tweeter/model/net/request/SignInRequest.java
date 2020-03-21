package edu.byu.cs.tweeter.model.net.request;

public class SignInRequest
{
    public String userAlias, password;

    public SignInRequest()
    {}

    public SignInRequest(String userAlias, String password)
    {
        this.userAlias = userAlias;
        this.password = password;
    }

    public String getUserAlias()
    {
        return userAlias;
    }

    public String getPassword()
    {
        return password;
    }
}
