package edu.byu.cs.tweeter.model.net.response;

public class PostStatusResponse
{
    String message;

    public PostStatusResponse()
    {
        message = null;
    }

    public PostStatusResponse(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
