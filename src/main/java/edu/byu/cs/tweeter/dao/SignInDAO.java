package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.SignInRequest;
import edu.byu.cs.tweeter.model.net.response.SignInResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SignInDAO extends DAO
{
    private static DynamoDBMapper userTableMapper;
    private static final long authTokenTTLMilliseconds = 86400000; //24 hours

    public SignInDAO()
    {
        if(userTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            userTableMapper = new DynamoDBMapper(client);
        }
    }

    public String generateAuthToken()
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest MD5 = MessageDigest.getInstance("MD5");

        MD5.update(password.getBytes());

        byte[] bytes = MD5.digest();

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < bytes.length; i++)
        {
            stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return stringBuilder.toString();
    }

    public SignInResponse signIn(SignInRequest request)
    {
        if(request.password == null || request.userAlias == null)
        {
            return new SignInResponse(invalidRequestMessage);
        }

        UserTableItem userQuery = new UserTableItem();
        userQuery.setAlias("@".concat(request.userAlias));

        UserTableItem userQueryResult = userTableMapper.load(userQuery);

        if(userQueryResult == null)
        {
            return new SignInResponse("@".concat(request.userAlias).concat(" not found"));
        }

        String signInPasswordHash;

        try
        {
            signInPasswordHash = hashPassword(request.password);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();

            return new SignInResponse(serverErrorMessage);
        }

        if(!signInPasswordHash.equals(userQueryResult.getHashedPassword()))
        {
            System.out.println(String.format("signInPasswordHash: %s\nuserPasswordHash: %s",
                    signInPasswordHash, userQueryResult.getHashedPassword()));
            return new SignInResponse("Invalid password");
        }

        //User signed in successfully
        String newAuthToken = generateAuthToken();
        userQueryResult.setAuthToken(newAuthToken);
        userQueryResult.setAuthTokenExpirationDate(System.currentTimeMillis() + authTokenTTLMilliseconds);

        userTableMapper.save(userQueryResult);

        return new SignInResponse(
                new User(
                        userQueryResult.getFirstName(),
                        userQueryResult.getLastName(),
                        userQueryResult.getAlias().substring(1),
                        userQueryResult.getImageUrl()),
                newAuthToken);
    }
}
