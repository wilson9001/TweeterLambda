package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class ChangeRelationshipRequest
{
    public enum RelationshipChange
    {
        FOLLOW,
        UNFOLLOW
    }

    private final RelationshipChange relationshipChange;

    private final User currentUser, otherUser;

    public ChangeRelationshipRequest(User currentUser, User otherUser, RelationshipChange relationshipChange)
    {
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.relationshipChange = relationshipChange;
    }

    public RelationshipChange getRelationshipChange()
    {
        return relationshipChange;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public User getOtherUser()
    {
        return otherUser;
    }
}
