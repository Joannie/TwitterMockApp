package com.codepath.apps.mysimpletwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletwitter.models.Account;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    private ImageView profileImg;
    private TextView profileName;
    private EditText addEditText;
    private Button tweetBtn;
    private Account account;
    private TextView numberChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient();
        profileImg = (ImageView) findViewById(R.id.profile_img_compose);
        profileName = (TextView) findViewById(R.id.tvuserName_compose);
        addEditText = (EditText) findViewById(R.id.editText_compose);
        tweetBtn = (Button) findViewById(R.id.tweetBtn);
        numberChar = (TextView) findViewById(R.id.numberChar);

        addEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                numberChar.setText(String.valueOf(140 - editable.toString().length()));
            }
        });

        populateProfile();

    }

    private void populateProfile() {
        client.getAccountProfile(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                //Log.d("DEBUG", jsonObject.toString());
                account = Account.fromJSON(jsonObject);
                profileName.setText("@"+ account.getName());
                Picasso.with(getContext()).load(account.getProfileImgUrl()).into(profileImg);
                super.onSuccess(statusCode, headers, jsonObject);
            }
        });
    }

    //onClickListener of Tweet Button
    public void onPostTweet(View view){
        client.postNewTweet(addEditText.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


}
