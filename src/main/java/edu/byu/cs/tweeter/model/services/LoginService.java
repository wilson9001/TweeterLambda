package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.request.SignOutRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.net.response.SignOutResponse;

public class LoginService {

    private static LoginService instance;

    private final ServerFacade serverFacade;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    private LoginService() {
        serverFacade = new ServerFacade();
    }

    public SignInResponse signIn(SignInRequest signInRequest)
    {
        return serverFacade.signIn(signInRequest);
    }

    public User getCurrentUser() {
        return serverFacade.currentUser();
    }

    public void clearCurrentUser()
    {
        serverFacade.clearCurrentUser();
    }

    public User getUserBeingViewed()
    {
        return serverFacade.userViewing();
    }

    public void clearUserBeingViewed()
    {
        serverFacade.clearUserBeingViewed();
    }

    public SignOutResponse signOut(SignOutRequest signOutRequest)
    {
        return serverFacade.signOut(signOutRequest);
    }

    public boolean getUserFollowsUserBeingViewed()
    {
        return serverFacade.userFollowsUserBeingViewed();
    }
}
