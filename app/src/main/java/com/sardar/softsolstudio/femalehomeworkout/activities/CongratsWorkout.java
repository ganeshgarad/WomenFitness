package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sardar.softsolstudio.femalehomeworkout.R;

import java.util.Locale;

public class CongratsWorkout extends AppCompatActivity {
    Button completed_next_btn;
    TextView workout_name;
    Bundle bundle;
    String workoutName="";
    TextToSpeech textToSpeech;
    MediaPlayer mp;
    //AdView LH_bottom;
    //InterstitialAd interstitialAd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrats_workout);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            workoutName=bundle.getString("workout");
        }
        workout_name=findViewById(R.id.workout_name);
        if (TextUtils.equals(workoutName,"1")){
            workout_name.setText("Morning Stretch");
        }else if (TextUtils.equals(workoutName,"2")){
            workout_name.setText("Evening Stretch");
        }else {
            workout_name.setText(workoutName);
        }
        mp = MediaPlayer.create(this, R.raw.cheer);
        mp.start();
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    int SpeechStatus = textToSpeech.speak("Congratulation", TextToSpeech.QUEUE_FLUSH, null);

                    if (SpeechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech yeh!");
                    }
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

     /*   MobileAds.initialize(CompleteWorkout.this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=findViewById(R.id.finish_work_bottomAd);
        LH_bottom.loadAd(adRequest);
        interstitialAd =new InterstitialAd(CompleteWorkout.this);
        interstitialAd.setAdUnitId(getString(R.string.interstitialunitid));
        interstitialAd.loadAd(adRequest);*/
        completed_next_btn=findViewById(R.id.completed_next_btn);
        completed_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {
                    finish();
                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        finish();
                    }
                });*/
             finish();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }
}
