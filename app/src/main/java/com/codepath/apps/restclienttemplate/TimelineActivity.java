package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.FileUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.adapters.ComplexTweetAdapter;
import com.codepath.apps.restclienttemplate.adapters.TweetsAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class TimelineActivity extends AppCompatActivity {

    public static final String TAG="TimelineActivity";
    public static final int COMPOSE_REQUEST=0;
    TwitterClient client;
    RecyclerView rvTimeline;
    List<Tweet> tweets;
    //TweetsAdapter adapter;
    ComplexTweetAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;
    Context context;
    FloatingActionButton fabCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        context = this;
        client=TwitterApp.getRestClient(this);

        fabCompose=findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,ComposeActivity.class);

                startActivityForResult(intent,COMPOSE_REQUEST);
            }
        });



        swipeContainer=findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Fetching new data");
                populateHomeTimeline();
            }
        });

        rvTimeline=findViewById(R.id.rvTimeline);
        tweets = new ArrayList<>();
        //adapter = new TweetsAdapter(this,tweets);
        adapter = new ComplexTweetAdapter(this,tweets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "onLoadMore "+page);
                loadMoreData();

            }
        };

        rvTimeline.addOnScrollListener(scrollListener);


        populateHomeTimeline();



        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.compose:
                //navigate to compose activity
                Intent intent= new Intent(this,ComposeActivity.class);
                startActivityForResult(intent,COMPOSE_REQUEST);

                //note on composing a tweet: the post request returns a json object identical to
                // the ones we use for getting a tweet, we can use this to our advantage when adding
                //the tweet to the timeline

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COMPOSE_REQUEST){
            switch(resultCode){
                case RESULT_OK:
                    Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
                    tweets.add(0,tweet);
                    adapter.notifyItemInserted(0);
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    private void loadMoreData() {
        // 1. Send an API request to retrieve appropriate paginated data
        client.getMoreTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess for loadMoreData"+json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                } catch (JSONException e) {
                    Log.e(TAG,"JSONException in loadMoreData",e);
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "onFailure for loadMoreData",throwable);


            }
        },tweets.get(tweets.size()-1).getId());
    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess!"+json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    adapter.clear();
                    adapter.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    Log.e(TAG,"Json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG,"onFailure!",throwable);

            }
        });
    }
}