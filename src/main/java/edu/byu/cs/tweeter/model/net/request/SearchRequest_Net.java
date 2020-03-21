package edu.byu.cs.tweeter.model.net.request;

public class SearchRequest_Net
{
    public String authToken;
    public SearchRequest request;

    public SearchRequest_Net()
    {}

    public SearchRequest_Net(SearchRequest request, String authToken)
    {
        this.request = request;
        this.authToken = authToken;
    }
}
