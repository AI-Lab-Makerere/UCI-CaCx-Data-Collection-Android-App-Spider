package com.ug.cancerapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ug.cancerapp.Activities.ModelActivity;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Activities.DashBoardActivity;
import com.ug.cancerapp.ml.Cancer;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.ug.cancerapp.Activities.SplashActivity.THRESHOLD;
import static com.ug.cancerapp.Fragments.Camera1Fragment.FLON;
import static com.ug.cancerapp.Fragments.Camera1Fragment.FLOP;
import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera1Fragment.VR;
import static com.ug.cancerapp.Fragments.Camera2Fragment.FLON2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.FLOP2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.VR2;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLON3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLOP3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.VR3;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLON4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLOP4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.VR4;
import static com.ug.cancerapp.Fragments.FifthFragment.ABORTION;
import static com.ug.cancerapp.Fragments.FifthFragment.DATS;
import static com.ug.cancerapp.Fragments.FifthFragment.PARITY;
import static com.ug.cancerapp.Fragments.FifthFragment.PREGNANT;
import static com.ug.cancerapp.Fragments.FirstFragment.AGE;
import static com.ug.cancerapp.Fragments.FirstFragment.COUNTY;
import static com.ug.cancerapp.Fragments.FirstFragment.DISTRICT;
import static com.ug.cancerapp.Fragments.FirstFragment.INITIAL;
import static com.ug.cancerapp.Fragments.FirstFragment.STUDY;
import static com.ug.cancerapp.Fragments.FirstFragment.ZONE;
import static com.ug.cancerapp.Fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.Fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.Fragments.HaartFragment.CHOICE2;
import static com.ug.cancerapp.Fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.Fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.Fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.Fragments.ScreenFragment.METHOD;
import static com.ug.cancerapp.Fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.Fragments.SecondFragment.TEXT;
import static com.ug.cancerapp.Fragments.SixtyFragment.S4;
import static com.ug.cancerapp.Fragments.ThirdFragment.TEXT2;
import static com.ug.cancerapp.Fragments.ViaFragment.LESION;
import static com.ug.cancerapp.Fragments.ViaFragment.NOTES;
import static com.ug.cancerapp.Fragments.ViaFragment.VIA;
import static com.ug.cancerapp.Fragments.YesFragment.SS;
import static com.ug.cancerapp.Fragments.YesOrNoFragment.CHOICES;


public class Other2Fragment extends Fragment {

    View view;
    Button back, send, model;
    String s = "";
    int num2 = 0;

    private FormViewModel formViewModel;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_API = "sharedApi";
    public static String uniqueID;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor;

    String via;
    float pos, pos2, pos4, pos3, neg, neg2, neg3, neg4;
    String viar, viar2, viar4, viar3;

    String mmv;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    ProgressDialog progressDialog;

    String studyID, initial, age, district, county, zone, text, ss, symptom,
            text2, past, method, datey, treat, value3, valuex, year, value, time, child, abort,
            choice, s4, notes, location, diagnosis, format;

    Boolean consult;

