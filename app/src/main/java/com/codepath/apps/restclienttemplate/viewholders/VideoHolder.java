package com.codepath.apps.restclienttemplate.viewholders;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class VideoHolder extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvHandle;
    TextView tvName;
    TextView tvTweetBody;
    TextView tvRelativeTime;
    VideoView vvVideo;
    Context context;
    TextView tvRetweet;
    ImageView ivRetweetIcon;
    ImageButton ibtnRetweet;
    ImageButton ibtnLike;



    public VideoHolder(@NonNull View itemView,Context context) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvHandle=itemView.findViewById(R.id.tvHandle);
        tvName=itemView.findViewById(R.id.tvName);
        tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
        tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        vvVideo=itemView.findViewById(R.id.vvVideo);
        ibtnRetweet=itemView.findViewById(R.id.ibtnRetweet);
        ibtnLike=itemView.findViewById(R.id.ibtnLike);
        tvRetweet=itemView.findViewById(R.id.tvRetweet);
        ivRetweetIcon=itemView.findViewById(R.id.ivRetweetIcon);
        this.context=context;

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

        //check if gif or video, if gif keep replaying
        final String type = tweet.mediaType;
        vvVideo.setVideoPath(tweet.tweetMedia.get(0).mediaUrl);

            vvVideo.start();
        if(type.equals("video")){
            vvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vvVideo.isPlaying()){
                        vvVideo.pause();
                    }else{
                        vvVideo.start();
                    }
                }
            });
        }

        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vvVideo.start();
            }
        });

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
