package edu.byu.cs.tweeter.service;

import edu.byu.cs.tweeter.dao.ChangeRelationshipDAO;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;

public class ChangeRelationshipService
{
    private static ChangeRelationshipDAO changeRelationshipDAO;

    public ChangeRelationshipService()
    {
        if (changeRelationshipDAO == null)
        {
            changeRelationshipDAO = new ChangeRelationshipDAO();
        }
    }

    public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest_Net request)
    {
        return changeRelationshipDAO.changeRelationship(request);
    }
}
