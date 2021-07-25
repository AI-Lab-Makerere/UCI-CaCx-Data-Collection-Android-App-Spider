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
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.Activities.ModelActivity;
import com.ug.cancerapp.Activities.SavingActivity;
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
    EditText et1, et2, et3;
    Button next, back;
    String nurse1, nurse2, nurse3;

    public static final String SHARED_PREFS = "sharedPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String NURSE1 = "nurse1";
    public static final String NURSE2 = "nurse2";
    public static final String NURSE3 = "nurse3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other2, container, false);
        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        et1 = view.findViewById(R.id.nurse1);
        et2 = view.findViewById(R.id.nurse2);
        et3 = view.findViewById(R.id.nurse3);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadData();
        updateViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nurse1 = et1.getText().toString();
                nurse2 = et2.getText().toString();
                nurse3 = et3.getText().toString();

                if (nurse1.isEmpty()){
                    Toast.makeText(getActivity(), "Please provide at least on other Nurse", Toast.LENGTH_SHORT).show();
                    et1.setCursorVisible(true);
                }else {
                    saveData();
                    startActivity(new Intent(getActivity(), SavingActivity.class));

                }


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

        return view;
    }

    private void saveData() {

        nurse1 = et1.getText().toString();
        nurse2 = et2.getText().toString();
        nurse3 = et3.getText().toString();

        editor.putString(NURSE1, nurse1);
        editor.putString(NURSE2, nurse2);
        editor.putString(NURSE3, nurse3);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        nurse1 = sharedPreferences.getString(NURSE1, "");
        nurse2 = sharedPreferences.getString(NURSE2, "");
        nurse3 = sharedPreferences.getString(NURSE3, "");

    }

    public void updateViews(){

        et2.setText(nurse2);
        et3.setText(nurse3);
        et1.setText(nurse1);

    }
}