package com.workout.fitness.womenfitness.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.workout.fitness.womenfitness.R;

public class YoutubeVideo extends YouTubeBaseActivity {
    Activity myactivty;
    String video_id="";
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video);
        myactivty=YoutubeVideo.this;
        bundle=getIntent().getExtras();
        if (bundle!=null){
            video_id=bundle.getString("video_id");
            Log.d("Watch_video","URL"+video_id);
        }

        //setSupportActionBar(binding.toolbar);

        final YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        if (!video_id.isEmpty()){
            playVideo(video_id, youTubePlayerView);
        }
    }
    private void playVideo(final String s, YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.initialize("AIzaSyASH6dzv8D-2N2EPq7Iiw6ymUST2UmtLRg", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("url",s);
                youTubePlayer.cueVideo(s);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        video_id="";
    }
}
