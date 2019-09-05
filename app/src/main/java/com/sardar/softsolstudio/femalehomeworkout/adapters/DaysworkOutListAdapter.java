package com.sardar.softsolstudio.femalehomeworkout.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.fragments.RestDayFragment;
import com.sardar.softsolstudio.femalehomeworkout.fragments.SingleWorkOutList;
import com.sardar.softsolstudio.femalehomeworkout.models.DaysModel;

import java.util.ArrayList;

public class DaysworkOutListAdapter extends RecyclerView.Adapter<DaysworkOutListAdapter.ViewHolder> {
    Context context;
    ArrayList<DaysModel>list;
    public DaysworkOutListAdapter(Context context, ArrayList<DaysModel> daysModelsList) {
        this.context=context;
        this.list=daysModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view_holder_days,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DaysModel daysModel=list.get(position);
        final String status=daysModel.getStatus().toString();
        holder.daytext.setText(daysModel.getDay());
        if (TextUtils.equals(status,"true")){
            holder.fabdone.setImageResource(R.drawable.ic_tick_done);

        }else if (TextUtils.equals(status,"false")){
            holder.fabdone.setImageResource(R.drawable.ic_tick_not);

        }else if (TextUtils.equals(status,"coffee")){
            holder.fabdone.setImageResource(R.drawable.ic_relax);

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(TextUtils.equals(status,"coffee"))){
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new SingleWorkOutList();
                    Bundle args = new Bundle();
                    args.putString("plan", daysModel.getDay());
                    args.putString("position",String.valueOf(position));
                    args.putString("lebal",daysModel.getPlan());
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, myFragment).addToBackStack(null).commit();
                }else {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new RestDayFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, myFragment).addToBackStack("coffee").commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView daytext;
        ImageView fabdone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.day_workout_cardview);
            daytext=itemView.findViewById(R.id.tv_day_workout);
            fabdone =itemView.findViewById(R.id.done_day_workout);
        }
    }
}
