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
    public Boolean hasMedia; //if the json Object has extended entities
    public List<Media> tweetMedia;
    public String mediaType;
    public Boolean isRetweet;
    public String retweeter;



    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.tweetMedia=new ArrayList<>();

        tweet.id=jsonObject.getLong("id");

        tweet.isRetweet=false;
        tweet.retweeter=null;
        if(jsonObject.has("retweeted_status")){
            tweet.isRetweet=true;
            tweet.retweeter=jsonObject.getJSONObject("user").getString("name");
            jsonObject=jsonObject.getJSONObject("retweeted_status");

        }


        tweet.hasMedia=false;
        tweet.mediaType=null;
        if(jsonObject.has("extended_entities")){
            tweet.hasMedia=true;
            tweet.tweetMedia.
                    addAll(Media.fromJsonArray(
                            jsonObject
                                    .getJSONObject("extended_entities")
                                    .getJSONArray("media")));
            tweet.mediaType=tweet.tweetMedia.get(0).mediaType;
        }

        tweet.tweetBody=jsonObject.getString("text");
        tweet.createdAt=jsonObject.getString("created_at");

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
