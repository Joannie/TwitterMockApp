package com.codepath.apps.mysimpletwitter;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "RldHgQyaOCFhPKm63FkJuCKGO";       // Change this
	public static final String REST_CONSUMER_SECRET = "QQfpAnVbqHFMrpE087LRttv61jqZRgEW4HexurG8EE3fj8HZgE"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

    private int maxID;

	public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

	//Get statuses/home_timeline.json
	//count=25
	//since_id=1

	public void getHomeTimeline(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify parameter
        RequestParams params = new RequestParams();
        params.put("count", 10);
        //Execute the request
        getClient().get(apiUrl, params, handler);

    }

    public void getHomeTimeline(long max_ID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 10);
        params.put("max_id", max_ID);

        //Execute the request
        getClient().get(apiUrl, params, handler);

    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 10);

        //Execute the request
        getClient().get(apiUrl, params, handler);

    }


    //To Get the personal profile in order to compose new tweets
    public void getAccountProfile(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);

    }

    //post a new tweet
    public void postNewTweet(String status, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
            params.add("status", status);
            getClient().post(apiUrl,params,handler);

    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 10);
        params.put("screen_name", screenName);

        //Execute the request
        getClient().get(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
