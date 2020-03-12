package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.SimpleStatus;

public class StoryResponse extends PagedResponse {
    private List<SimpleStatus> statuses;

    public StoryResponse(String message)
    {
        super(false, message, false);
    }

    public StoryResponse(List<SimpleStatus> statuses, boolean hasMorePages)
    {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public List<SimpleStatus> getStatuses()
    {
        return statuses;
    }
}
