package com.ug.cancerapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import static com.ug.cancerapp.fragments.SixtyFragment.CHECK1;
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
    Button save;
    int num2 = 0;

    private FormViewModel formViewModel;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textView = findViewById(R.id.text);
        save = findViewById(R.id.save);

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        getData();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

//                Form form = new Form();

//                form.setDate(format);
//                form.setStudyID(studyID);
//                form.setInitials(initial);
//                form.setAge(number);
//                form.setDistrict(district);
//                form.setCounty(county);
//                form.setVillage(zone);
//                form.setHave_symptoms(text);
//                form.setSymptoms(ss);
//                form.setOther_symptoms(symptom);
//                form.setScreened_for_cancer(text2);
//                form.setLast_screened(datey);
//                form.setTreatment(treat);
//                form.setScreening_process(sss);
//                form.setScreening_results(past);
//                form.setHiv_status(value3);
//                form.setOn_haart(valuex);
//                form.setYears_on_haart(num2);
//                form.setPregnant(value);
//                form.setLast_menstrual(time);
//                form.setParity(children);
//                form.setAbortion(abortion);
//                form.setOn_contraceptives(choice);
//                form.setContraceptives(s4);
//                form.setImage1(sImage);
//                form.setImage2(sImage2);
//                form.setImage3(sImage3);
//                form.setImage4(sImage4);
//                form.setVia(via);
//                form.setLocation(location);
//                form.setNotes(notes);

//                formViewModel.insert(form);

                Toast.makeText(DataActivity.this, "successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DataActivity.this, DashBoardActivity.class));
                finish();
            }
        });
    }

    private void getData() {


//        s = studyID + "\n" + initial + "\n" + age + "\n" + district + "\n" + county + "\n" + zone +
//                "\n" + text + "\n" + ss + "\n" + symptom + "\n" + text2 + "\n" + past + "\n" + sss +
//                "\n" + datey + "\n" + treat + "\n" + value3 + "\n" + valuex + "\n" + year + "\n" +
//                value + "\n" + time + "\n" + child + "\n" + abort + "\n" + choice + "\n" + s4 + "\n" +
//                sImage + "\n" + sImage2 + "\n" + sImage3 + "\n" + sImage4 + "\n" + via + "\n" + notes +
//                "\n" + location + "\n" + "location";
//
//        textView.setText(s);


//        form.setDate(format);


        
    }
}