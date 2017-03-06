package com.codepath.apps.mysimpletwitter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joanniehuang on 2017/3/3.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private long lastId;

    public TweetsArrayAdapter(Context context, List<Tweet> tweet) {
        super(context, android.R.layout.simple_list_item_1, tweet);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.get tweets
        Tweet tweet = getItem(position);
        //2. Find or inflate the template
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        //3. find the subviews to fill with date in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.profile_img);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvuserName);
        TextView tvBodyText = (TextView) convertView.findViewById(R.id.tvbodyText);
        TextView timeStamp = (TextView) convertView.findViewById(R.id.timeStamp);
        //4. Return the view to the list
        tvUserName.setText("@"+tweet.getUser().getScreenName());
        tvBodyText.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        timeStamp.setText(tweet.getRelativeTimeAgo(tweet.getCreateAt()));
        Picasso.with(getContext()).load(tweet.getUser().getProfileImgURL()).into(ivProfileImage);
        return convertView;
    }

    public long getLastId() {
        return lastId;
    }

    //private Handler handler
}
