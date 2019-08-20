package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.models.WeightHeightModel;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ReportsBMIactivity extends AppCompatActivity implements View.OnClickListener {
    LineChart mchart;
    Toolbar toolbar;
    TextView weight, height, editbtn, bmi_result, weight_result;
    String bodyweight = "65", bodyheight = "5.9";
    ArrayList<WeightHeightModel> WEHEList;
    WeightHeightModel daysModel=new WeightHeightModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activty);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Reports");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        BindData();

        mchart = findViewById(R.id.linechart);
        weight = findViewById(R.id.tvweight);
        height = findViewById(R.id.tvheight);
        editbtn = findViewById(R.id.edit_bmi);
        bmi_result = findViewById(R.id.bmi_value);
        weight_result = findViewById(R.id.tv_weight_result);
        editbtn.setOnClickListener(this);
        daysModel = SharedPrefManager.getInstance(this).getWEHE();
        if (daysModel!=null) {
            BindData();
            calcBMI();
            Log.d("MealPlane", "sharedpref not null");
        } else {

            WeightHeightModel WEHE = new WeightHeightModel();
            WEHE.setWeight("65");
            WEHE.setHeight("5.8");
            if (SharedPrefManager.getInstance(ReportsBMIactivity.this).addWHToPref(WEHE)) {
                Log.d("MealPlan", "Add to Pref");
            }
        }
        BindData();
        calcBMI();
    }

    private void BindData() {
        daysModel = SharedPrefManager.getInstance(this).getWEHE();
        Log.d("HEIGHT","WEIGHT"+daysModel.getHeight()+" "+daysModel.getWeight());
        weight.setText(daysModel.getWeight());
        height.setText(daysModel.getHeight());
        ArrayList<Entry> list = new ArrayList<>();
        list.add(new Entry(0, Float.parseFloat(weight.getText().toString())));
        list.add(new Entry(2, 30f));
        list.add(new Entry(3, Float.parseFloat(height.getText().toString())));
        LineDataSet set1 = new LineDataSet(list, "health");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(set1);
        LineData lineData = new LineData(dataset);
        mchart.setData(lineData);
    }


    public void showPopup() {
        final View popupView = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, 500);
        Button btn = (Button) popupView.findViewById(R.id.buttoncancel);
        Button btn1 = (Button) popupView.findViewById(R.id.buttondial);
        final EditText editweight = (EditText) popupView.findViewById(R.id.edit_weight);
        final EditText editheight = (EditText) popupView.findViewById(R.id.edit_height);
        popupWindow.setFocusable(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onDial(editText.getText().toString());
                if (!editweight.getText().toString().isEmpty() && !editheight.getText().toString().isEmpty()) {
                    SetWHToPref(editweight.getText().toString(),editheight.getText().toString());
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(ReportsBMIactivity.this, "Please fill detail", Toast.LENGTH_SHORT).show();
                }

            }
        });
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }

    private void SetWHToPref(String toString, String toString1) {
        SharedPrefManager.getInstance(ReportsBMIactivity.this).RemoveDays();
        WeightHeightModel WEHE = new WeightHeightModel();
        WEHE.setWeight(toString);
        WEHE.setHeight(toString1);

        if (SharedPrefManager.getInstance(ReportsBMIactivity.this).addWHToPref(WEHE)) {
            Log.d("MealPlan", "Add to Pref");
            BindData();
            calcBMI();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_bmi:
                showPopup();
                break;

        }
    }

    public void calcBMI() {
        try {

            double weightvalue = Double.parseDouble(weight.getText().toString());
            double heightvalue = Double.parseDouble(height.getText().toString()) / 3.28;
            double result = weightvalue / Math.pow(heightvalue, 2);
            bmi_result.setText(String.format("%.1f", result) + " KG/m2");
            float roundvalue= Float.parseFloat(String.format("%.1f",result));
            Log.d("Fail", "Error: " + roundvalue);
            if (roundvalue<18.5){
                weight_result.setText("Underweight");
                weight_result.setTextColor(getResources().getColor(R.color.underweight));
            }else if (roundvalue>18.5 && roundvalue<24.9){
                weight_result.setText("Healthy weight");
                weight_result.setTextColor(getResources().getColor(R.color.healthyweight));
            }else if (roundvalue>24.9 && roundvalue<29.9){
                weight_result.setText("Over weight");
                weight_result.setTextColor(getResources().getColor(R.color.overweight));
            }else if (roundvalue>30 && roundvalue<34.5){
                weight_result.setText("Obese");
                weight_result.setTextColor(getResources().getColor(R.color.obese));
            }else if (roundvalue>35){
                weight_result.setText("Extreme obese");
                weight_result.setTextColor(getResources().getColor(R.color.extremeobese));
            }
        } catch (NumberFormatException e) {
            Log.d("Fail", "Error: " + e);
        }

    }
}
