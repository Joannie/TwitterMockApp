package com.codepath.apps.mysimpletwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter adapter;
    private ListView listView;
    private final int REQUEST_CODE = 20;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        listView = (ListView) findViewById(R.id.twListView);
        tweets = new ArrayList<>();
        adapter = new TweetsArrayAdapter(this, tweets);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Tweet tweet = adapter.getItem(adapter.getCount()-1);
                loadNextDataFromApi(tweet.getTweetUniqueID()-1);

                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        listView.setAdapter(adapter);
        client = TwitterApplication.getRestClient();

        populateTimeline();

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_compose){
            composeTweet();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void composeTweet() {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);

    }

    //get the activity result tweet object and insert it to adapter to show up
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == 200){
                Tweet tweet = Parcels.unwrap(data.getParcelableExtra("newpost"));
                adapter.insert(tweet, 0);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                //Log.d("DEBUG", array.toString());
                adapter.addAll(Tweet.fromJSONArray(array));
                adapter.notifyDataSetChanged();
                //Log.d("DEBUG", adapter.toString());

            }

            //FAILED
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(HomeTimelineActivity.this, "Sorry, no Internect detected!", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", errorResponse.toString());
            }

        });


    }

    //setup the pull-to-refresh onRefreshListener
    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(Tweet.fromJSONArray(json));
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }


    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(long maxid) {

        client.getHomeTimeline(maxid, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                adapter.addAll(Tweet.fromJSONArray(array));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

            }
        });
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
    }


}
