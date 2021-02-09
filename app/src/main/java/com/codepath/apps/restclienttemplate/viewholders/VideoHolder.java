package com.codepath.apps.restclienttemplate.viewholders;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.codepath.apps.restclienttemplate.models.Tweet;

public class VideoHolder extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvHandle;
    TextView tvName;
    TextView tvTweetBody;
    TextView tvRelativeTime;
    VideoView vvVideo;
    Context context;



    public VideoHolder(@NonNull View itemView,Context context) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvHandle=itemView.findViewById(R.id.tvHandle);
        tvName=itemView.findViewById(R.id.tvName);
        tvTweetBody=itemView.findViewById(R.id.tvTweetBody);
        tvRelativeTime=itemView.findViewById(R.id.tvRelativeTime);
        vvVideo=itemView.findViewById(R.id.vvVideo);
        this.context=context;

    }

    public void bind(Tweet tweet, Context context){
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


    }
}
