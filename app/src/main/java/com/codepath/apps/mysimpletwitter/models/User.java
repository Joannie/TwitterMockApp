package com.codepath.apps.mysimpletwitter.models;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joanniehuang on 2017/3/3.
 */

public class User {
    //list attribute
    private String name;
    private long userID;
    private String screenName;
    private String profileImgURL;

    //generate the User object
    public static User fromJSON(JSONObject jsonObject){
        //populate the json data
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.userID = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImgURL = jsonObject.getString("profile_image_url");
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
}
