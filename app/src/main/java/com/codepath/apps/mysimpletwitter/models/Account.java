package com.codepath.apps.mysimpletwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joanniehuang on 2017/3/5.
 */

public class Account {

    //list_attribute
    private String name;
    private String profileImgUrl;

    //generate Account object

    public static Account fromJSON(JSONObject jsonObject){
        Account account = new Account();

        try {
            account.name = jsonObject.getString("screen_name");
            account.profileImgUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return account;

    }

    public String getName() {
        return name;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

}
