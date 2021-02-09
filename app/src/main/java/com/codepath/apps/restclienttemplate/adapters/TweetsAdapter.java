package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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





            //if(tweet.hasMedia){
                //check media type and then load it
               // this.bindMedia(tweet);
            //}

        }
/*
        private void bindMedia(Tweet tweet) {
            if(tweet.tweetMedia.get(0).mediaType.equals("photo")){
                switch (tweet.tweetMedia.size()){
                    case 1:
                        Glide.with(context).
                                load(tweet.tweetMedia.get(0).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(400,200).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia1));
                        itemView.findViewById(R.id.ivMedia1).setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        Glide.with(context).
                                load(tweet.tweetMedia.get(0).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,200).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia1));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(1).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,200).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia2));
                        itemView.findViewById(R.id.ivMedia1).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia2).setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        Glide.with(context).
                                load(tweet.tweetMedia.get(0).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia1));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(2).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia3));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(1).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,200).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia2));

                        itemView.findViewById(R.id.ivMedia1).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia2).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia3).setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        Glide.with(context).
                                load(tweet.tweetMedia.get(0).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia1));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(2).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia3));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(1).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia2));
                        Glide.with(context).
                                load(tweet.tweetMedia.get(1).mediaUrl).
                                transform(new RoundedCornersTransformation(30,10)).
                                //override(200,100).
                                fitCenter().
                                into((ImageView) itemView.findViewById(R.id.ivMedia4));

                        itemView.findViewById(R.id.ivMedia1).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia2).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia3).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivMedia4).setVisibility(View.VISIBLE);

                        break;
                }
            }

            /*for(int i=0;i<tweet.tweetMedia.size();i++){
                if(tweet.tweetMedia.get(0).mediaType.equals("photo")){
                    switch(i){
                        case 0:
                            Glide.with(context).
                                    load(tweet.tweetMedia.get(i).mediaUrl).
                                    transform(new RoundedCornersTransformation(30,10)).
                                    //override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).
                                    fitCenter().
                                    into((ImageView) itemView.findViewById(R.id.ivMedia1));

                            itemView.findViewById(R.id.ivMedia1).setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            Glide.with(context).
                                    load(tweet.tweetMedia.get(i).mediaUrl).
                                    transform(new RoundedCornersTransformation(30,10)).
                                    //override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).
                                    fitCenter().
                                    into((ImageView) itemView.findViewById(R.id.ivMedia2));

                            itemView.findViewById(R.id.ivMedia2).setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Glide.with(context).
                                    load(tweet.tweetMedia.get(i).mediaUrl).
                                    transform(new RoundedCornersTransformation(30,10)).
                                    //override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).
                                    fitCenter().
                                    into((ImageView) itemView.findViewById(R.id.ivMedia3));

                            itemView.findViewById(R.id.ivMedia3).setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            Glide.with(context).
                                    load(tweet.tweetMedia.get(i).mediaUrl).
                                    transform(new RoundedCornersTransformation(30,10)).
                                    //override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).
                                    fitCenter().
                                    into((ImageView) itemView.findViewById(R.id.ivMedia4));

                            itemView.findViewById(R.id.ivMedia4).setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

        }*/

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
