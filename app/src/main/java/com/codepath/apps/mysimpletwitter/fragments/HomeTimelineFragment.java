package com.codepath.apps.mysimpletwitter.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.mysimpletwitter.TwitterApplication;
import com.codepath.apps.mysimpletwitter.TwitterClient;
import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by joanniehuang on 2017/3/10.
 */

public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private RecyclerView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        listView = super.getListView();
        populateTimeline();

        /*listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Tweet tweet = getAdapter().getItem(getAdapter().getItemCount()-1);
                loadNextDataFromApi(tweet.getTweetUniqueID()-1);
            }
        });*/
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
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

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(long maxid) {

        client.getHomeTimeline(maxid, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                addAll(Tweet.fromJSONArray(array));
                //adapter.addAll(Tweet.fromJSONArray(array));
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

            }
        });
    }
}
