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
    int number = 0;
    int act = 0;
    int ident = 0;

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

    String mmv, sImage, sImage2, sImage3, sImage4;
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        format = simpleDateFormat.format(new Date());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        via = sharedPreferences.getString(VIA, "");

//        getModelResults();

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        progressDialog = new ProgressDialog(getActivity());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                act = 2;
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Saving Data...");
                progressDialog.show();
                makePredictions();
//                editor.clear();
//                editor.apply();

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
//                saveData();
                act = 1;
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Saving Data...");
                progressDialog.show();
                makePredictions();
            }
        });

        return view;
    }

//    private void saveData() {
//
////        studyID = sharedPreferences.getString(STUDY, "");
////        initial = sharedPreferences.getString(INITIAL, "");
////        age = sharedPreferences.getString(AGE, "");
////        number = Integer.parseInt(age);
////        district = sharedPreferences.getString(DISTRICT, "");
////        county = sharedPreferences.getString(COUNTY, "");
////        zone = sharedPreferences.getString(ZONE, "");
////        text = sharedPreferences.getString(TEXT, "");
////        ss = sharedPreferences.getString(SS, "");
////        if (ss.equals("")) {
////            ss = "None";
////        }
////        symptom = sharedPreferences.getString(OTHER, "");
////        if (symptom.equals("")) {
////            symptom = "None";
////        }
////        text2 = sharedPreferences.getString(TEXT2, "");
////        past = sharedPreferences.getString(CHOICE, "");
////        method = sharedPreferences.getString(METHOD, "");
////        datey = sharedPreferences.getString(DATEPICKER, "");
////        treat = sharedPreferences.getString(TREATMENT, "");
////        value3 = sharedPreferences.getString(TEXT3, "");
////        valuex = sharedPreferences.getString(CHOICE2, "");
////        if (valuex.equals("")) {
////            valuex = "No";
////        }
////        year = sharedPreferences.getString(YEARS, "");
////        if (year.isEmpty()) {
////            num2 = 0;
////        } else {
////            num2 = Integer.parseInt(year);
////        }
////        value = sharedPreferences.getString(PREGNANT, "");
////        time = sharedPreferences.getString(DATS, "");
////        child = sharedPreferences.getString(PARITY, "");
////        children = Integer.parseInt(child);
////        abort = sharedPreferences.getString(ABORTION, "");
////        abortion = Integer.parseInt(abort);
////        choice = sharedPreferences.getString(CHOICES, "");
////        s4 = sharedPreferences.getString(S4, "");
////        if (s4.equals("")) {
////            s4 = "None";
////        }
////
////        notes = sharedPreferences.getString(NOTES, "");
////        location = sharedPreferences.getString(LESION, "");
////
//////        String negative = sharedPreferences.getString(FLON, "");
//////        float neg = Float.parseFloat(negative);
//////        String positive = sharedPreferences.getString(FLOP, "");
//////        float pos = Float.parseFloat(positive);
//////        String viar = sharedPreferences.getString(VR, "");
//////
//////        String negative2 = sharedPreferences.getString(FLON2, "");
//////        float neg2 = Float.parseFloat(negative2);
//////        String positive2 = sharedPreferences.getString(FLOP2, "");
//////        float pos2 = Float.parseFloat(positive2);
//////        String viar2 = sharedPreferences.getString(VR2, "");
//////
//////        String negative3 = sharedPreferences.getString(FLON3, "");
//////        float neg3 = Float.parseFloat(negative3);
//////
//////
//////        String negative4 = sharedPreferences.getString(FLON4, "");
//////        float neg4 = Float.parseFloat(negative4);
////
////
////        diagnosis = mmv;
////
////        if (mmv.equals(via)){
////            consult = false;
////        }else {
////            consult = true;
////        }
////
////        consult = false;
////        uniqueID = UUID.randomUUID().toString();
////
////        sImage = sharedPreferences.getString(IMAGE, "");
////        sImage2 = sharedPreferences.getString(IMAGE2, "");
////        sImage3 = sharedPreferences.getString(IMAGE3, "");
////        sImage4 = sharedPreferences.getString(IMAGE4, "");
////
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
////        format = simpleDateFormat.format(new Date());
////
////
////        Form form = new Form(format, studyID, initial, district, county, zone, number, text, ss, symptom,
////                text2, datey, method, treat, past, value3, valuex, num2, value, time, children, abortion, choice,
////                s4, sImage, sImage2, sImage3, sImage4, via, location, notes, diagnosis, consult, false,
////                neg, pos, viar, neg2, pos2, viar2, neg3, pos3, viar3, neg4, pos4, viar4, uniqueID);
////
////        formViewModel.insert(form);
//
//    }


    private void makePredictions(){

        sImage = sharedPreferences.getString(IMAGE, "");
        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
        bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ident = 1;
        boolean sone = runTensorflowModel(bitmap1, ident);
        if (sone){
            sImage2 = sharedPreferences.getString(IMAGE2, "");
            byte[] bytes2 = Base64.decode(sImage2, Base64.DEFAULT);
            bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            ident = 2;
            boolean sone2 = runTensorflowModel(bitmap2, ident);
            if (sone2){
                sImage3 = sharedPreferences.getString(IMAGE3, "");
                byte[] bytes3 = Base64.decode(sImage3, Base64.DEFAULT);
                bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                ident = 3;
                boolean sone3 = runTensorflowModel(bitmap3, ident);
                if (sone3){
                    sImage4 = sharedPreferences.getString(IMAGE4, "");
                    byte[] bytes4 = Base64.decode(sImage4, Base64.DEFAULT);
                    bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);
                    ident = 4;
                    boolean sone4 = runTensorflowModel(bitmap4, ident);
                    if (sone4) {
                        boolean diagno = getModelResults();
                        if (diagno){
                            savingData();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed to obtain general diagnosis", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to obtain predictions of the fourth image", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to obtain predictions of the third image", Toast.LENGTH_SHORT).show();
                }
            }else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Failed to obtain predictions of the second image", Toast.LENGTH_SHORT).show();
            }
        }else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Failed to obtain predictions of the first image", Toast.LENGTH_SHORT).show();
        }

    }

    private void savingData() {

        studyID = sharedPreferences.getString(STUDY, "");
        initial = sharedPreferences.getString(INITIAL, "");
        age = sharedPreferences.getString(AGE, "");
        number = Integer.parseInt(age);
        district = sharedPreferences.getString(DISTRICT, "");
        county = sharedPreferences.getString(COUNTY, "");
        zone = sharedPreferences.getString(ZONE, "");
        text = sharedPreferences.getString(TEXT, "");
        ss = sharedPreferences.getString(SS, "");
        if (ss.equals("")) {
            ss = "None";
        }
        symptom = sharedPreferences.getString(OTHER, "");
        if (symptom.equals("")) {
            symptom = "None";
        }
        text2 = sharedPreferences.getString(TEXT2, "");
        past = sharedPreferences.getString(CHOICE, "");
        method = sharedPreferences.getString(METHOD, "");
        datey = sharedPreferences.getString(DATEPICKER, "");
        treat = sharedPreferences.getString(TREATMENT, "");
        value3 = sharedPreferences.getString(TEXT3, "");
        valuex = sharedPreferences.getString(CHOICE2, "");
        if (valuex.equals("")) {
            valuex = "No";
        }
        year = sharedPreferences.getString(YEARS, "");
        if (year.isEmpty()) {
            num2 = 0;
        } else {
            num2 = Integer.parseInt(year);
        }
        value = sharedPreferences.getString(PREGNANT, "");
        time = sharedPreferences.getString(DATS, "");
        child = sharedPreferences.getString(PARITY, "");
        children = Integer.parseInt(child);
        abort = sharedPreferences.getString(ABORTION, "");
        abortion = Integer.parseInt(abort);
        choice = sharedPreferences.getString(CHOICES, "");
        s4 = sharedPreferences.getString(S4, "");
        if (s4.equals("")) {
            s4 = "None";
        }

        notes = sharedPreferences.getString(NOTES, "");
        location = sharedPreferences.getString(LESION, "");

        diagnosis = mmv;

        if (mmv.equals(via)){
            consult = false;
        }else {
            consult = true;
        }

        consult = false;
        uniqueID = UUID.randomUUID().toString();

        Form form = new Form(format, studyID, initial, district, county, zone, number, text, ss, symptom,
                text2, datey, method, treat, past, value3, valuex, num2, value, time, children, abortion, choice,
                s4, sImage, sImage2, sImage3, sImage4, via, location, notes, diagnosis, consult, false,
                neg, pos, viar, neg2, pos2, viar2, neg3, pos3, viar3, neg4, pos4, viar4, uniqueID);

        formViewModel.insert(form);
        progressDialog.dismiss();

        if (act == 1){
            Intent intent = new Intent(getActivity(), ModelActivity.class);
            intent.putExtra("model", mmv);
            intent.putExtra("nurse", via);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), "Data saved Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), DashBoardActivity.class));
        }
    }

    private boolean runTensorflowModel(Bitmap bitmap, int ident) {

        try {
            Cancer model = Cancer.newInstance(getActivity());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 300, 300, 3}, DataType.FLOAT32);

            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
            tensorImage.load(bitmap);
            ByteBuffer byteBuffer = tensorImage.getBuffer();
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Cancer.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Releases model resources if no longer used.
            model.close();

            if (ident == 1){
                neg = outputFeature0.getFloatArray()[0];
                pos = outputFeature0.getFloatArray()[1];

                if (neg > pos){
                    viar = "Negative";
                }else {
                    viar = "Positive";
                }

            }else if (ident == 2){
                neg2 = outputFeature0.getFloatArray()[0];
                pos2 = outputFeature0.getFloatArray()[1];

                if (neg2 > pos2){
                    viar2 = "Negative";
                }else {
                    viar2 = "Positive";
                }

            }else if (ident == 3){
                neg3 = outputFeature0.getFloatArray()[0];
                pos3 = outputFeature0.getFloatArray()[1];

                if (neg3 > pos3){
                    viar3 = "Negative";
                }else {
                    viar3 = "Positive";
                }

            }else {
                neg4 = outputFeature0.getFloatArray()[0];
                pos4 = outputFeature0.getFloatArray()[1];

                if (neg4 > pos4){
                    viar4 = "Negative";
                }else {
                    viar4 = "Positive";
                }
            }


        } catch (IOException e) {
            // TODO Handle the exception
        }

        return true;
    }

    private boolean getModelResults() {

        float threshold = (float) 0.5;

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

        return true;
    }
}