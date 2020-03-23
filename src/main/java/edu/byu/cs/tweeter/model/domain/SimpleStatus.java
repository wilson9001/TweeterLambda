package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

//NOTE: This class is a simplified version of the Status class found in the android client. It does not store parsed references for several reasons (some better than others ;P)
public class SimpleStatus implements Comparable<SimpleStatus>
{
    //since java strings have a toString method, and since the other two classes can also implement toString, this may allow for the use of a generic list to print the list.
    public String statusText;
    public long timeStamp;
    public User poster;

    public SimpleStatus()
    {}

    public SimpleStatus(@NotNull String statusText, @NotNull User poster)
    {
        this.timeStamp = System.currentTimeMillis();
        this.poster = poster;
        this.statusText = statusText;
    }

    public SimpleStatus(@NotNull String statusText, @NotNull User poster, long timeStamp)
    {
        this.timeStamp = timeStamp;
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
        return new Date(timeStamp);
    }

    public long getRawTimestamp()
    {
        return timeStamp;
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
               ", timestamp='" + timeStamp + '\'' +
               ", statusText='" + getStatusText() + '\'' +
               '}';
    }

    @Override
    public int compareTo(SimpleStatus status)
    {
        return Long.compare(timeStamp, status.getRawTimestamp());
    }
}
