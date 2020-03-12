package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

//NOTE: This class is a simplified version of the Status class found in the android client. It does not store parsed references for several reasons (some better than others ;P)
public class SimpleStatus implements Comparable<SimpleStatus>
{
    //since java strings have a toString method, and since the other two classes can also implement toString, this may allow for the use of a generic list to print the list.
    private final String statusText;
    private final Date timeStamp;
    private final User poster;

    public SimpleStatus(@NotNull String statusText, @NotNull User poster)
    {
        this.timeStamp = new Date();
        this.poster = poster;
        this.statusText = statusText;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public User getPoster()
    {
        return poster;
    }

    public Date getTimeStamp()
    {
        return new Date(timeStamp.getTime());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(toString());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleStatus status = (SimpleStatus) o;
        return toString().equals(status.toString());
    }

    @NotNull
    @Override
    public String toString()
    {
        return "SimpleStatus{" +
               "alias='" + poster.getAlias() + '\'' +
               ", timestamp='" + timeStamp.getTime() + '\'' +
               ", statusText='" + getStatusText() + '\'' +
               '}';
    }

    @Override
    public int compareTo(SimpleStatus status)
    {
        return this.timeStamp.compareTo(status.getTimeStamp());
    }
}
