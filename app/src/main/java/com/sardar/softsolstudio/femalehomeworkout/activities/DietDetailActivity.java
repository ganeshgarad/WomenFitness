package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.models.DaysModel;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DietDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button finish_btn;
    String day="",postioon="";
    String breakfast="",snack="",lunch="",dinner="";
    TextView tvbreakfast,tvsnack,tvlunch,tvdinner;
    //AdView LH_bottom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diat_detail_activity);
/*        MobileAds.initialize(diet_activity_detail.this,getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=findViewById(R.id.diet_detail_bottomAd);
        LH_bottom.loadAd(adRequest);*/
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            day=bundle.getString("day");
            postioon=bundle.getString("position");
            Log.d("Diet activity","Deatial  "+day+postioon);
        }
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(day);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //finish_btn=findViewById(R.id.done_diet);
        final FloatingActionButton fabdone = findViewById(R.id.done_diet);
        // FloatingActionButton fabreomove = findViewById(R.id.remove_diet);
        List<DaysModel> daysModel = SharedPrefManager.getInstance(DietDetailActivity.this).getdays();
        ArrayList<DaysModel> DaysModelsList=new ArrayList<DaysModel>();
        DaysModelsList.addAll(daysModel);
        String status=DaysModelsList.get(Integer.parseInt(postioon)).getStatus();
        if (TextUtils.equals(status,"true")){
            fabdone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        }else {
            fabdone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.removediet)));
        }
        tvbreakfast=findViewById(R.id.breakfast_detail);
        tvsnack=findViewById(R.id.snack_detail);
        tvlunch=findViewById(R.id.lunch_detail);
        tvdinner=findViewById(R.id.dinner_detail);
        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<DaysModel> daysModel = SharedPrefManager.getInstance(DietDetailActivity.this).getdays();
                ArrayList<DaysModel> DaysModelsList=new ArrayList<DaysModel>();
                DaysModelsList.addAll(daysModel);
                String status=DaysModelsList.get(Integer.parseInt(postioon)).getStatus();
                Log.d("Diet activity","status"+status);
                if (TextUtils.equals(status,"true")){
                    DaysModelsList.get(Integer.parseInt(postioon)).setStatus("false");
                    SharedPrefManager.getInstance(DietDetailActivity.this).RemoveDays();
                    if (SharedPrefManager.getInstance(DietDetailActivity.this).addDaysToPref(DaysModelsList)){
                        Log.d("MealPlanremove","Add to Pref");
                        fabdone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.removediet)));

                    }
                }else if (TextUtils.equals(status,"false")){
                    DaysModelsList.get(Integer.parseInt(postioon)).setStatus("true");
                    SharedPrefManager.getInstance(DietDetailActivity.this).RemoveDays();
                    if (SharedPrefManager.getInstance(DietDetailActivity.this).addDaysToPref(DaysModelsList)){
                        Log.d("MealPlandone","Add to Pref");
                        fabdone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    }
                }

            }
        });
        getDietDetail();
        bindData();
    }

    private void bindData() {
        if (!breakfast.isEmpty() && !snack.isEmpty() && !lunch.isEmpty() && !dinner.isEmpty()) {
            tvbreakfast.setText(breakfast);
            tvsnack.setText(snack);
            tvlunch.setText(lunch);
            tvdinner.setText(dinner);
        }else {
            Log.d("diet detail activity","data not fount");
        }
    }

    private void getDietDetail() {
        String json;
        try {

            InputStream is=getAssets().open("diet.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();

            json =new String(buffer,"UTF-8");
            JSONArray jsonArray=new JSONArray(json);

            for (int i = 0; i<jsonArray.length(); i++ ){
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                if (jsonObject.getString("Day").equals(day)){
                    breakfast=jsonObject.getString("breakfast");
                    snack=jsonObject.getString("snack");
                    lunch=jsonObject.getString("lunch");
                    dinner=jsonObject.getString("dinner");
                    Log.d("Count", String.valueOf(day));
                }

            }


            //Toast.makeText(GroupList.this,grouplist.toString(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "catch error"+e, Toast.LENGTH_SHORT).show();
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

}
