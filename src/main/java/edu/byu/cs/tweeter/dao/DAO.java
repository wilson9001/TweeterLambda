package edu.byu.cs.tweeter.dao;

import com.amazonaws.regions.Regions;

public abstract class DAO
{
    protected static final Regions region = Regions.US_WEST_2;

    static final String feedTableName = "feeds";
    protected static final String feedsTableHashKey = "feedOwner";
    protected static final String feedsTableRangeKey = "timeStamp";

    static final String userTableName = "users";
    protected static final String userTableHashKey = "alias";
    protected static final String userTableIndexName = "authToken-index";
    protected static final String userTableIndexHashKey = "authToken";

    static final String storyTableName = "stories";
    protected static final String storyTableHashKey = "alias";
    protected static final String storyTableRangeKey = "timeStamp";

    static final String followsTableName = "follows";
    protected static final String followsTableHashKey = "followerAlias";
    protected static final String followsTableRangeKey = "followeeAlias";
    protected static final String followsTableIndexName = "followee-index";
    protected static final String followsTableIndexHashKey = followsTableRangeKey;
    protected static final String followsTableIndexRangeKey = followsTableHashKey;

    protected static final String invalidAuthTokenMessage = "Invalid authToken";
    protected static final String invalidRequestMessage = "Invalid request";
    protected static final String serverErrorMessage = "Server error";

    protected static final String postStatusSQSUrl = "https://sqs.us-west-2.amazonaws.com/073776183233/PostStatus";
    protected static final String postStatusSQSPosterAliasKey = "posterAlias";
    protected static final String postStatusSQSTimeStampKey = "timestamp";
    protected static final String postStatusSQSPosterFirstNameKey = "posterFirstName";
    protected static final String postStatusSQSPosterLastNameKey = "posterLastName";
    protected static final String postStatusSQSPosterImageUrlKey = "posterImageUrl";

    protected static final String updateFeedSQSUrl = "https://sqs.us-west-2.amazonaws.com/073776183233/updateFeed";
    protected static final String updateFeedStatusTextKey = "statusText";
    protected static final String updateFeedPosterFirstNameKey = postStatusSQSPosterFirstNameKey;
    protected static final String updateFeedPosterLastNameKey = postStatusSQSPosterLastNameKey;
    protected static final String updateFeedPosterImageUrlKey = postStatusSQSPosterImageUrlKey;
    protected static final String updateFeedTimeStampKey = postStatusSQSTimeStampKey;
    protected static final String updateFeedPosterAliasKey = postStatusSQSPosterAliasKey;
    protected static final String updateFeedFollowerAliasSplitCharacter = ",";
    protected static final int followersPerUpdateFeedMessage = 100;
}

/*
~~~DynamoDB Schemas~~~
users:
[
	{
		"alias": "<user's alias>" <----H
		"firstName": "<first name>",
		"lastName": "<last name>",
		"imageUrl": "<url to image, which is going to be an S3 bucket>"
		"passwordHash": "<server-side hash of password>"
		"authToken": "<authToken for user, which occasionally expires and has to be replaced>" <----IH
		"authTokenExpirationDate": <UNIX timestamp of when the current authToken expires>
	}
[

stories:
[
	"statusText": "<text of status>",
	"timeStamp": <UNIX timestamp>, <----R
	"firstName": "<first name>",
	"lastName": "<last name>",
	"imageUrl": "<url to image, which is going to be an S3 bucket>"
	"alias": "<user's alias>" <----H
]

feeds:
[
	"feedOwner": "<alias of user who owns this feed>", <----H
	"statusText": "<text of status>",
	"timeStamp": <UNIX timestamp>, <----R
	"firstName": "<first name>",
	"lastName": "<last name>",
	"imageUrl": "<url to image, which is going to be an S3 bucket>"
	"alias": "<user's alias>"
]

follows:
[
	{
		"followerAlias": "<alias of follower>", <----H, IR
		"followerFirstName": "<first name of follower>"
		"followerLastName": "<last name of follower>"
		"followerImageUrl": "<url to follower's image>"
		"followeeAlias": "<alias of followee>" <----R, IH
		"followeeFirstName": "<first name of followee>"
		"followeeLastName": "<last name of followee>"
		"followeeImageUrl": "<url to followee's image>"
	}
]
*/