package com.ug.cancerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;

import static com.ug.cancerapp.fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.fragments.FifthFragment.ABORTION;
import static com.ug.cancerapp.fragments.FifthFragment.DATS;
import static com.ug.cancerapp.fragments.FifthFragment.PARITY;
import static com.ug.cancerapp.fragments.FifthFragment.PREGNANT;
import static com.ug.cancerapp.fragments.FirstFragment.AGE;
import static com.ug.cancerapp.fragments.FirstFragment.COUNTY;
import static com.ug.cancerapp.fragments.FirstFragment.DISTRICT;
import static com.ug.cancerapp.fragments.FirstFragment.INITIAL;
import static com.ug.cancerapp.fragments.FirstFragment.STUDY;
import static com.ug.cancerapp.fragments.FirstFragment.ZONE;
import static com.ug.cancerapp.fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.fragments.HaartFragment.CHOICE2;
import static com.ug.cancerapp.fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.fragments.ScreenFragment.SSS;
import static com.ug.cancerapp.fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.fragments.SecondFragment.TEXT;
import static com.ug.cancerapp.fragments.SixtyFragment.S4;
import static com.ug.cancerapp.fragments.ThirdFragment.TEXT2;
import static com.ug.cancerapp.fragments.ViaFragment.LESION;
import static com.ug.cancerapp.fragments.ViaFragment.NOTES;
import static com.ug.cancerapp.fragments.ViaFragment.VIA;
import static com.ug.cancerapp.fragments.YesFragment.SS;
import static com.ug.cancerapp.fragments.YesOrNoFragment.CHOICES;

public class DataActivity extends AppCompatActivity {

    TextView textView;
    String s = "";

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textView = findViewById(R.id.text);

        getData();
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String studyID = sharedPreferences.getString(STUDY, "");
        String initial = sharedPreferences.getString(INITIAL, "");
        String age = sharedPreferences.getString(AGE, "");
        String district = sharedPreferences.getString(DISTRICT, "");
        String county = sharedPreferences.getString(COUNTY, "");
        String zone = sharedPreferences.getString(ZONE, "");
        String text = sharedPreferences.getString(TEXT, "");
        String ss = sharedPreferences.getString(SS, "");
        String symptom = sharedPreferences.getString(OTHER, "");
        String text2 = sharedPreferences.getString(TEXT2, "");
        String past = sharedPreferences.getString(CHOICE, "");
        String sss = sharedPreferences.getString(SSS, "");
        String datey = sharedPreferences.getString(DATEPICKER, "");
        String treat = sharedPreferences.getString(TREATMENT, "");
        String value3 = sharedPreferences.getString(TEXT3, "");
        String valuex = sharedPreferences.getString(CHOICE2, "");
        String year = sharedPreferences.getString(YEARS, "");
        String value = sharedPreferences.getString(PREGNANT, "");
        String time = sharedPreferences.getString(DATS, "");
        String child = sharedPreferences.getString(PARITY, "");
        String abort = sharedPreferences.getString(ABORTION, "");
        String choice = sharedPreferences.getString(CHOICES, "");
        String s4 = sharedPreferences.getString(S4, "");
        String sImage = sharedPreferences.getString(IMAGE, "");
        String sImage2 = sharedPreferences.getString(IMAGE2, "");
        String sImage3 = sharedPreferences.getString(IMAGE3, "");
        String sImage4 = sharedPreferences.getString(IMAGE4, "");
        String via = sharedPreferences.getString(VIA, "");
        String notes = sharedPreferences.getString(NOTES, "");
        String location = sharedPreferences.getString(LESION, "");

        s = studyID + "\n" + initial + "\n" + age + "\n" + district + "\n" + county + "\n" + zone +
                "\n" + text + "\n" + ss + "\n" + symptom + "\n" + text2 + "\n" + past + "\n" + sss +
                "\n" + datey + "\n" + treat + "\n" + value3 + "\n" + valuex + "\n" + year + "\n" +
                value + "\n" + time + "\n" + child + "\n" + abort + "\n" + choice + "\n" + s4 + "\n" +
                sImage + "\n" + sImage2 + "\n" + sImage3 + "\n" + sImage4 + "\n" + via + "\n" + notes +
                "\n" + location + "\n" + "location";

        textView.setText(s);

        
    }
}