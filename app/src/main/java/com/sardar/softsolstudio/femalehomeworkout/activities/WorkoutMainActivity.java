package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.models.DaysModel;
import com.sardar.softsolstudio.femalehomeworkout.utils.DatabaseHelper;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WorkoutMainActivity extends AppCompatActivity implements View.OnClickListener {
    String plan="",title="",position="",video_url="";
    Bundle bundle;
    int counter = 1;
    int Total;
    Toolbar toolbar;
    GifImageView gifImageView;
    private Handler handler = new Handler();
    private Handler handler1 = new Handler();
    private boolean runningThread=true;
    Runnable runnable2;
    TextView w_title, w_desc, w_turn, counter_count, video_link,time;
    TextToSpeech textToSpeech;
    TextView titlename,popuptvturn;
    GifImageView nextgifImage;
    TextToSpeech textToSpeech12;
    DatabaseHelper db;
    MediaPlayer mp;
    int maxVolume = 100;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_main_activity);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            plan = bundle.getString("plan");
            title = bundle.getString("title");
            position = bundle.getString("position");
            Log.d("id of Work", "is this" + plan+title+position);
        }
        initialization();
    }

    private void initialization() {
        MobileAds.initialize(this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        interstitialAd =new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitialunitid));
        interstitialAd.loadAd(adRequest);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

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
        textToSpeech12 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech12.setLanguage(Locale.US);

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
        mp = MediaPlayer.create(this, R.raw.music);
        final float volume = (float) (1 - (Math.log(maxVolume - 60.0) / Math.log(maxVolume)));
        mp.setVolume(volume, volume);
        mp.start();
        db = new DatabaseHelper(this);
        final FloatingActionButton fabdone = findViewById(R.id.done_workout);
        fabdone.setOnClickListener(this);
        toolbar = findViewById(R.id.Routine_guide_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        w_title = findViewById(R.id.workout_txt);
        w_desc = findViewById(R.id.Wdetails_text);
        w_turn = findViewById(R.id.W_turns_text);
        video_link = findViewById(R.id.video_link);
        video_link.setOnClickListener(this);
        counter_count = findViewById(R.id.counter_count);
        gifImageView=findViewById(R.id.animation_imageview);
        get_data(counter);
    }

    public void get_data(int counter){
        String json;
        try {
            String path = plan.replaceAll(" ", "");
            Log.d("SINGLE", "PATH" + path);
            InputStream is = getAssets().open(path + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Total = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("id").equals(String.valueOf(counter))) {
                    w_title.setText(jsonObject.getString("display"));
                    w_turn.setText("Truns: " + jsonObject.getString("turns"));
                    counter_count.setText(counter + "/" + jsonArray.length());
                    w_desc.setText(jsonObject.getString("howTo"));
                    video_url=jsonObject.getString("url");
                    try {
                        GifDrawable gifFromAssets = new GifDrawable(getAssets(), jsonObject.getString("name"));
                        gifImageView.setBackground(gifFromAssets);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("Count", String.valueOf(w_title));
                }
                // groupList.add(jsonObject.getString("name"));
                //count++;
                //Log.d("Count", String.valueOf(count));
            }
            if (TextUtils.equals(String.valueOf(counter), "1")) {
            if (!w_turn.getText().toString().isEmpty()) {
                if (w_turn.getText().toString().endsWith("Sec")) {
                    runningThread = true;
                    String turnsValue = w_turn.getText().toString();
                    turnsValue = turnsValue.substring(6, turnsValue.length() - 4);
                    Log.d("Detail activity", "Lets Start next" + turnsValue);
                    TurnsCounter(turnsValue);
                } else {
                    runningThread = true;
                    String turnsValue = w_turn.getText().toString();
                    turnsValue = turnsValue.substring(7);
                    Log.d("Detail activity", "Lets Start next" + turnsValue);
                    TurnsCounter(turnsValue);
                }
            }
        }


            //Toast.makeText(GroupList.this,grouplist.toString(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.done_workout:
                if (counter!=Total){
                    textToSpeech12.shutdown();
                    stopthread();
                    counter=counter+1;
                    showPopup();
                    get_data(counter);
                }else {
                    textToSpeech.shutdown();
                    stopthread();
                    mp.release();
                    if (position != null) {
                        List<DaysModel> daysModel = SharedPrefManager.getInstance(WorkoutMainActivity.this).getWorkdays();
                        ArrayList<DaysModel> DaysModelsList = new ArrayList<DaysModel>();
                        DaysModelsList.addAll(daysModel);
                        String status = DaysModelsList.get(Integer.parseInt(position)).getStatus();
                        Log.d("DetailGuide Activity", "status id" + status);
                        if (TextUtils.equals(status, "false")) {
                            DaysModelsList.get(Integer.parseInt(position)).setStatus("true");
                            SharedPrefManager.getInstance(WorkoutMainActivity.this).RemoveWprkoutDays();
                            if (SharedPrefManager.getInstance(WorkoutMainActivity.this).addWorkDaysToPref(DaysModelsList)) {
                                Log.d("MealPlandone", "Add to Pref");
                                // fabdone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                            }
                        }
                    }
                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    long id = db.insertNote(title, mydate);
                    Log.d("Detail Activity", "Add to Histroy" + mydate);
                    if (id != 0) {
                        Log.d("Detail Activity", "Add to Histroy");
                    }

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent intent = new Intent(WorkoutMainActivity.this, CongratsWorkout.class);
                        intent.putExtra("workout", plan);
                        startActivity(intent);
                        finish();
                    }
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent intent = new Intent(WorkoutMainActivity.this, CongratsWorkout.class);
                            intent.putExtra("workout", plan);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                break;
            case R.id.video_link:
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {

                    Intent intent = new Intent(WorkoutMainActivity.this, YoutubeVideo.class);
                    intent.putExtra("video_id",video_url );
                    startActivity(intent);
                    stopthread();

                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Intent intent = new Intent(WorkoutMainActivity.this, YoutubeVideo.class);
                        intent.putExtra("video_id",video_url );
                        startActivity(intent);
                        stopthread();

                    }
                });

                break;
        }
    }
    public  void stopthread(){
        runningThread = false;

    }
    public void showPopup() {
        mp.start();
        final View popupView = LayoutInflater.from(WorkoutMainActivity.this).inflate(R.layout.popup_rest_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Button skipbtn = (Button) popupView.findViewById(R.id.skip_btn);
        final TextView time = popupView.findViewById(R.id.count_time);
        titlename = popupView.findViewById(R.id.resttv__next_displayname);
        popuptvturn = popupView.findViewById(R.id.resttv_next_turns);
        nextgifImage = popupView.findViewById(R.id.restnext_gifview_workout);
        next_data(counter);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) popupView.findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        int speechStatus = textToSpeech.speak("Next exercise in 30 seconds get ready", TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
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
                        Thread.sleep(300); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        final CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                time.setText(hms);//set text
                if (TextUtils.equals(hms, "00:00:10")) {
                    int speechStatus = textToSpeech.speak("Next exercise in 10 seconds get ready", TextToSpeech.QUEUE_FLUSH, null);
                }
                if (TextUtils.equals(hms, "00:00:03")) {
                    int speechStatus = textToSpeech.speak("3", TextToSpeech.QUEUE_FLUSH, null);
                }
                if (TextUtils.equals(hms, "00:00:02")) {
                    int speechStatus = textToSpeech.speak("2", TextToSpeech.QUEUE_FLUSH, null);
                }
                if (TextUtils.equals(hms, "00:00:01")) {
                    int speechStatus = textToSpeech.speak("1           Lets start", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            public void onFinish() {
                if (!w_turn.getText().toString().isEmpty()) {
                    if (w_turn.getText().toString().endsWith("Sec")){
                        runningThread=true;
                        String turnsValue = w_turn.getText().toString();
                        turnsValue = turnsValue.substring(6,turnsValue.length()-4);
                        Log.d("Detail activity", "Lets Start next"+turnsValue);
                        TurnsCounter(turnsValue);
                    }else {
                        runningThread=true;
                        String turnsValue = w_turn.getText().toString();
                        turnsValue = turnsValue.substring(7);
                        Log.d("Detail activity", "Lets Start next"+turnsValue);
                        TurnsCounter(turnsValue);
                    }
                }
                popupWindow.dismiss();
                int SpeechStatus = textToSpeech.speak("Lets start", TextToSpeech.QUEUE_FLUSH, null);
                textToSpeech.stop();
                if (SpeechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech yeh!");
                }
                //countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();
        popupWindow.setFocusable(true);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                int SpeechStatus = textToSpeech.speak("Lets start", TextToSpeech.QUEUE_FLUSH, null);
                //textToSpeech.stop();
                if (SpeechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech yeh!");
                }
                if (!w_turn.getText().toString().isEmpty()) {
                    if (w_turn.getText().toString().endsWith("Sec")){
                        runningThread=true;
                        String turnsValue = w_turn.getText().toString().trim();
                        turnsValue = turnsValue.substring(6,turnsValue.length()-4);
                        Log.d("Detail activity", "Lets Start next"+turnsValue);
                        TurnsCounter(turnsValue);
                    }else {
                        runningThread=true;
                        String turnsValue = w_turn.getText().toString();
                        turnsValue = turnsValue.substring(7);
                        Log.d("Detail activity", "Lets Start next"+turnsValue);
                        TurnsCounter(turnsValue);
                    }
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }
    public void next_data(int counter){
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
            Total=jsonArray.length();
            for (int i = 0; i<jsonArray.length(); i++ ){
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                if (jsonObject.getString("id").equals(String.valueOf(counter))){
                    titlename.setText(jsonObject.getString("display"));
                    popuptvturn.setText("Truns: "+jsonObject.getString("turns"));
                    try {
                        GifDrawable gifFromAssets = new GifDrawable( getAssets(), jsonObject.getString("name") );
                        nextgifImage.setBackground(gifFromAssets);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("Count", String.valueOf(w_title));
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

    public void TurnsCounter(final String turns) {
        speechReNew();
        mp.start();
        final String path=turns.replaceAll(" ", "");
        Log.d("SINGLE","PATH"+path);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.green_progress_drawable);
        final ProgressBar mProgress = (ProgressBar)findViewById(R.id.mf_progress_bar);
        time = findViewById(R.id.count_turns);
        mProgress.setProgress(0);   // Main Progress
        //mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(Integer.parseInt(path)); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
/*        String value = turns;
        value = value.substring(1);
        final String finalValue = value;*/
        Log.d("Check here the turns", "Value is" + path);
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (!runningThread){
                    return;
                }
                int pStatus = 0;
                // TODO Auto-generated method stub
                while (runningThread && pStatus <= Integer.parseInt(path)) {


                    final int finalPStatus = pStatus;
                    pStatus += 1;
                    handler1.post(runnable2=new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(finalPStatus);
                            time.setText("");
                            time.setText(String.valueOf(finalPStatus));
                            Log.d("Check here the turns", "Value is" + finalPStatus);
                            int SpeechStatuss = textToSpeech12.speak(String.valueOf(finalPStatus), TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(2000); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }
    public void speechReNew() {
        textToSpeech12 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech12.setLanguage(Locale.US);

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

    }
    private void ShowDialogProgress() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to quit");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                textToSpeech.shutdown();
                textToSpeech12.shutdown();
                mp.release();
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowDialogProgress();
        textToSpeech.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!w_turn.getText().toString().isEmpty()) {
            if (w_turn.getText().toString().endsWith("Sec")){
                runningThread=true;
                String turnsValue = w_turn.getText().toString();
                turnsValue = turnsValue.substring(6,turnsValue.length()-4);
                Log.d("Detail activity", "Lets Start next"+turnsValue);
                TurnsCounter(turnsValue);
            }else {
                runningThread=true;
                String turnsValue = w_turn.getText().toString();
                turnsValue = turnsValue.substring(7);
                Log.d("Detail activity", "Lets Start next"+turnsValue);
                TurnsCounter(turnsValue);
            }
        }
    }
}