    int abortion, children;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other2, container, false);

        back = view.findViewById(R.id.back);
        send = view.findViewById(R.id.save);
        model = view.findViewById(R.id.model);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences2 = getActivity().getSharedPreferences(SHARED_API, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        via = sharedPreferences.getString(VIA, "");

        String positive3 = sharedPreferences.getString(FLOP3, "");
        pos3 = Float.parseFloat(positive3);
        viar3 = sharedPreferences.getString(VR3, "");

        String positive4 = sharedPreferences.getString(FLOP4, "");
        pos4 = Float.parseFloat(positive4);
        viar4 = sharedPreferences.getString(VR4, "");

        getModelResults();

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        progressDialog = new ProgressDialog(getActivity());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
//                editor.clear();
//                editor.apply();
                Toast.makeText(getActivity(), "Task Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), DashBoardActivity.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ViaFragment());
                fr.commit();
            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(getActivity(), ModelActivity.class);
                intent.putExtra("model", mmv);
                intent.putExtra("nurse", via);
                startActivity(intent);
            }
        });

        return view;
    }

    private void saveData() {

        String studyID = sharedPreferences.getString(STUDY, "");
        String initial = sharedPreferences.getString(INITIAL, "");
        String age = sharedPreferences.getString(AGE, "");
        int number = Integer.parseInt(age);
        String district = sharedPreferences.getString(DISTRICT, "");
        String county = sharedPreferences.getString(COUNTY, "");
        String zone = sharedPreferences.getString(ZONE, "");
        String text = sharedPreferences.getString(TEXT, "");
        String ss = sharedPreferences.getString(SS, "");
        if (ss.equals("")) {
            ss = "None";
        }
        String symptom = sharedPreferences.getString(OTHER, "");
        if (symptom.equals("")) {
            symptom = "None";
        }
        String text2 = sharedPreferences.getString(TEXT2, "");
        String past = sharedPreferences.getString(CHOICE, "");
        String method = sharedPreferences.getString(METHOD, "");
        String datey = sharedPreferences.getString(DATEPICKER, "");
        String treat = sharedPreferences.getString(TREATMENT, "");
        String value3 = sharedPreferences.getString(TEXT3, "");
        String valuex = sharedPreferences.getString(CHOICE2, "");
        if (valuex.equals("")) {
            valuex = "No";
        }
        String year = sharedPreferences.getString(YEARS, "");
        if (year.isEmpty()) {
            num2 = 0;
        } else {
            num2 = Integer.parseInt(year);
        }
        String value = sharedPreferences.getString(PREGNANT, "");
        String time = sharedPreferences.getString(DATS, "");
        String child = sharedPreferences.getString(PARITY, "");
        children = Integer.parseInt(child);
        String abort = sharedPreferences.getString(ABORTION, "");
        abortion = Integer.parseInt(abort);
        choice = sharedPreferences.getString(CHOICES, "");
        s4 = sharedPreferences.getString(S4, "");
        if (s4.equals("")) {
            s4 = "None";
        }

        notes = sharedPreferences.getString(NOTES, "");
        location = sharedPreferences.getString(LESION, "");

        String negative = sharedPreferences.getString(FLON, "");
        float neg = Float.parseFloat(negative);
        String positive = sharedPreferences.getString(FLOP, "");
        float pos = Float.parseFloat(positive);
        String viar = sharedPreferences.getString(VR, "");

        String negative2 = sharedPreferences.getString(FLON2, "");
        float neg2 = Float.parseFloat(negative2);
        String positive2 = sharedPreferences.getString(FLOP2, "");
        float pos2 = Float.parseFloat(positive2);
        String viar2 = sharedPreferences.getString(VR2, "");

        String negative3 = sharedPreferences.getString(FLON3, "");
        float neg3 = Float.parseFloat(negative3);


        String negative4 = sharedPreferences.getString(FLON4, "");
        float neg4 = Float.parseFloat(negative4);


        diagnosis = mmv;
        consult = false;
        uniqueID = UUID.randomUUID().toString();

        String sImage = sharedPreferences.getString(IMAGE, "");
        String sImage2 = sharedPreferences.getString(IMAGE2, "");
        String sImage3 = sharedPreferences.getString(IMAGE3, "");
        String sImage4 = sharedPreferences.getString(IMAGE4, "");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        format = simpleDateFormat.format(new Date());


        Form form = new Form(format, studyID, initial, district, county, zone, number, text, ss, symptom,
                text2, datey, method, treat, past, value3, valuex, num2, value, time, children, abortion, choice,
                s4, sImage, sImage2, sImage3, sImage4, via, location, notes, diagnosis, consult, false,
                neg, pos, viar, neg2, pos2, viar2, neg3, pos3, viar3, neg4, pos4, viar4, uniqueID);

        formViewModel.insert(form);

    }

    private void getModelResults() {

        String thres = sharedPreferences2.getString(THRESHOLD, "");
        float threshold = Float.parseFloat(thres);

        if (viar3.equals(viar4)) {
            mmv = viar3;
        } else {
            if (viar3.equals("Positive")) {
                if (pos3 >= threshold) {
                    mmv = viar3;
                } else {
                    mmv = "Negative";
                }
            } else {
                if (pos4 >= threshold) {
                    mmv = viar4;
                } else {
                    mmv = "Negative";
                }
            }
        }
    }
}