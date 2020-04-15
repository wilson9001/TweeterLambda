package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

//This is a simplified version of the User object found in the tweeter client. It has been altered to be compatible with AWS lambda.
public class User implements Comparable<User>
{

    public String firstName;
    public String lastName;
    public String alias;
    public String imageUrl;

    public User()
    {}

    public User(@NotNull String firstName, @NotNull String lastName, String imageURL)
    {
        this(firstName, lastName, firstName.concat(lastName), imageURL);
    }

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String alias, String imageURL)
    {
        this.firstName = firstName.isEmpty() ? "firstName" : firstName;
        this.lastName = lastName.isEmpty() ? "lastName" : lastName;
        this.alias = alias.isEmpty() ? "@".concat(this.firstName).concat(this.lastName) : "@".concat(alias);
        this.imageUrl = imageURL.isEmpty() ? "https://tweeter-data.s3-us-west-2.amazonaws.com/Chester_Cheetah.webp" : imageURL;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    /*public String getName()
    {
        return String.format("%s %s", firstName, lastName);
    }*/

    public String getAlias()
    {
        return alias;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(alias);
    }

    @NotNull
    @Override
    public String toString()
    {
        return "User{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", alias='" + alias + '\'' +
               ", imageUrl='" + imageUrl + '\'' +
               '}';
    }

    @Override
    public int compareTo(User user)
    {
        return this.getAlias().compareTo(user.getAlias());
    }
}
