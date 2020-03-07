package edu.byu.cs.tweeter.net.response;

public class ChangeRelationshipResponse
{
    private final String message;
    private final RelationshipChanged relationshipChanged;

    public enum RelationshipChanged
    {
        FOLLOWED,
        UNFOLLOWED,
        NOCHANGE
    }

    public ChangeRelationshipResponse(RelationshipChanged relationshipChanged)
    {
        message = null;
        this.relationshipChanged = relationshipChanged;
    }

    public ChangeRelationshipResponse(String message)
    {
        this.message = message;
        this.relationshipChanged = RelationshipChanged.NOCHANGE;
    }

    public String getMessage()
    {
        return message;
    }

    public RelationshipChanged getRelationshipChanged()
    {
        return relationshipChanged;
    }
}
