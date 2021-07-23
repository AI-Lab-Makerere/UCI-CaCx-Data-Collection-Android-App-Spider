package com.ug.cancerapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class LoopActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView etDate, etMonthYear, etYear;
    LinearLayout linearLayout;
    Spinner spinner;
    String text, time, months;
    Calendar calendar;
    int year, month;
    int year1, month1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

//        etDate = findViewById(R.id.txt_date);
        etMonthYear = findViewById(R.id.txt_month_year);
        etYear = findViewById(R.id.txt_year);
        linearLayout = findViewById(R.id.vnc);
        spinner = findViewById(R.id.day);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.same, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void btnMonthYear(View view) {

         calendar = Calendar.getInstance();
         month = calendar.get(Calendar.MONTH);
         year = calendar.get(Calendar.YEAR);

        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                time = (selectedMonth + 1) + "/" + selectedYear;
                etMonthYear.setText(time);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }, year, month);

        builder.setActivatedMonth(month)
                .setMinYear(2010)
                .setActivatedYear(year)
                .setMaxYear(year)
                .setTitle("Select Month")
                .build().show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        if (position == 1){
            etYear.setText(text);
            months = "01/" + time;
            Toast.makeText(this, months, Toast.LENGTH_SHORT).show();
        }else  if (position == 2){
            etYear.setText(text);
            months = "15/" + time;
            Toast.makeText(this, months, Toast.LENGTH_SHORT).show();
        }else if (position == 3) {
            etYear.setText(text);
            months = "29/" + time;
            Toast.makeText(this, months, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}