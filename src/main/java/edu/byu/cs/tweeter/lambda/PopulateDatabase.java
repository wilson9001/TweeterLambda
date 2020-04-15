package edu.byu.cs.tweeter.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import edu.byu.cs.tweeter.dao.FollowsTableItem;
import edu.byu.cs.tweeter.dao.SignInDAO;
import edu.byu.cs.tweeter.dao.UserTableItem;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PopulateDatabase
{
    private static final int userEntriesToCreate = 10000;
    private static final String newUsersFirstName = "User";
    private static final String defaultImageUrl = "https://tweeter-data.s3-us-west-2.amazonaws.com/Chester_Cheetah.webp";
    private static final String newUsersPassword = "password";
    private static String newUserPasswordHash = null;
    private static UserTableItem userToFollow;
    private static DynamoDBMapper tableMapper;

    public static void main(String[] args)
    {
        if (tableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
            tableMapper = new DynamoDBMapper(client);
        }

        if (newUserPasswordHash == null)
        {
            try
            {
                newUserPasswordHash = SignInDAO.hashPassword(newUsersPassword);
            } catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
                return;
            }
        }

        if (userToFollow == null)
        {
            userToFollow = new UserTableItem();
            userToFollow.setFirstName("Test");
            userToFollow.setLastName("User");
            userToFollow.setAlias("@TestUser");
            userToFollow.setImageUrl(defaultImageUrl);
            userToFollow.setHashedPassword(newUserPasswordHash);
        }

        List<UserTableItem> newUsers = IntStream.range(0, userEntriesToCreate).mapToObj(userNumber ->
        {
            UserTableItem newUser = new UserTableItem();
            String lastName = String.valueOf(userNumber);
            newUser.setFirstName(newUsersFirstName);
            newUser.setLastName(lastName);
            newUser.setImageUrl(defaultImageUrl);
            newUser.setAlias("@".concat(newUsersFirstName).concat(lastName));
            newUser.setHashedPassword(newUserPasswordHash);

            return newUser;
        }).collect(Collectors.toList());

        newUsers.add(userToFollow);

        List<FollowsTableItem> newFollows = newUsers.stream().map(newUser ->
        {
            FollowsTableItem newFollow = new FollowsTableItem();
            newFollow.setFolloweeFirstName(userToFollow.getFirstName());
            newFollow.setFolloweeLastName(userToFollow.getLastName());
            newFollow.setFolloweeAlias(userToFollow.getAlias());
            newFollow.setFolloweeImageUrl(userToFollow.getImageUrl());
            newFollow.setFollowerFirstName(newUser.getFirstName());
            newFollow.setFollowerLastName(newUser.getLastName());
            newFollow.setFollowerAlias(newUser.getAlias());
            newFollow.setFollowerImageUrl(newUser.getImageUrl());

            return newFollow;
        }).collect(Collectors.toList());

        tableMapper.batchWrite(newUsers, new ArrayList<>(0));
        tableMapper.batchWrite(newFollows, new ArrayList<>(0));

        System.out.println("Tables populated.");
    }
}
//7225