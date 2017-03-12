package com.codepath.apps.mysimpletwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletwitter.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletwitter.R;
import com.codepath.apps.mysimpletwitter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletwitter.TwitterClient;
import com.codepath.apps.mysimpletwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanniehuang on 2017/3/10.
 */

public abstract class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> mTweets;
    private TweetsArrayAdapter adapter;
    private RecyclerView listView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TwitterClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, parent, false);
        listView = (RecyclerView) view.findViewById(R.id.twListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Tweet tweet = adapter.getItem(adapter.getItemCount()-1);
                loadNextDataFromApi(tweet.getTweetUniqueID()-1);
            }
        };

        listView.addOnScrollListener(scrollListener);


        return view;
    }

    //creation of lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweets = new ArrayList<>();
        adapter = new TweetsArrayAdapter(getActivity(), mTweets);

    }

    public void addAll(List<Tweet> tweets){
        mTweets.addAll(tweets);
    }

    public void add(Tweet tweet){
        mTweets.add(tweet);
    }

    public void insert(Tweet tweet){
        mTweets.add(0, tweet);
    }

    public TweetsArrayAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getListView() {
        return listView;
    }


    public EndlessRecyclerViewScrollListener getScrollListener() {
        return scrollListener;
    }

    public abstract void loadNextDataFromApi(long page);
}
