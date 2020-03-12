package edu.byu.cs.tweeter.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Follow;
//import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
//import edu.byu.cs.tweeter.net.request.ChangeRelationshipRequest;
//import edu.byu.cs.tweeter.net.request.FeedRequest;
//import edu.byu.cs.tweeter.net.request.FollowersRequest;
//import edu.byu.cs.tweeter.net.request.FollowingRequest;
//import edu.byu.cs.tweeter.net.request.PostStatusRequest;
//import edu.byu.cs.tweeter.net.request.SearchRequest;
//import edu.byu.cs.tweeter.net.request.SignInRequest;
//import edu.byu.cs.tweeter.net.request.SignOutRequest;
//import edu.byu.cs.tweeter.net.request.SignUpRequest;
//import edu.byu.cs.tweeter.net.request.StoryRequest;
//import edu.byu.cs.tweeter.net.response.ChangeRelationshipResponse;
//import edu.byu.cs.tweeter.net.response.FeedResponse;
//import edu.byu.cs.tweeter.net.response.FollowersResponse;
//import edu.byu.cs.tweeter.net.response.FollowingResponse;
//import edu.byu.cs.tweeter.net.response.PostStatusResponse;
//import edu.byu.cs.tweeter.net.response.SearchResponse;
//import edu.byu.cs.tweeter.net.response.SignInResponse;
//import edu.byu.cs.tweeter.net.response.SignOutResponse;
//import edu.byu.cs.tweeter.net.response.SignUpResponse;
//import edu.byu.cs.tweeter.net.response.StoryResponse;

public class FakeDatabase
{
    private static Map<User, List<User>> followerToFollowees;
    private static Map<User, List<User>> followeeToFollowers;
    private static List<Follow> follows;
//    private static List<Status> statuses;
    private static Map<String, User> aliasesToUsers;
//    private static Map<User, List<Status>> userToStory;
//    private static Map<User, List<Status>> userToFeed;
//    private static User signedInUser, userViewing;
    private static Map<String, String> aliasesToPasswords;

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

    /*public Map<User, List<Status>> userToStory()
    {
        return userToStory;
    }*/

    /*public Map<User, List<Status>> userToFeed()
    {
        return userToFeed;
    }*/

    /*public Map<String, String> aliasesToPasswords()
    {
        return aliasesToPasswords;
    }*/

    /*public Map<String, User> aliasesToUsers()
    {
        return aliasesToUsers;
    }*/

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

    /*public SignInResponse signIn(SignInRequest signInRequest)
    {
        if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        if(aliasesToUsers == null)
        {
            initializeUserList();
        }

        String userAlias = signInRequest.getUserAlias();

        String userPassword = aliasesToPasswords.get(userAlias);

        if(userPassword == null)
        {
            return new SignInResponse("User not found");
        }
        else if (userPassword.equals(signInRequest.getPassword()))
        {
            signedInUser = aliasesToUsers.get("@".concat(userAlias));
            userViewing = signedInUser;

            if (signedInUser == null)
            {
                Log.e("FakeDatabase signin", "aliasesToUsers did not contain alias even though aliasesToPasswords did");
            }

            Log.d("FakeDatabase signIn", "SignIn successful");
            return new SignInResponse(signedInUser);
        }
        else
        {
            return new SignInResponse("Incorrect password");
        }
    }*/

    private void initializeUserList()
    {
        aliasesToUsers = new HashMap<>();

        User newUser = new User("Test", "User", "");

        aliasesToUsers.put(newUser.alias, newUser);

        for (int i = 0; i < 12; i++)
        {
            newUser = new User("Test", String.valueOf(i), "");
            aliasesToUsers.put(newUser.alias, newUser);
        }
    }

    /*public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        String userAlias = signUpRequest.getAlias();

        if(aliasesToUsers == null)
        {
            initializeUserList();
        }

        if(aliasesToUsers.get("@".concat(userAlias)) == null)
        {
            User newUser = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), userAlias, signUpRequest.getImageURL());
            followerToFollowees.put(newUser, new ArrayList<User>());
            followeeToFollowers.put(newUser, new ArrayList<User>());
            userToStory.put(newUser, new ArrayList<Status>());
            userToFeed.put(newUser, new ArrayList<Status>());
            aliasesToPasswords.put(userAlias, signUpRequest.getPassword());
            signedInUser = newUser;
            userViewing = signedInUser;
            aliasesToUsers.put("@".concat(userAlias), newUser);

            return new SignUpResponse(newUser);
        }
        else
        {
            return new SignUpResponse("Alias already taken");
        }
    }*/

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

    /*public StoryResponse getStory(StoryRequest storyRequest)
    {
        assert storyRequest.getLimit() >= 0;
        assert storyRequest.getOwner() != null;

        if (userToStory == null)
        {
            userToStory = initializeStories();
        }

        List<Status> statuses = userToStory.get(storyRequest.getOwner());

        List<Status> responseStatuses = new ArrayList<>(storyRequest.getLimit());

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
    }*/

