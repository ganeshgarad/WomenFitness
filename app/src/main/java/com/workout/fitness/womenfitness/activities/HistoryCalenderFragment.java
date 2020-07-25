package com.workout.fitness.womenfitness.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.workout.fitness.womenfitness.R;
import com.workout.fitness.womenfitness.adapters.HistoryAdapter;
import com.workout.fitness.womenfitness.models.HistoryModel;
import com.workout.fitness.womenfitness.utils.DatabaseHelper;
import com.workout.fitness.womenfitness.utils.MyEventDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryCalenderFragment extends AppCompatActivity {
    View view;
    ArrayList<HistoryModel> arrayList = new ArrayList<>();
    ArrayList<HistoryModel> temparray = new ArrayList<>();
    DatabaseHelper db;
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    Date date1;
    Activity mactivity;
    RecyclerView recyclerView;
    //AdView LH_bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_activity);
        initailization();
    }

    private void initailization() {
     /*   MobileAds.initialize(CalenderFragment.this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=findViewById(R.id.calender_bottomAd);
        LH_bottom.loadAd(adRequest);*/
        db = new DatabaseHelper(HistoryCalenderFragment.this);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        arrayList.addAll(db.getAllNotes());
        if (arrayList!=null){
            for (int i=0; i<arrayList.size();i++){
                HistoryModel model=arrayList.get(i);
                String date=model.getDate();
                Log.d("Calender","Check kr edr kia ha"+date);
                mEventDays.add(new MyEventDay(getCalenderDay(date),R.drawable.check_calender,"ok"));

            }
        }
        Log.d("CalenderActivity","DATE"+mCalendarView.getSelectedDate());
        Log.d("Calender Date","Check out yar"+mEventDays.size());
        mCalendarView.setEvents(mEventDays);
        recyclerView = findViewById(R.id.history_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (arrayList != null) {
            HistoryAdapter adapter = new HistoryAdapter(this, arrayList);
            recyclerView.setAdapter(adapter);
            Log.d("MealPlan", "Adpter Set");
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }



    private Calendar getCalenderDay(String date) {

/*        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        try {
            date1 = sdf.parse(date);
            Log.d("Calender Date","Check out this"+date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Calender Date","Check out this error"+date);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        return calendar;*/
        SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd-M-yyyy");
        date = spf.format(newDate);
        try {
            date1=spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("Calender","Activity ok ok"+date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        return calendar;
    }
}