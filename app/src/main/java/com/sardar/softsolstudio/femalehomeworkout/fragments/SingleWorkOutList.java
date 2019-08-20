package com.sardar.softsolstudio.femalehomeworkout.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.adapters.WorkListAdapter;
import com.sardar.softsolstudio.femalehomeworkout.models.WorkoutDetailModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SingleWorkOutList extends Fragment implements View.OnClickListener{
    View view;
    RecyclerView recyclerViewSingle;
    Button StartBtn;
    String exercise="";
    List<WorkoutDetailModel> WorkoutList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.single_workout_list_fragment,container,false);
        if (getArguments() != null) {
            exercise = getArguments().getString("lebal");
            Log.d("singin", "LOVE" + exercise);
        } else {
            Toast.makeText(getActivity(), "Basic info not save", Toast.LENGTH_SHORT).show();
        }
        intial();
        get_data();
        return view;
    }

    private void intial() {
        WorkoutList=new ArrayList<>();
        StartBtn=view.findViewById(R.id.routine_start_button);
        StartBtn.setOnClickListener(this);
        recyclerViewSingle = view.findViewById(R.id.single_workout_recyclerview);
        recyclerViewSingle.setLayoutManager(new LinearLayoutManager(getContext()));
        WorkListAdapter workListAdapter=new WorkListAdapter(getContext(),WorkoutList);
        recyclerViewSingle.setAdapter(workListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.routine_start_button:
                break;
        }
    }

    public void get_data(){
        String json;
        try {
            String path=exercise.replaceAll(" ", "");
            Log.d("SINGLE","PATH"+path);
            InputStream is=getActivity().getAssets().open(path+".json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();

            json =new String(buffer,"UTF-8");
            JSONArray jsonArray=new JSONArray(json);

            for (int i = 0; i<jsonArray.length(); i++ ){
                JSONObject jsonObject=jsonArray.getJSONObject(i);


                    WorkoutDetailModel model=new WorkoutDetailModel();
                    model.setName(jsonObject.getString("name"));
                    model.setUrl(jsonObject.getString("url"));
                    model.setCalories(jsonObject.getString("calories"));
                    model.setCorrectForm(jsonObject.getString("correctForm"));
                    model.setDisplayName(jsonObject.getString("display"));
                    model.setHowtodo(jsonObject.getString("howTo"));
                    model.setId(jsonObject.getInt("id"));
                    model.setInfo(jsonObject.getString("info"));
                    model.setTurns(jsonObject.getString("turns"));
                    WorkoutList.add(model);
                    // groupList.add(jsonObject.getString("name"));
                    //count++;
                    //Log.d("Count", String.valueOf(count));
            }


            //Toast.makeText(GroupList.this,grouplist.toString(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(exercise);
        super.onViewCreated(view, savedInstanceState);
    }
}
