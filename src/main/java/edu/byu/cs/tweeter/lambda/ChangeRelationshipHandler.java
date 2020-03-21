package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.ChangeRelationshipRequest_Net;
import edu.byu.cs.tweeter.model.net.response.ChangeRelationshipResponse;
import edu.byu.cs.tweeter.service.ChangeRelationshipService;

public class ChangeRelationshipHandler implements RequestHandler<ChangeRelationshipRequest_Net, ChangeRelationshipResponse>
{
    @Override
    public ChangeRelationshipResponse handleRequest(ChangeRelationshipRequest_Net changeRelationshipRequest, Context context)
    {
        ChangeRelationshipResponse response = new ChangeRelationshipService().changeRelationship(changeRelationshipRequest);

        if(response.getMessage() != null)
        {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
