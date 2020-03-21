package edu.byu.cs.tweeter.model.net.request;

public class PostStatusRequest_Net
{
    public String authToken;
    public PostStatusRequest request;

    public PostStatusRequest_Net()
    {}

    public PostStatusRequest_Net(String authToken, PostStatusRequest request)
    {
        this.authToken = authToken;
        this.request = request;
    }
}
