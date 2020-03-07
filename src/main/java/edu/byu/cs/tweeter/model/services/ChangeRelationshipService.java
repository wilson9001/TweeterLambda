package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.request.ChangeRelationshipRequest;
import edu.byu.cs.tweeter.net.response.ChangeRelationshipResponse;

public class ChangeRelationshipService
{
    private static ChangeRelationshipService instance;
    private final ServerFacade serverFacade;

    public static ChangeRelationshipService getInstance()
    {
        if(instance == null)
        {
            instance = new ChangeRelationshipService();
        }

        return instance;
    }

    private ChangeRelationshipService()
    {
        serverFacade = new ServerFacade();
    }

    public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest changeRelationshipRequest)
    {
        return serverFacade.changeRelationship(changeRelationshipRequest);
    }
}
