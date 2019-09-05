package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.models.WorkoutDetailModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ReadyToStart extends AppCompatActivity {
    TextToSpeech textToSpeech;
    //DetailGuideActivity activity;
    String WorkId = "", plan = "",title="",position="";
    CountDownTimer countDownTimer;
    Bundle bundle;
    TextView next_display_name,next_turns;
    GifImageView gifImageView;
    private Handler handler = new Handler();
    NativeExpressAdView nativeExpressAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_to_start);
        bundle = getIntent().getExtras();
        if (bundle != null) {
          //  WorkId = bundle.getString("ID");
            plan = bundle.getString("plan");
            title = bundle.getString("title");
            position = bundle.getString("position");
            Log.d("id of Work", "is this" + WorkId + plan);
        }
        nativeExpressAdView=(NativeExpressAdView) findViewById(R.id.nativead);
        MobileAds.initialize(ReadyToStart.this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        //nativeExpressAdView.setAdSize(new AdSize(280, 180));
        //nativeExpressAdView.setAdUnitId("ca-app-pub-3940256099942544/2247696110");

        //activity=new DetailGuideActivity();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.whistle_double);
        mp.start();
        next_display_name=findViewById(R.id.tv__next_displayname);
        next_turns=findViewById(R.id.tv_next_turns);
        gifImageView=findViewById(R.id.next_gifview_workout);
        get_data();
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    int speechStatus = textToSpeech.speak("Ready to go", TextToSpeech.QUEUE_FLUSH, null);
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
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar)findViewById(R.id.circularProgressbarReady);
        final TextView time = findViewById(R.id.count_time_ready);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        /*int speechStatus = textToSpeech.speak("Ready to go", TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }*/
        new Thread(new Runnable() {

            @Override
            public void run() {
                int pStatus = 0;
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    final int finalPStatus = pStatus;
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(finalPStatus);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(100); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                time.setText(hms);//set text
                if (TextUtils.equals(hms,"00:00:03")){
                    int speechStatus = textToSpeech.speak("3", TextToSpeech.QUEUE_FLUSH, null);
                }
                if (TextUtils.equals(hms,"00:00:02")){
                    int speechStatus = textToSpeech.speak("2", TextToSpeech.QUEUE_FLUSH, null);
                }
                if (TextUtils.equals(hms,"00:00:01")){
                    int speechStatus = textToSpeech.speak("1", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            public void onFinish() {
                //((DetailGuideActivity)activity).FirstCheck();

                // Intent intent=new Intent(getContext(), DetailGuideActivity.class);

                Intent intent=new Intent(ReadyToStart.this, WorkoutMainActivity.class);
                intent.putExtra("plan",plan);
                intent.putExtra("title",title);
                intent.putExtra("position",position);
                startActivity(intent);
                finish();


                int SpeechStatus = textToSpeech.speak("Lets start", TextToSpeech.QUEUE_FLUSH, null);
                //textToSpeech.stop();
                if (SpeechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech yeh!");
                }

                //countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();
        nativeExpressAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        textToSpeech.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // textToSpeech.stop();
    }
    public void get_data(){
        String json;
        try {
            String path=plan.replaceAll(" ", "");
            Log.d("SINGLE","PATH"+path);
            InputStream is=getAssets().open(path+".json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();

            json =new String(buffer,"UTF-8");
            JSONArray jsonArray=new JSONArray(json);

            for (int i = 0; i<jsonArray.length(); i++ ){
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                if (jsonObject.getString("id").equals("1")){
                    next_display_name.setText(jsonObject.getString("display"));
                    next_turns.setText(jsonObject.getString("turns"));
                    try {
                        GifDrawable gifFromAssets = new GifDrawable( getAssets(), jsonObject.getString("name") );
                        gifImageView.setBackground(gifFromAssets);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("Count", String.valueOf(next_display_name));
                }
                // groupList.add(jsonObject.getString("name"));
                //count++;
                //Log.d("Count", String.valueOf(count));
            }


            //Toast.makeText(GroupList.this,grouplist.toString(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
