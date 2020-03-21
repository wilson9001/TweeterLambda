package edu.byu.cs.tweeter.model.net.request;

public class SignOutRequest_Net
{
    public String authToken;
    public SignOutRequest request;

    public SignOutRequest_Net()
    {}

    public SignOutRequest_Net(String authToken, SignOutRequest request)
    {
        this.authToken = authToken;
        this.request = request;
    }
}
