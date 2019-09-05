package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.os.Bundle;
import android.text.Html;
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
import com.sardar.softsolstudio.femalehomeworkout.utils.constants;

public class privacyPoliceyFragment extends Fragment {
    View view;
    TextView textView;
    AdView LH_bottom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.privacy_fragment,container,false);
        textView = view.findViewById(R.id.tv_terms);
        textView.setText(Html.fromHtml(constants.termsAndUse));
        MobileAds.initialize(getContext(),getString(R.string.ApAdId));
        AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=view.findViewById(R.id.privacy_bottomAd);
        LH_bottom.loadAd(adRequest);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Privacy Policy");
        super.onViewCreated(view, savedInstanceState);
    }
}
