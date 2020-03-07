package edu.byu.cs.tweeter.model.domain;

import android.util.Log;

import androidx.core.util.Pair;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Status implements Comparable<Status>
{
    private final static String ALIASREGEX = "@[\\S]*";

    //since java strings have a toString method, and since the other two classes can also implement toString, this may allow for the use of a generic list to print the list.
    private final String statusText;

    public List<Pair<String, Pair<Integer, Integer>>> getReferences()
    {
        return references;
    }

    private List<Pair<String, Pair<Integer, Integer>>> references;
    private final Date timeStamp;
    private final User poster;

    public Status(@NotNull String statusText, @NotNull User poster)
    {
        this.timeStamp = new Date();
        this.poster = poster;
        this.statusText = statusText;

        //TODO: go through status string and build list of starting indices and references for references
        Pattern pattern = Pattern.compile(ALIASREGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(statusText);

        references = new ArrayList<>();

        while(matcher.find())
        {
            //may need to store alias text here with group()
            references.add(new Pair<>(matcher.group(), new Pair<>(matcher.start(), matcher.end())));
        }
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
        Status status = (Status) o;
        return toString().equals(status.toString());
    }

    @NotNull
    @Override
    public String toString()
    {
        return "Status{" +
               "alias='" + poster.getAlias() + '\'' +
               ", timestamp='" + timeStamp.getTime() + '\'' +
               ", statusText='" + getStatusText() + '\'' +
               '}';
    }

    @Override
    public int compareTo(Status status)
    {
        return this.timeStamp.compareTo(status.getTimeStamp());
    }
}
