package com.codepath.apps.restclienttemplate.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class PhotoHolder2 extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvHandle;
    TextView tvName;
    TextView tvTweetBody;
    TextView tvRelativeTime;
    ImageView ivPhoto1;
    ImageView ivPhoto2;
    TextView tvRetweet;
    ImageView ivRetweetIcon;
    ImageButton ibtnRetweet;
    ImageButton ibtnLike;

    public PhotoHolder2(@NonNull View itemView) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvHandle=itemView.findViewById(R.id.tvHandle);
        tvName=itemView.findViewById(R.id.tvName);
        tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
        tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        ivPhoto1=itemView.findViewById(R.id.ivPhoto1);
        ivPhoto2=itemView.findViewById(R.id.ivPhoto2);
        tvRetweet=itemView.findViewById(R.id.tvRetweet);
        ivRetweetIcon=itemView.findViewById(R.id.ivRetweetIcon);
        ibtnRetweet=itemView.findViewById(R.id.ibtnRetweet);
        ibtnLike=itemView.findViewById(R.id.ibtnLike);
    }

    public void bind(final Tweet tweet, final Context context){
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
                .transform(new CenterCrop(),new RoundedCornersTransformation(30,10))
                .into(ivPhoto1);

        Glide.with(context)
                .load(tweet.tweetMedia.get(1).mediaUrl)
                //.centerCrop()
                .transform(new CenterCrop(),new RoundedCornersTransformation(30,10))
                .into(ivPhoto2);

        if(tweet.favorited)
            ibtnLike.setImageResource(R.drawable.ic_vector_heart);
        else
            ibtnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
        if(tweet.retweeted)
            ibtnRetweet.setImageResource(R.drawable.ic_vector_retweet);
        else
            ibtnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);

        ibtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterClient client=new TwitterClient(context);
                long id;
                if(tweet.isRetweet){
                    id=tweet.retweet_id;
                }else{
                    id=tweet.id;
                }
                if(tweet.favorited){
                    client.postUnlike(id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            ibtnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
                            tweet.favorited=false;
                        }
                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                        }
                    });
                }else{
                    client.postLike(id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            ibtnLike.setImageResource(R.drawable.ic_vector_heart);
                            tweet.favorited=true;
                        }
                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                        }
                    });
                }

            }
        });

        ibtnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterClient client=new TwitterClient(context);
                long id;
                if(tweet.isRetweet){
                    id=tweet.retweet_id;
                }else {
                    id = tweet.id;
                }

                if(tweet.retweeted){
                    client.postUnretweet(id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            ibtnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                            tweet.retweeted=false;
                        }
                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                        }
                    });
                }else{
                    client.postRetweet(id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            ibtnRetweet.setImageResource(R.drawable.ic_vector_retweet);
                            tweet.retweeted=true;
                        }
                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                        }
                    });
                }

            }
        });
    }
}