    /*public FeedResponse getFeed(FeedRequest feedRequest)
    {

        //TODO: Fill in functionality
        assert feedRequest.getLimit() >= 0;
        assert feedRequest.getOwner() != null;

        if (userToFeed == null)
        {
            userToFeed = initializeFeed();
        }

        List<Status> statuses = userToFeed.get(feedRequest.getOwner());

        List<Status> responseStatuses = new ArrayList<>(feedRequest.getLimit());

        boolean hasMorePages = false;

        if (statuses != null)
        {
            Collections.sort(statuses);

            Collections.reverse(statuses);

            int feedIndex = getFeedStartingIndex(feedRequest.getLastStatus(), statuses);

            for (int limitCounter = 0; feedIndex < statuses.size() && limitCounter < feedRequest.getLimit(); feedIndex++, limitCounter++)
            {
                responseStatuses.add(statuses.get(feedIndex));
            }

            hasMorePages = feedIndex < statuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }*/

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

    /*private int getStoryStartingIndex(Status lastStatus, List<Status> allStatuses)
    {
        int storyIndex = 0;

        if (lastStatus != null)
        {
            return allStatuses.indexOf(lastStatus) + 1;
        }

        return storyIndex;
    }*/

    /*
    * Generates the status data
    * */
    /*private Map<User, List<Status>> initializeStories()
    {
        Map<User, List<Status>> userToOwnStatuses = new HashMap<>();

        if (followerToFollowees == null)
        {
            followerToFollowees = initializeFollowees();
        }

        if (followeeToFollowers == null)
        {
            followeeToFollowers = initializeFollowers();
        }

        if (statuses == null)
        {
            statuses = getStatusGenerator().generateStatuses(12, 25/*50*//*, followerToFollowees.keySet());
        }

        for (Status status : statuses)
        {
            List<Status> posterStatuses = userToOwnStatuses.get(status.getPoster());

            if (posterStatuses == null)
            {
                posterStatuses = new ArrayList<>();
                userToOwnStatuses.put(status.getPoster(), posterStatuses);
            }

            posterStatuses.add(status);
        }

        return userToOwnStatuses;
    }*/

    /*private int getFeedStartingIndex(Status lastStatus, List<Status> allStatuses)
    {
        int feedIndex = 0;

        if (lastStatus != null)
        {
            feedIndex = allStatuses.indexOf(lastStatus) + 1;
        }

        return feedIndex;
    }*/

    /*private Map<User, List<Status>> initializeFeed()
    {
        Map<User, List<Status>> userToOtherStatuses = new HashMap<>();

        if(userToStory == null)
        {
           userToStory = initializeStories();
        }

        for (User follower : followerToFollowees.keySet())
        {
            List<Status> feedStatuses = userToOtherStatuses.get(follower);

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
                    List<Status> statusesFromFollowee = userToStory.get(followee);

                    if(statusesFromFollowee != null)
                    {
                        feedStatuses.addAll(statusesFromFollowee);
                    }
                }
            }
        }

        return userToOtherStatuses;
    }*/

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

    /*public SignOutResponse signOut(SignOutRequest signOutRequest)
    {
        clearCurrentUser();
        clearUserBeingViewed();

        return new SignOutResponse();
    }*/

    /*public PostStatusResponse postStatus(PostStatusRequest postStatusRequest)
    {
        List<Status> statuses = userToStory.get(postStatusRequest.getPostingUser());

        if (statuses == null)
        {
            return new PostStatusResponse("User not found");
        }

        Status newStatus = new Status(postStatusRequest.getStatusText(), postStatusRequest.getPostingUser());

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

        return new PostStatusResponse();
    }*/

    /*public SearchResponse search(SearchRequest searchRequest)
    {
        if (aliasesToUsers == null)
        {
            initializeUserList();
        }

        User searchedUser = aliasesToUsers.get("@".concat(searchRequest.getSearchQuery()));

        if(searchedUser == null)
        {
            return new SearchResponse("@".concat(searchRequest.getSearchQuery()).concat(" not found"));
        }
        else
        {
            userViewing = searchedUser;

            if(followerToFollowees == null)
            {
                followerToFollowees = initializeFollowees();
            }

            List<User> followees = followerToFollowees.get(signedInUser);
            return new SearchResponse(searchedUser, followees.contains(searchedUser));
        }
    }*/

    /*public ChangeRelationshipResponse changeRelationship(ChangeRelationshipRequest changeRelationshipRequest)
    {
        User follower = changeRelationshipRequest.getCurrentUser();
        User followee = changeRelationshipRequest.getOtherUser();
        List<User> followees = followerToFollowees.get(follower);
        List<User> followersOfFollowee = followeeToFollowers.get(followee);

        if (changeRelationshipRequest.getRelationshipChange() == ChangeRelationshipRequest.RelationshipChange.FOLLOW)
        {
            followees.add(followee);
            followersOfFollowee.add(follower);

            return new ChangeRelationshipResponse(ChangeRelationshipResponse.RelationshipChanged.FOLLOWED);
        }
        else
        {
            followees.remove(followee);
            followersOfFollowee.remove(follower);
            return new ChangeRelationshipResponse(ChangeRelationshipResponse.RelationshipChanged.UNFOLLOWED);
        }
    }*/

    /*public boolean userFollowsUserBeingViewed()
    {
        if (followerToFollowees == null)
        {
            return false;
        }

        return followerToFollowees.get(signedInUser).contains(userViewing);
    }*/
}
