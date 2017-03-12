package com.codepath.apps.mysimpletwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletwitter.models.Tweet;

public class HomeTimelineActivity extends AppCompatActivity {
    private HomeTimelineFragment homeTimelineFragment;
    //private SwipeRefreshLayout swipeContainer;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup the viewPager for tab showing
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetPageAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        /*swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_compose:
                onComposeTweet(item);
                break;
            case R.id.action_profile:
                onProfileView(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onProfileView(MenuItem item){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("class", "accountProfile");
        startActivityForResult(i, REQUEST_CODE);
        //launch the profile view
    }

    private void onComposeTweet(MenuItem item) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);

    }

    //get the activity result tweet object and insert it to adapter to show up
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 200) {
                Tweet tweet = (Tweet) data.getParcelableExtra("newpost");
                homeTimelineFragment.add(tweet);
                //adapter.insert(tweet, 0);
                homeTimelineFragment.getAdapter().notifyDataSetChanged();
            }
        }
    }


    //setup the pull-to-refresh onRefreshListener
    /*public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                // Remember to CLEAR OUT old items before appending in the new ones
                mTweets.clear();
                adapter.notifyItemRemoved(mTweets.size());

                mTweets.addAll(Tweet.fromJSONArray(json));
                adapter.notifyItemChanged(mTweets.size());
                // ...the data has come back, add new items to your adapter...
                adapter.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }*/

    public class TweetPageAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitle[] = {"Home", "Mentions"};

        public TweetPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        //Control the order and creation of fragments within the paper
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimelineFragment();
            }else{
                return new MentionsTimelineFragment();
            }
        }

        //Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        // how many fragments there are in the page
        @Override
        public int getCount() {
            return tabTitle.length;
        }

    }

}

