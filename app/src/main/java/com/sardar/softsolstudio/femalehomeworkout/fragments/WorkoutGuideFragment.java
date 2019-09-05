package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.activities.MealPlan;
import com.sardar.softsolstudio.femalehomeworkout.utils.constants;

public class WorkoutGuideFragment extends Fragment implements Html.ImageGetter {
    View view;
    TextView textView;
    AdView LH_bottom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.workout_guide_fragment,container,false);
        textView = view.findViewById(R.id.tv_guideline);
        Spanned spanned = Html.fromHtml(constants.guide, this, null);
        //textView.setText(Html.fromHtml(constants.guide));
        textView.setText(spanned);
        MobileAds.initialize(getContext(),getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=view.findViewById(R.id.LI_bottomAd);
        LH_bottom.loadAd(adRequest);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Workout Guide");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public Drawable getDrawable(String s) {
        // TODO Auto-generated method stub
        int id = 0;

        if(s.equals("guide1.png")){
            id = R.drawable.guide1;
        }

        if(s.equals("guide2.png")){
            id = R.drawable.guide2;
        }
        if(s.equals("chest.jpg")){
            id = R.drawable.chest;
        }
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(10, 10, 640, 250);

        return d;
    }
}
