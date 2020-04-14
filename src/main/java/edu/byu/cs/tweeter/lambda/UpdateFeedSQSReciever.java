package edu.byu.cs.tweeter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.cs.tweeter.dao.DAO;

public class UpdateFeedSQSReciever extends DAO implements RequestHandler<SQSEvent, Void>
{
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context)
    {
        for(SQSEvent.SQSMessage updateFeedMessage : sqsEvent.getRecords())
        {
            
        }

        return null;
    }
}
