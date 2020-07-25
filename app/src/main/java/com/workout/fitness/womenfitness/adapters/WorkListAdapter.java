package com.workout.fitness.womenfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workout.fitness.womenfitness.R;
import com.workout.fitness.womenfitness.models.WorkoutDetailModel;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> {
    List<WorkoutDetailModel> list;
    Context context;

    public WorkListAdapter(Context context, List<WorkoutDetailModel> workoutList) {
        this.context = context;
        this.list = workoutList;
    }

    @NonNull
    @Override
    public WorkListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkListAdapter.ViewHolder holder, int position) {
        WorkoutDetailModel model=list.get(position);
        holder.displayName.setText(model.getDisplayName());
        holder.turns.setText("Turns: "+model.getTurns());
        try {
            GifDrawable gifFromAssets = new GifDrawable( context.getAssets(), model.getName() );
            holder.gifImageView.setBackground(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayName,turns;
        GifImageView gifImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName=itemView.findViewById(R.id.tv_displayname);
            turns=itemView.findViewById(R.id.turns_tv);
            gifImageView=itemView.findViewById(R.id.gifview_workout);
        }

    }
}
