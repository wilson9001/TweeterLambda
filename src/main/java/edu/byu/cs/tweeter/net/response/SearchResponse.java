package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchResponse
{
    private final User searchedUser;
    private final String message;
    private final boolean userFollowsSearchedUser;

    public SearchResponse(User searchedUser, boolean userFollowsSearchedUser)
    {
        this.searchedUser = searchedUser;
        this.userFollowsSearchedUser = userFollowsSearchedUser;
        this.message = null;
    }

    public SearchResponse(String message)
    {
        this.searchedUser = null;
        this.userFollowsSearchedUser = false;
        this.message = message;
    }

    public User getSearchedUser()
    {
        return searchedUser;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean getUserFollowsSearchedUser()
    {
        return userFollowsSearchedUser;
    }
}
