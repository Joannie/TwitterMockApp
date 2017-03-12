package com.codepath.apps.mysimpletwitter.models;

import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by joanniehuang on 2017/3/3.
 */

//Parse the JSON, store the data, encapulate state logic or display logic

@Parcel
public class Tweet implements Parcelable{
    //list out the attributes
    String body;
    long tweetUniqueID;
    User user;
    String createAt;

    public Tweet(){}

    protected Tweet(android.os.Parcel in) {
        body = in.readString();
        tweetUniqueID = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        createAt = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(android.os.Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    //Tweet jsonObject
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.tweetUniqueID = jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    //Tweet.fromJSONArray(...)
    public static ArrayList<Tweet> fromJSONArray(JSONArray array) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i =0; i <array.length(); i++){
            try {
                JSONObject tweetJson = array.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue; //even the tweet failed, still contine it
            }
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public long getTweetUniqueID() {
        return tweetUniqueID;
    }

    public String getCreateAt() {
        return createAt;
    }

    public User getUser() {
        return user;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String retiveDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            retiveDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return retiveDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(body);
        parcel.writeLong(tweetUniqueID);
        parcel.writeParcelable(user, i);
        parcel.writeString(createAt);
    }
}
