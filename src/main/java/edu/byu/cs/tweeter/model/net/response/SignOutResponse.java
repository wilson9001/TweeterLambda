package edu.byu.cs.tweeter.model.net.response;

public class SignOutResponse
{
    private final String message;

    public SignOutResponse()
    {
        this.message = null;
    }

    public SignOutResponse(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
