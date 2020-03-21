package edu.byu.cs.tweeter.dao;

import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;

public class ChangeRelationshipDAO
{
    private static FakeDatabase fakeDatabase;

    public ChangeRelationshipDAO()
    {
        if(fakeDatabase == null)
        {
            fakeDatabase = new FakeDatabase();
        }
    }

    public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest_Net request)
    {
        assert request.request.currentUser != null;
        assert request.request.otherUser != null;
        assert request.request.relationshipChange != null;
        assert request.authToken != null;

        return fakeDatabase.changeRelationship(request);
    }
}
