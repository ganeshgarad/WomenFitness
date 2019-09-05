package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.adapters.DaysworkOutListAdapter;
import com.sardar.softsolstudio.femalehomeworkout.models.DaysModel;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class DaysWorkoutListFragment extends Fragment {
    View view;
    RecyclerView Daysrecycler;
    ArrayList<DaysModel> DaysModelsList;
    AdView LH_bottom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.days_list_fragment,container,false);
        inital();
        return view;
    }

    private void inital() {
        MobileAds.initialize(getContext(),getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=view.findViewById(R.id.daysworkout_bottomAd);
        LH_bottom.loadAd(adRequest);
        Daysrecycler = view.findViewById(R.id.days_workout_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        Daysrecycler.setLayoutManager(linearLayoutManager);
        DaysModelsList = new ArrayList<DaysModel>();
        List<DaysModel> daysModel = SharedPrefManager.getInstance(getContext()).getWorkdays();
        if (daysModel!=null){
            DaysModelsList.addAll(daysModel);
            Log.d("MealPlane","sharedpref not null");
        }else {
            for (int i=1;i<=28;i++){
                DaysModel days=new DaysModel();
                if (i == 1 || i==7){
                    days.setPlan("plan1");
                }else if (i==2 || i==8){
                    days.setPlan("plan2");
                }else if (i==3 || i==22 ){
                    days.setPlan("plan3");
                }else if (i==25 || i==11){
                    days.setPlan("plan4");
                }else if (i==5 || i==27 || i==20){
                    days.setPlan("plan5");
                }else if (i==26 || i==17 || i==4){
                    days.setPlan("plan6");
                }else if (i==9 || i==23 || i==15){
                    days.setPlan("plan7");
                }else if (i==10 || i==21){
                    days.setPlan("plan8");
                }else if (i==13 || i==16 || i==28){
                    days.setPlan("plan9");
                }else if (i==14 || i==19){
                    days.setPlan("plan10");
                }
                days.setDay("Day "+i);
                if (i==6 || i==12 || i==18 || i==24 ){
                    days.setStatus("coffee");
                }else {
                    days.setStatus("false");
                }
                DaysModelsList.add(days);
            }
            if (SharedPrefManager.getInstance(getContext()).addWorkDaysToPref(DaysModelsList)){
                Log.d("MealPlan","Add to Pref");
            }

        }
        if (DaysModelsList != null) {
            DaysworkOutListAdapter adapter = new DaysworkOutListAdapter(getContext(), DaysModelsList);
            Daysrecycler.setAdapter(adapter);
            Log.d("MealPlan", "Adpter Set");
        } else {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Full Body Challenge");
        super.onViewCreated(view, savedInstanceState);
    }
}
