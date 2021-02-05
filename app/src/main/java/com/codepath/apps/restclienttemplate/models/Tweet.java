package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String tweetBody;
    public String createdAt;
    public User user;
    public long id;



    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.tweetBody=jsonObject.getString("text");
        tweet.createdAt=jsonObject.getString("created_at");
        tweet.id=jsonObject.getLong("id");
        tweet.user=User.fromJson(jsonObject.getJSONObject("user"));
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getTweetBody() {
        return tweetBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }


    public long getId() {
        return id;
    }
}
