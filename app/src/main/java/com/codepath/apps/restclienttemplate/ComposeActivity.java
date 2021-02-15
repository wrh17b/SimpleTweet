package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final String TAG="ComposeActivity";
    public static final int MAX_TWEET_LENGTH=280;
    public static final int COMPOSE_REQUEST=0;


    TwitterClient client;

    EditText etCompose;
    Button btTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose=findViewById(R.id.etCompose);
        btTweet=findViewById(R.id.btTweet);
        client=new TwitterClient(this);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent=etCompose.getText().toString();
                //Error checking
                if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tweetContent.length()>MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                //the tweet is valid
                //make an api call
                client.postTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG,"client.postTweet onSuccess");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.d(TAG,"Tweet.fromJson successful");
                            Intent intent= new Intent();
                            intent.putExtra("tweet",Parcels.wrap(tweet));
                            setResult(RESULT_OK,intent);
                            finish();
                        } catch (JSONException e) {
                            Log.e(TAG,"Tweet.fromJson failed",e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d(TAG,"client.postTweet onFailure",throwable);


                    }
                });
            }
        });
    }
}