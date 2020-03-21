package edu.byu.cs.tweeter.model.net.request;

public class ChangeRelationshipRequest_Net
{
    public String authToken;
    public ChangeRelationshipRequest request;

    public ChangeRelationshipRequest_Net()
    {}

    public ChangeRelationshipRequest_Net(String authToken, ChangeRelationshipRequest request)
    {
        this.authToken = authToken;
        this.request = request;
    }
}
