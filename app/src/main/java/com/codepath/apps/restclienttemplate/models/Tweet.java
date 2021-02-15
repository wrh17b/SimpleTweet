package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String tweetBody;
    public String createdAt;
    public String retweeter;
    public String mediaType;

    public Boolean hasMedia; //if the json Object has extended entities
    public Boolean isRetweet; //if the tweet shown on the timeline is a retweet
    public Boolean retweeted; //if the authenticated user has retweeted the tweet
    public Boolean favorited; //if the authenticated user has favorited (previously called "like") the tweet

    public long id;
    public long retweet_id;

    public User user;
    public List<Media> tweetMedia;



    //Empty constructor for parceler library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.tweetMedia=new ArrayList<>();
        tweet.retweet_id=0;

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

        tweet.retweeted=jsonObject.getBoolean("retweeted");
        tweet.favorited=jsonObject.getBoolean("favorited");

        tweet.retweet_id=jsonObject.getLong("id");

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
