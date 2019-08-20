package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sardar.softsolstudio.femalehomeworkout.R;

public class HomeFragment extends Fragment implements View.OnClickListener{
    View view;
    CardView abs_beginner,abs_inter,abs_advance,arm_cardview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment,container,false);
        initialization();
        return view;
    }

    private void initialization() {
        abs_beginner=view.findViewById(R.id.abs_beginner_cardview);
        abs_inter=view.findViewById(R.id.abs_inter_cardview);
        abs_advance=view.findViewById(R.id.abs_advance_cardview);
        arm_cardview=view.findViewById(R.id.arms_cardview);
        abs_beginner.setOnClickListener(this);
        abs_inter.setOnClickListener(this);
        abs_advance.setOnClickListener(this);
        arm_cardview.setOnClickListener(this);
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
                SingleWorkOutList fragment=new SingleWorkOutList();
                Bundle args = new Bundle();
                args.putString("lebal", "ABS beginner");
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.addToBackStack("forgetpass_fragment");
                fragmentTransaction.commit();
                break;
            case R.id.abs_inter_cardview:
                SingleWorkOutList fragment1=new SingleWorkOutList();
                Bundle args1 = new Bundle();
                args1.putString("lebal", "ABS intermediate");
                fragment1.setArguments(args1);
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.main_frame, fragment1);
                fragmentTransaction1.addToBackStack("forgetpass_fragment");
                fragmentTransaction1.commit();
                break;
            case R.id.abs_advance_cardview:
                SingleWorkOutList fragment2=new SingleWorkOutList();
                Bundle args2 = new Bundle();
                args2.putString("lebal", "ABS advanced");
                fragment2.setArguments(args2);
                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.main_frame, fragment2);
                fragmentTransaction2.addToBackStack("forgetpass_fragment");
                fragmentTransaction2.commit();
                break;
            case R.id.arms_cardview:
                SingleWorkOutList fragment3=new SingleWorkOutList();
                Bundle args3 = new Bundle();
                args3.putString("lebal", "ARM workout");
                fragment3.setArguments(args3);
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.main_frame, fragment3);
                fragmentTransaction3.addToBackStack("forgetpass_fragment");
                fragmentTransaction3.commit();
                break;
        }
    }
}
