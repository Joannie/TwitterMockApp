package com.codepath.apps.mysimpletwitter.models;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by joanniehuang on 2017/3/3.
 */

@Parcel
public class User {
    //list attribute
    String name;
    long userID;
    String screenName;
    String profileImgURL;
    String profileDescription;
    long followerCount;
    long followingCount;

    //generate the User object
    public static User fromJSON(JSONObject jsonObject){
        //populate the json data
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.userID = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImgURL = jsonObject.getString("profile_image_url");
            user.profileDescription = jsonObject.getString("description");
            user.followerCount = jsonObject.getLong("followers_count");
            user.followingCount = jsonObject.getLong("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public long getUserID() {
        return userID;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }
}
