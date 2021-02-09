package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.viewholders.PhotoHolder1;
import com.codepath.apps.restclienttemplate.viewholders.PhotoHolder2;
import com.codepath.apps.restclienttemplate.viewholders.PhotoHolder3;
import com.codepath.apps.restclienttemplate.viewholders.PhotoHolder4;
import com.codepath.apps.restclienttemplate.viewholders.TextHolder;
import com.codepath.apps.restclienttemplate.viewholders.VideoHolder;

import java.util.List;

public class ComplexTweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TEXT_TWEET=0,
            PHOTO_TWEET1=1, PHOTO_TWEET2=2, PHOTO_TWEET3=3, PHOTO_TWEET4=4,
            MP4TWEET=5;

    public static final String TAG="ComplexTweetAdapter";

    public List<Tweet> tweets;
    public Context context;

    public ComplexTweetAdapter(Context context, List<Tweet> tweets) {
        this.tweets = tweets;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case TEXT_TWEET:
                view =inflater.inflate(R.layout.item_tweet,parent,false);
                viewHolder=new TextHolder(view);
                break;
            case PHOTO_TWEET1:
                view =inflater.inflate(R.layout.item_photo1_tweet,parent,false);
                viewHolder=new PhotoHolder1(view);
                break;
            case PHOTO_TWEET2:
                view =inflater.inflate(R.layout.item_photo2_tweet,parent,false);
                viewHolder=new PhotoHolder2(view);
                break;
            case PHOTO_TWEET3:
                view =inflater.inflate(R.layout.item_photo3_tweet,parent,false);
                viewHolder=new PhotoHolder3(view);
                break;
            case PHOTO_TWEET4:
                view =inflater.inflate(R.layout.item_photo4_tweet,parent,false);
                viewHolder=new PhotoHolder4(view);
                break;
            case MP4TWEET:
                view =inflater.inflate(R.layout.item_video_tweet,parent,false);
                viewHolder=new VideoHolder(view, context);
                break;
            default:
                view =inflater.inflate(R.layout.item_tweet,parent,false);
                viewHolder=new TextHolder(view);
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        switch (holder.getItemViewType()){
            case TEXT_TWEET:
                ((TextHolder)holder).bind(tweet,context);
                break;
            case PHOTO_TWEET1:
                ((PhotoHolder1)holder).bind(tweet,context);
                break;
            case PHOTO_TWEET2:
                ((PhotoHolder2)holder).bind(tweet,context);

                break;
            case PHOTO_TWEET3:
                ((PhotoHolder3)holder).bind(tweet,context);

                break;
            case PHOTO_TWEET4:
                ((PhotoHolder4)holder).bind(tweet,context);

                break;
            case MP4TWEET:
                ((VideoHolder)holder).bind(tweet,context);

                break;
            default:
                ((TextHolder)holder).bind(tweet,context);

        }

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public int getItemViewType(int position){
        if(tweets.get(position).hasMedia) {
            switch (tweets.get(position).mediaType) {
                case "photo":
                    switch(tweets.get(position).tweetMedia.size()){
                        case 1:
                            return PHOTO_TWEET1;
                        case 2:
                            return PHOTO_TWEET2;
                        case 3:
                            return PHOTO_TWEET3;
                        case 4:
                            return PHOTO_TWEET4;
                    }
                    break;
                case "animated_gif":
                    return MP4TWEET;
                case "video":
                    return MP4TWEET;

            }
        }else{
            return TEXT_TWEET;
        }
        return TEXT_TWEET;
    }

    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> list){
        tweets.addAll(list);
        notifyDataSetChanged();
    }

}
