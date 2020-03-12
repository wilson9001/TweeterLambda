package edu.byu.cs.tweeter.model.net.request;

public class SignUpRequest
{
    private String firstName, lastName, alias, password, imageURL;

    public SignUpRequest(String firstName, String lastName, String alias, String password, String imageURL)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.imageURL = imageURL;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAlias()
    {
        return alias;
    }

    public String getPassword()
    {
        return password;
    }

    public String getImageURL()
    {
        return imageURL;
    }
}
