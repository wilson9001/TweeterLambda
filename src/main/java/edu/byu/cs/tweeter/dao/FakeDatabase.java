package edu.byu.cs.tweeter.dao;

import java.util.*;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.SimpleStatus;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;

public class FakeDatabase
{
    private static Map<User, List<User>> followerToFollowees;
    private static Map<User, List<User>> followeeToFollowers;
    private static List<Follow> follows;
    private static final int TOTALOTHERUSERS = 12;
    private static final int TOTALSTATUSES = 12;
    private static final String authTokenStart = "HorriblyInsecureAuthtokenForUser.";

    //private static List<SimpleStatus> statuses;
    private static Map<String, User> aliasesToUsers;
    private static Map<User, List<SimpleStatus>> userToStory;
    private static Map<User, List<SimpleStatus>> userToFeed;
    //    private static User signedInUser, userViewing;
    private static Map<String, String> aliasesToPasswords;
    private static Map<String, String> authTokensToAliases;

    public FakeDatabase()
    {
        initializeUserList();

        aliasesToPasswords = new HashMap<>();
        aliasesToPasswords.put("TestUser", "password");

        /*if(aliasesToPasswords == null)
        {
            aliasesToPasswords = new HashMap<>();
            aliasesToPasswords.put("TestUser", "password");
            signedInUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            userViewing = signedInUser;
        }*/
    }

    public Map<User, List<User>> followerToFollowees()
    {
        return followerToFollowees;
    }

    public Map<User, List<User>> followeeToFollowers()
    {
        return followeeToFollowers;
    }

    public Map<User, List<SimpleStatus>> userToStory()
    {
        return userToStory;
    }

    /*public Map<User, List<SimpleStatus>> userToFeed()
    {
        return userToFeed;
    }*/

    /*public Map<String, String> aliasesToPasswords()
    {
        return aliasesToPasswords;
    }*/

    public Map<String, User> aliasesToUsers()
    {
        return aliasesToUsers;
    }

    /*public User currentUser()
    {
        if (signedInUser == null)
        {
            signedInUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        }

        return signedInUser;
    }*/

    /*public void clearCurrentUser()
    {
        signedInUser = null;
    }*/

    /*public User userViewing()
    {
        if (userViewing == null)
        {
           userViewing = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        }

        return userViewing;
    }*/

    /*public void clearUserBeingViewed()
    {
        userViewing = null;
    }*/

    private String makeAuthToken(User user)
    {
        return authTokenStart.concat(user.alias);
    }

    public SignInResponse signIn(SignInRequest signInRequest)
    {
        /*if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        if(aliasesToUsers == null)
        {
            initializeUserList();
        }*/

        String userAlias = signInRequest.getUserAlias();

        String userPassword = aliasesToPasswords.get(userAlias);

        if(userPassword == null)
        {
            return new SignInResponse("User not found");
        }
        else if (userPassword.equals(signInRequest.getPassword()))
        {
            /*signedInUser = aliasesToUsers.get("@".concat(userAlias));
            userViewing = signedInUser;

            if (signedInUser == null)
            {
                Log.e("FakeDatabase signin", "aliasesToUsers did not contain alias even though aliasesToPasswords did");
            }

            return new SignInResponse(signedInUser);*/

            User signedInUser = aliasesToUsers.get("@".concat(userAlias));
            return new SignInResponse(signedInUser, makeAuthToken(signedInUser));
        }
        else
        {
            return new SignInResponse("Incorrect password");
        }
    }

