package com.sardar.softsolstudio.femalehomeworkout.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.activities.HistoryCalenderFragment;
import com.sardar.softsolstudio.femalehomeworkout.models.HistoryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<HistoryModel> list;

    public HistoryAdapter(HistoryCalenderFragment calenderFragment, ArrayList<HistoryModel> arrayList) {
        this.context = calenderFragment;
        this.list = arrayList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_view_holder, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, int i) {
        final HistoryModel model=list.get(i);
        if (TextUtils.equals(model.getDaytitle(),"1")){
            viewHolder.tv_day.setText("Morning Stretch");
        }else if (TextUtils.equals(model.getDaytitle(),"2")){
            viewHolder.tv_day.setText("Evening Stretch");
        }else {
            viewHolder.tv_day.setText(model.getDaytitle());
        }
        viewHolder.tv_date_time.setText(model.getDate());
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
        TextView tv_day, tv_date_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_day = itemView.findViewById(R.id.history_day);
            tv_date_time = itemView.findViewById(R.id.history_date_time);
        }
    }
}
