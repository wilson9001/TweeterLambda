package edu.byu.cs.tweeter.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.SignUpRequest;
import edu.byu.cs.tweeter.model.net.response.SignUpResponse;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class SignUpDAO extends DAO
{
    private static DynamoDBMapper userTableMapper;
    private static final String defaultImageUrl = "https://tweeter-data.s3-us-west-2.amazonaws.com/Chester_Cheetah.webp";
    private static final String s3BucketName = "tweeter-data";
    public SignUpDAO()
    {
        if(userTableMapper == null)
        {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
            userTableMapper = new DynamoDBMapper(client);
        }
    }

    public SignUpResponse signUp(SignUpRequest request)
    {
        if(request.firstName == null ||
                request.lastName == null ||
                request.password == null ||
                request.password.isEmpty())
        {
            return new SignUpResponse(invalidRequestMessage);
        }

        if(request.alias == null || request.alias.isEmpty())
        {
            request.alias = "@".concat(request.firstName).concat(request.lastName);
        }

        //make sure alias isn't already taken
        UserTableItem possibleNewUser = new UserTableItem();
        possibleNewUser.setAlias(request.alias.startsWith("@") ? request.alias : "@".concat(request.alias));

        UserTableItem existingUser = userTableMapper.load(possibleNewUser);

        if(existingUser != null)
        {
            return new SignUpResponse(possibleNewUser.getAlias().concat(" already taken"));
        }

        if(request.imageURL == null || request.imageURL.isEmpty())
        {
            possibleNewUser.setImageUrl(defaultImageUrl);
        }
        else
        {
            //if image url is provided, grab image and upload to S3. Then save URL to S3 image in user's profile
            try
            {
                //retrieve image from web
                URL imageUrl = new URL(request.imageURL);
                //BufferedImage image = ImageIO.read(imageUrl);

                InputStream imageStream = new BufferedInputStream(imageUrl.openStream());

                //put image into S3
                AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
                String imageKey = possibleNewUser.getAlias();

                ObjectMetadata metadata = new ObjectMetadata();

                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        s3BucketName,
                        imageKey,
                        imageStream,
                        metadata)
                        .withCannedAcl(
                                CannedAccessControlList.PublicRead);

                PutObjectResult result = s3.putObject(putObjectRequest);

                System.out.println(result.toString());

                imageUrl = s3.getUrl(s3BucketName, imageKey);

                possibleNewUser.setImageUrl(imageUrl.toString());
            } catch (Exception e)
            {
                e.printStackTrace();
                return new SignUpResponse("Unable to retrieve profile pic");
            }

            //possibleNewUser.setImageUrl(request.imageURL);

        }

        //hash password to save
        try
        {
            possibleNewUser.setHashedPassword(SignInDAO.hashPassword(request.password));
        }
        catch (NoSuchAlgorithmException e)
        {
            return new SignUpResponse("Can't hash password");
        }

        //generate authToken and authToken expiration date to return
        possibleNewUser.setAuthToken(SignInDAO.generateAuthToken());
        possibleNewUser.setAuthTokenExpirationDate(System.currentTimeMillis() + SignInDAO.authTokenTTLMilliseconds);

        //fill in rest of user info
        possibleNewUser.setFirstName(request.firstName);
        possibleNewUser.setLastName(request.lastName);

        //create user entry in table
        userTableMapper.save(possibleNewUser);

        return new SignUpResponse(new User(
                possibleNewUser.getFirstName(),
                possibleNewUser.getLastName(),
                possibleNewUser.getAlias().substring(1),
                possibleNewUser.getImageUrl()),
                possibleNewUser.getAuthToken());
    }
}