    private void initializeUserList()
    {
        aliasesToUsers = new HashMap<>(TOTALOTHERUSERS+1);
        authTokensToAliases = new HashMap<>(TOTALOTHERUSERS+1);

        User newUser = new User("Test", "User", "");

        aliasesToUsers.put(newUser.alias, newUser);
        authTokensToAliases.put(makeAuthToken(newUser), newUser.alias);

        for (int i = 0; i < TOTALOTHERUSERS; i++)
        {
            newUser = new User("Test", String.valueOf(i), "");
            aliasesToUsers.put(newUser.alias, newUser);
            authTokensToAliases.put(makeAuthToken(newUser), newUser.alias);
        }
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        /*if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }*/

        String userAlias = signUpRequest.getAlias();

        if(aliasesToUsers == null)
        {
            initializeUserList();
        }

        if(aliasesToUsers.get("@".concat(userAlias)) == null)
        {
            /*User newUser = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), userAlias, signUpRequest.getImageURL());
            followerToFollowees.put(newUser, new ArrayList<User>());
            followeeToFollowers.put(newUser, new ArrayList<User>());
            userToStory.put(newUser, new ArrayList<SimpleStatus>());
            userToFeed.put(newUser, new ArrayList<SimpleStatus>());
            aliasesToPasswords.put(userAlias, signUpRequest.getPassword());
            signedInUser = newUser;
            userViewing = signedInUser;
            aliasesToUsers.put("@".concat(userAlias), newUser);

            return new SignUpResponse(newUser);*/

            return new SignUpResponse("User would be signed up");
        }
        else
        {
            return new SignUpResponse("Alias already taken");
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request)
    {
        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        if (followerToFollowees == null)
        {
            followerToFollowees = initializeFollowees();
        }

        List<User> allFollowees = followerToFollowees.get(request.getFollower());

        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0)
        {
            if (allFollowees != null)
            {
                Collections.sort(allFollowees);

                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for (int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++)
                {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    public FollowersResponse getFollowers(FollowersRequest followersRequest)
    {

        assert followersRequest.getLimit() >= 0;
        assert followersRequest.getFollowee() != null;

        if (followeeToFollowers == null)
        {
            followeeToFollowers = initializeFollowers();
        }

        List<User> allFollowers = followeeToFollowers.get(followersRequest.getFollowee());
        List<User> responseFollowers = new ArrayList<>(followersRequest.getLimit());

        boolean hasMorePages = false;

        if (followersRequest.getLimit() > 0)
        {
            if (allFollowers != null)
            {
                Collections.sort(allFollowers);

                int followersIndex = getFollowersStartingIndex(followersRequest.getLastFollower(), allFollowers);

                for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < followersRequest.getLimit(); followersIndex++, limitCounter++)
                {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest storyRequest)
    {
        assert storyRequest.getLimit() >= 0;
        assert storyRequest.getOwner() != null;

        if (userToStory == null)
        {
            userToStory = initializeStories();
        }

        List<SimpleStatus> statuses = userToStory.get(storyRequest.getOwner());

        List<SimpleStatus> responseStatuses = new ArrayList<>(storyRequest.getLimit());

        boolean hasMorePages = false;

        if (statuses != null)
        {
            Collections.sort(statuses);

            Collections.reverse(statuses);

            int storyIndex = getStoryStartingIndex(storyRequest.getLastStatus(), statuses);

            for (int limitCounter = 0; storyIndex < statuses.size() && limitCounter < storyRequest.getLimit(); storyIndex++, limitCounter++)
            {
                responseStatuses.add(statuses.get(storyIndex));
            }

            hasMorePages = storyIndex < statuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    public FeedResponse getFeed(FeedRequest_Net feedRequest)
    {
        assert feedRequest.request.getLimit() >= 0;
        assert feedRequest.request.getOwner() != null;
        assert feedRequest.authToken != null;

        assert aliasesToUsers.get(authTokensToAliases.get(feedRequest.authToken)).equals(feedRequest.request.owner);

        if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        List<SimpleStatus> statuses = userToFeed.get(feedRequest.request.getOwner());

        List<SimpleStatus> responseStatuses = new ArrayList<>(feedRequest.request.getLimit());

        boolean hasMorePages = false;

        if (statuses != null)
        {
            Collections.sort(statuses);

            Collections.reverse(statuses);

            int feedIndex = getFeedStartingIndex(feedRequest.request.getLastStatus(), statuses);

            for (int limitCounter = 0; feedIndex < statuses.size() && limitCounter < feedRequest.request.getLimit(); feedIndex++, limitCounter++)
            {
                responseStatuses.add(statuses.get(feedIndex));
            }

            hasMorePages = feedIndex < statuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers)
    {
        int followersIndex = 0;

        if (lastFollower != null)
        {
            /*for (int i = 0; i < allFollowers.size(); i++)
            {
                if(lastFollower.equals(allFollowers.get(i)))
                {
                    followersIndex = i + 1;
                }
            }*/

            //TODO: Determine if this works or if there's something weird with equals() which makes the provided way necessary.
            return allFollowers.indexOf(lastFollower) + 1;
        }

        return followersIndex;
    }

    /**
     * Generates the followers data
     */
    private Map<User, List<User>> initializeFollowers()
    {
        Map<User, List<User>> followersOfFollowee = new HashMap<>();

        if (follows == null)
        {
            initializeFollows();
        }

        for (Follow follow : follows)
        {
            List<User> followers = followersOfFollowee.get(follow.getFollowee());

            if (followers == null)
            {
                followers = new ArrayList<>();
                followersOfFollowee.put(follow.getFollowee(), followers);
            }

            followers.add(follow.getFollower());
        }

        return followersOfFollowee;
    }

    private int getStoryStartingIndex(SimpleStatus lastStatus, List<SimpleStatus> allStatuses)
    {
        int storyIndex = 0;

        if (lastStatus != null)
        {
            return allStatuses.indexOf(lastStatus) + 1;
        }

        return storyIndex;
    }

    /*
     * Generates the status data
     * */
    private Map<User, List<SimpleStatus>> initializeStories()
    {
        Map<User, List<SimpleStatus>> userToOwnStatuses = new HashMap<>();

        if (followerToFollowees == null)
        {
            followerToFollowees = initializeFollowees();
        }

        if (followeeToFollowers == null)
        {
            followeeToFollowers = initializeFollowers();
        }

        List<SimpleStatus> statuses;

        for (User user : followeeToFollowers().keySet())
        {
            statuses = new ArrayList<>(TOTALSTATUSES);

            for (int i = 0; i < TOTALSTATUSES; i++)
            {
                statuses.add(new SimpleStatus(user.getName().concat(" status ").concat(String.valueOf(i)), user));
            }

            userToOwnStatuses.put(user, statuses);
        }

        statuses = userToOwnStatuses.get(new User("Test", "User", ""));

        statuses.add(new SimpleStatus("@TestUser @User0", new User("Test", "User", "")));
        /*for (SimpleStatus status : statuses)
        {
            List<SimpleStatus> posterStatuses = userToOwnStatuses.get(status.getPoster());

            if (posterStatuses == null)
            {
                posterStatuses = new ArrayList<>();
                userToOwnStatuses.put(status.getPoster(), posterStatuses);
            }

            posterStatuses.add(status);
        }*/

            return userToOwnStatuses;
    }

    private int getFeedStartingIndex(SimpleStatus lastStatus, List<SimpleStatus> allStatuses)
    {
        int feedIndex = 0;

        if (lastStatus != null)
        {
            feedIndex = allStatuses.indexOf(lastStatus) + 1;
        }

        return feedIndex;
    }

    private Map<User, List<SimpleStatus>> initializeFeed()
    {
        Map<User, List<SimpleStatus>> userToOtherStatuses = new HashMap<>();

        if(userToStory == null)
        {
           userToStory = initializeStories();
        }

        for (User follower : followerToFollowees.keySet())
        {
            List<SimpleStatus> feedStatuses = userToOtherStatuses.get(follower);

            if (feedStatuses == null)
            {
                feedStatuses = new ArrayList<>();
                userToOtherStatuses.put(follower, feedStatuses);
            }

            List<User> followees = followerToFollowees.get(follower);

            if (followees != null)
            {
                for (User followee : followees)
                {
                    List<SimpleStatus> statusesFromFollowee = userToStory.get(followee);

                    if(statusesFromFollowee != null)
                    {
                        feedStatuses.addAll(statusesFromFollowee);
                    }
                }
            }
        }

        return userToOtherStatuses;
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees)
    {
        int followeesIndex = 0;

        if (lastFollowee != null)
        {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            /*for (int i = 0; i < allFollowees.size(); i++)
            {
                if (lastFollowee.equals(allFollowees.get(i)))
                {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }*/

            //TODO: See if this works fine or if there's something weird with equals() which makes the provided way necessary
            followeesIndex = allFollowees.indexOf(lastFollowee) + 1;
        }

        return followeesIndex;
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees()
    {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        if (follows == null)
        {
            initializeFollows();
        }

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for (Follow follow : follows)
        {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if (followees == null)
            {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    private void initializeFollows()
    {
        follows = new ArrayList<>();
        for (User follower : aliasesToUsers.values())
        {
            for (User followee : aliasesToUsers.values())
            {
                if (!follower.equals(followee))
                {
                    follows.add(new Follow(follower, followee));
                }
            }
        }
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    /*FollowGenerator getFollowGenerator()
    {
        return FollowGenerator.getInstance();
    }

    StatusGenerator getStatusGenerator()
    {
        return StatusGenerator.getInstance();
    }*/

    public SignOutResponse signOut(SignOutRequest_Net signOutRequest)
    {
        /*clearCurrentUser();
        clearUserBeingViewed();*/

        assert signOutRequest.request.userToSignOut.equals(aliasesToUsers.get(authTokensToAliases.get(signOutRequest.authToken)));

        return new SignOutResponse();
    }

    public PostStatusResponse postStatus(PostStatusRequest_Net postStatusRequest)
    {
        assert postStatusRequest.request.postingUser.equals(aliasesToUsers.get(authTokensToAliases.get(postStatusRequest.authToken)));

        if (followerToFollowees == null)
        {
            followerToFollowees = initializeFollowees();
        }

        List<User> followees = followerToFollowees().get(postStatusRequest.request.getPostingUser());

        if (followees == null)
        {
            return new PostStatusResponse("User not found");
        }

        return new PostStatusResponse();
        /*SimpleStatus newStatus = new SimpleStatus(postStatusRequest.getStatusText(), postStatusRequest.getPostingUser());

        statuses.add(newStatus);

        if(followeeToFollowers == null)
        {
            followeeToFollowers = initializeFollowers();
        }

        List<User> followers = followeeToFollowers.get(postStatusRequest.getPostingUser());

        if (followers == null)
        {
            return new PostStatusResponse("User not properly initialized");
        }

        if(userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        for (User follower : followers)
        {
            userToFeed.get(follower).add(newStatus);
        }

        return new PostStatusResponse();*/
    }

    public SearchResponse search(SearchRequest_Net request_net)
    {
        if (aliasesToUsers == null)
        {
            initializeUserList();
        }

        User searchedUser = aliasesToUsers.get("@".concat(request_net.request.getSearchQuery()));

        if(searchedUser == null)
        {
            return new SearchResponse("@".concat(request_net.request.getSearchQuery()).concat(" not found"));
        }
        else
        {
            //userViewing = searchedUser;

            if(followerToFollowees == null)
            {
                followerToFollowees = initializeFollowees();
            }

            boolean searchedUserFollowsUser = false;

            String signedInUserAlias = authTokensToAliases.get(request_net.authToken);

            if(signedInUserAlias != null)
            {
                User signedInUser = aliasesToUsers.get(signedInUserAlias);
                List<User> followees = followerToFollowees.get(signedInUser);

                searchedUserFollowsUser = followees.contains(searchedUser);
            }

            return new SearchResponse(searchedUser, searchedUserFollowsUser);
        }
    }

    public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest_Net changeRelationshipRequest)
    {
        /*User follower = changeRelationshipRequest.getCurrentUser();
        User followee = changeRelationshipRequest.getOtherUser();
        List<User> followees = followerToFollowees.get(follower);
        List<User> followersOfFollowee = followeeToFollowers.get(followee);*/

        if (changeRelationshipRequest.request.getRelationshipChange() == ChangeRelationshipRequest.RelationshipChange.FOLLOW)
        {
            /*followees.add(followee);
            followersOfFollowee.add(follower);*/

            return new ChangeRelationshipResponse("User would be followed");
        }
        else
        {
            /*followees.remove(followee);
            followersOfFollowee.remove(follower);*/
            return new ChangeRelationshipResponse("User would be unfollowed");
        }
    }

    /*public boolean userFollowsUserBeingViewed()
    {
        if (followerToFollowees == null)
        {
            return false;
        }

        return followerToFollowees.get(signedInUser).contains(userViewing);
    }*/
}
