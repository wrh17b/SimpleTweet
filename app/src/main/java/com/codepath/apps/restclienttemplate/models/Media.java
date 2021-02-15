package com.codepath.apps.restclienttemplate.models;

import android.util.Log;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Media {
    public static final String TAG="Media";
    public String mediaType; //Type of media animated_gif,photo,video
    public String mediaUrl;


    //Empty Constructor needed for parceler library
    public Media(){}

    public static List<Media> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Media> mediaList = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            mediaList.add(Media.fromJsonObject(jsonArray.getJSONObject(i)));
        }

        return mediaList;
    }


    public static Media fromJsonObject(JSONObject jsonObject) throws JSONException {
        Media media = new Media();
        media.mediaType=jsonObject.getString("type");
        if(media.mediaType.equals("video")||media.mediaType.equals("animated_gif")){
            //We want the mp4 URL, not the thumbnail URL. We need to dig deeper!
            media.mediaUrl=jsonObject
                    .getJSONObject("video_info")
                    .getJSONArray("variants")
                    .getJSONObject(0)
                    .getString("url");
        }else {
            media.mediaUrl = jsonObject.getString("media_url_https");
        }
        Log.i(TAG,"fromJsonObject"+media.mediaUrl);
        return media;
    }
}
