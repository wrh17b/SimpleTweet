package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String name;
    public String handle;
    public String profilePicUrl;


    //Empty Constructor needed for parceler library
    public User(){}

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.name=jsonObject.getString("name");
        user.handle=jsonObject.getString("screen_name");
        user.profilePicUrl=jsonObject.getString("profile_image_url_https");


        return user;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return "@"+handle;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }
}
