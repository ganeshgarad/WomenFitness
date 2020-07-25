package com.workout.fitness.womenfitness.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.workout.fitness.womenfitness.R;
import com.workout.fitness.womenfitness.activities.DietDetailActivity;
import com.workout.fitness.womenfitness.activities.MealPlan;
import com.workout.fitness.womenfitness.models.DaysModel;

import java.util.ArrayList;

public class DaysAdapter  extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    Context context;
    ArrayList<DaysModel> list;
    public DaysAdapter(MealPlan mealPlan, ArrayList<DaysModel> daysModelsList) {
        this.context=mealPlan;
        this.list=daysModelsList;
    }

    @NonNull
    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.days_view_holder,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysAdapter.ViewHolder viewHolder, final int i) {
        final DaysModel daysModel=list.get(i);
        String status=daysModel.getStatus().toString();
        viewHolder.daytext.setText(daysModel.getDay());
        if (TextUtils.equals(status,"true")){
            viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DietDetailActivity.class);
                intent.putExtra("day",daysModel.getDay());
                intent.putExtra("position",String.valueOf(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView daytext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.day_cardview);
            daytext=itemView.findViewById(R.id.tv_day);

        }
    }

}
