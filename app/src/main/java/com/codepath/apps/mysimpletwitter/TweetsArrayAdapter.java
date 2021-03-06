package com.codepath.apps.mysimpletwitter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joanniehuang on 2017/3/3.
 */

public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {

    private List<Tweet> mTweet;
    private Context mContext;

    public TweetsArrayAdapter(Context context, List<Tweet> tweet) {
        mTweet = tweet;
        mContext = context;
    }

    @Override
    public TweetsArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

        ViewHolder holder = new ViewHolder(tweetView);
        return holder;
    }

    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Tweet tweet = mTweet.get(position);

        // Set item views based on your views and data model
        ImageView ivProfileImage = holder.ivProfileImage;
        TextView tvUserName = holder.tvUserName;
        TextView tvBodyText = holder.tvBodyText;
        TextView timeStamp = holder.timeStamp;

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImgURL()).into(ivProfileImage);
        tvUserName.setText("@"+tweet.getUser().getScreenName());
        tvBodyText.setText(tweet.getBody());
        timeStamp.setText(tweet.getRelativeTimeAgo(tweet.getCreateAt()));

    }

    @Override
    public int getItemCount() {
        return mTweet.size();
    }

    public Tweet getItem(int position){
        return mTweet.get(position);
    }

    public Context getContext() {
        return mContext;
    }

    //private Handler handler
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBodyText;
        public TextView timeStamp;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.profile_img);
            tvUserName = (TextView) itemView.findViewById(R.id.tvuserName);
            tvBodyText = (TextView) itemView.findViewById(R.id.tvbodyText);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);

        }
    }
}
