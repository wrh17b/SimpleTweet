package com.codepath.apps.restclienttemplate.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class TextHolder extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvHandle;
    TextView tvName;
    TextView tvTweetBody;
    TextView tvRelativeTime;
    TextView tvRetweet;
    ImageView ivRetweetIcon;

    public TextHolder(@NonNull View itemView) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvHandle=itemView.findViewById(R.id.tvHandle);
        tvName=itemView.findViewById(R.id.tvName);
        tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
        tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        tvRetweet=itemView.findViewById(R.id.tvRetweet);
        ivRetweetIcon=itemView.findViewById(R.id.ivRetweetIcon);
    }

    public void bind(Tweet tweet, Context context){
        if(tweet.isRetweet){
            tvRetweet.setVisibility(View.VISIBLE);
            ivRetweetIcon.setVisibility(View.VISIBLE);
            tvRetweet.setText((tweet.retweeter +" Retweeted"));
        }else{
            tvRetweet.setVisibility(View.GONE);
            ivRetweetIcon.setVisibility(View.GONE);
        }

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
