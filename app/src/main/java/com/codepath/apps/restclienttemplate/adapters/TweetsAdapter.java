package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    //Pass in context and list of tweets
    //For each row, inflate layout
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfilePic;
        TextView tvHandle;
        TextView tvName;
        TextView tvTweetBody;
        TextView tvRelativeTime;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
            tvHandle=itemView.findViewById(R.id.tvHandle);
            tvName=itemView.findViewById(R.id.tvName);
            tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
            tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        }

        public void bind(Tweet tweet) {
            tvHandle.setText(tweet.getUser().getHandle());
            tvName.setText(tweet.getUser().getName());
            tvTweetBody.setText(tweet.getTweetBody());
            tvRelativeTime.setText(
                    TimeFormatter.
                            getTimeDifference(
                                    tweet.
                                            getCreatedAt()));


            Glide.with(context)
                    .load(tweet.getUser().getProfilePicUrl())
                    .circleCrop()
                    .into(ivProfilePic);

        }
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
