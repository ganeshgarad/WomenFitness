package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.adapters.DaysAdapter;
import com.sardar.softsolstudio.femalehomeworkout.models.DaysModel;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MealPlan extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView Daysrecycler;
    ArrayList<DaysModel> DaysModelsList;
    AdView LH_bottom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_activity);
        initialization();
    }

    private void initialization() {
        MobileAds.initialize(MealPlan.this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=findViewById(R.id.meal_plan_bottomAd);
        LH_bottom.loadAd(adRequest);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Meal Plan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Daysrecycler = findViewById(R.id.days_recycler_view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealPlan.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MealPlan.this,3);
        Daysrecycler.setLayoutManager(gridLayoutManager);
        DaysModelsList = new ArrayList<DaysModel>();
        List<DaysModel> daysModel = SharedPrefManager.getInstance(this).getdays();
        if (daysModel!=null){
            DaysModelsList.addAll(daysModel);
            Log.d("MealPlane","sharedpref not null");
        }else {
            for (int i=1;i<=30;i++){
                DaysModel days=new DaysModel();
                days.setDay("Day "+i);
                days.setStatus("false");
                DaysModelsList.add(days);
            }
            if (SharedPrefManager.getInstance(MealPlan.this).addDaysToPref(DaysModelsList)){
                Log.d("MealPlan","Add to Pref");
            }

        }
        if (DaysModelsList != null) {
            DaysAdapter adapter = new DaysAdapter(MealPlan.this, DaysModelsList);
            Daysrecycler.setAdapter(adapter);
            Log.d("MealPlan", "Adpter Set");
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaysModelsList.clear();
        DaysModelsList = new ArrayList<DaysModel>();
        List<DaysModel> daysModel = SharedPrefManager.getInstance(this).getdays();
        if (daysModel!=null){
            DaysModelsList.addAll(daysModel);
            Log.d("MealPlane","sharedpref not null");
            DaysAdapter adapter = new DaysAdapter(MealPlan.this, DaysModelsList);
            Daysrecycler.setAdapter(adapter);
            Log.d("MealPlan", "Adpter Set");
        }
    }
}
