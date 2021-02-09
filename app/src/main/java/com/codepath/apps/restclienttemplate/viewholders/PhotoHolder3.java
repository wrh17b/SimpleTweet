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

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PhotoHolder3 extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvHandle;
    TextView tvName;
    TextView tvTweetBody;
    TextView tvRelativeTime;
    ImageView ivPhoto1;
    ImageView ivPhoto2;
    ImageView ivPhoto3;
    TextView tvRetweet;
    ImageView ivRetweetIcon;

    public PhotoHolder3(@NonNull View itemView) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvHandle=itemView.findViewById(R.id.tvHandle);
        tvName=itemView.findViewById(R.id.tvName);
        tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
        tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        ivPhoto1=itemView.findViewById(R.id.ivPhoto1);
        ivPhoto2=itemView.findViewById(R.id.ivPhoto2);
        ivPhoto3=itemView.findViewById(R.id.ivPhoto3);
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

        Glide.with(context)
                .load(tweet.tweetMedia.get(0).mediaUrl)
                //.centerCrop()
                .transform(new RoundedCornersTransformation(30,10))
                .into(ivPhoto1);

        Glide.with(context)
                .load(tweet.tweetMedia.get(1).mediaUrl)
                //.centerCrop()
                .transform(new RoundedCornersTransformation(30,10))
                .into(ivPhoto2);

        Glide.with(context)
                .load(tweet.tweetMedia.get(2).mediaUrl)
                //.centerCrop()
                .transform(new RoundedCornersTransformation(30,10))
                .into(ivPhoto3);
    }
}
