package com.codepath.apps.mysimpletwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletwitter.TwitterApplication;
import com.codepath.apps.mysimpletwitter.TwitterClient;
import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by joanniehuang on 2017/3/11.
 */

public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();

    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFr = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFr.setArguments(args);
        return userFr;
    }

    private void populateTimeline() {
        String screenName = getArguments().getString("screen_name");
        client.getUserInformation(screenName, new JsonHttpResponseHandler(){
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array){
                addAll(Tweet.fromJSONArray(array));
                getAdapter().notifyDataSetChanged();
            }

            //FAILED
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

        });
    }

    @Override
    public void loadNextDataFromApi(long page) {
        String screenName = getArguments().getString("screen_name");
        client.getUserInformation(page, screenName, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                addAll(Tweet.fromJSONArray(array));
                getAdapter().notifyDataSetChanged();
                getScrollListener().resetState();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

            }
        });
    }
}
