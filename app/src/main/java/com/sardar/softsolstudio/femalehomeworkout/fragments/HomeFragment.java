package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.activities.ReadyToStart;

public class HomeFragment extends Fragment implements View.OnClickListener{
    View view;
    CardView daysChallenge,abs_beginner,abs_inter,abs_advance,arm_cardview,butt_beginner,butt_inter,butt_advance,chest_cardview,leg_beginner,leg_inter,leg_advance;
    AdView ad_bottom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        MobileAds.initialize(getContext(),getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        ad_bottom=view.findViewById(R.id.homebottom);
        ad_bottom.loadAd(adRequest);
        abs_beginner=view.findViewById(R.id.abs_beginner_cardview);
        abs_inter=view.findViewById(R.id.abs_inter_cardview);
        abs_advance=view.findViewById(R.id.abs_advance_cardview);
        arm_cardview=view.findViewById(R.id.arms_cardview);
        butt_advance=view.findViewById(R.id.butt_advance_cardview);
        butt_beginner=view.findViewById(R.id.butt_beginner_cardview);
        butt_inter=view.findViewById(R.id.butt_inter_cardview);
        chest_cardview=view.findViewById(R.id.chest_cardview);
        leg_advance=view.findViewById(R.id.leg_advance_cardview);
        leg_beginner=view.findViewById(R.id.leg_beginner_cardview);
        leg_inter=view.findViewById(R.id.leg_inter_cardview);
        daysChallenge=view.findViewById(R.id.day_challenge_cardview);
        daysChallenge.setOnClickListener(this);
        abs_beginner.setOnClickListener(this);
        abs_inter.setOnClickListener(this);
        abs_advance.setOnClickListener(this);
        arm_cardview.setOnClickListener(this);
        butt_inter.setOnClickListener(this);
        butt_beginner.setOnClickListener(this);
        butt_advance.setOnClickListener(this);
        chest_cardview.setOnClickListener(this);
        leg_inter.setOnClickListener(this);
        leg_beginner.setOnClickListener(this);
        leg_advance.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.abs_beginner_cardview:
                CallFragment("ABS beginner");
                break;
            case R.id.abs_inter_cardview:
                CallFragment("ABS intermediate");
                break;
            case R.id.abs_advance_cardview:
                CallFragment("ABS advanced");
                break;
            case R.id.arms_cardview:
                CallFragment("ARM workout");
                break;
            case R.id.butt_beginner_cardview:
                CallFragment("BUTT beginner");
                break;
            case R.id.butt_advance_cardview:
                CallFragment("BUTT advanced");
                break;
            case R.id.butt_inter_cardview:
                CallFragment("BUTT intermediate");
                break;
            case R.id.leg_beginner_cardview:
                CallFragment("LEG beginner");
                break;
            case R.id.leg_inter_cardview:
                CallFragment("LEG intermediate");
                break;
            case R.id.leg_advance_cardview:
                CallFragment("LEG advanced");
                break;
            case R.id.chest_cardview:
                CallFragment("CHEST workout");
                break;
            case R.id.day_challenge_cardview:
                DaysWorkoutListFragment fragment3=new DaysWorkoutListFragment();
                Bundle args3 = new Bundle();
                args3.putString("lebal", "Day Challenge");
                fragment3.setArguments(args3);
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.main_frame, fragment3);
                fragmentTransaction3.addToBackStack("forgetpass_fragment");
                fragmentTransaction3.commit();
                break;

        }
    }

    private void CallFragment(String workout) {
        SingleWorkOutList fragment3=new SingleWorkOutList();
        Bundle args3 = new Bundle();
        args3.putString("lebal", workout);
        args3.putString("plan", workout);
        fragment3.setArguments(args3);
        FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.main_frame, fragment3);
        fragmentTransaction3.addToBackStack("forgetpass_fragment");
        fragmentTransaction3.commit();
    }
}
