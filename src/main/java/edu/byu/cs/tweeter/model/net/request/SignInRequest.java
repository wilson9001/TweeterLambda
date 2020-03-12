package edu.byu.cs.tweeter.model.net.request;

public class SignInRequest
{
    private final String userAlias, password;

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
