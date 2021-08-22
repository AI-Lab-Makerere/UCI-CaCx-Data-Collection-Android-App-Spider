/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import static com.ug.cancerapp.Activities.LoginActivity.EMAIL;
import static com.ug.cancerapp.Activities.LoginActivity.FACID;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;
import static com.ug.cancerapp.Worker.LocationUploadWorker.KEY_X_ARG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.Capture2;
import com.ug.cancerapp.Models.Picture;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Worker.LocationUploadWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendingActivity extends AppCompatActivity {

    ArrayList<Form> formList, formList_search, selected;
    Long index;
    FormAdapter formAdapter;
    RecyclerView recyclerView;
    Dialog dialog;
    Button submit, btnSelected, btnDeselect;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri uri;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    EditText etSearch;
    Date parsedDate3;
    RelativeLayout root_layout;
    boolean InternetCheck = true;

    FormRepository formRepository;
    Form form;
    FormDAO formDAO;
    private FormViewModel formViewModel;

    public static final String SHARED_API = "sharedApi";
    String username, token, facility, text2;

    JsonPlaceHolder jsonPlaceHolder;
    ProgressDialog progressDialog;

    Date date1, date2, date3;
    long i = 0;
    Call<String> call;
    Long key;
    ArrayList<Long> arrayForm;
    int fac_id;

    String others = "mchodrine@gmail.com";
    private boolean autoSendOngoing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Records");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        submit = findViewById(R.id.submit);
        btnSelected = findViewById(R.id.btn1);
        btnDeselect = findViewById(R.id.btn2);
        root_layout = findViewById(R.id.root_layout);


        progressDialog = new ProgressDialog(this);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");
        facility = sharedPreferences.getString(FACID, "");
        fac_id = Integer.parseInt(facility);

        formAdapter = new FormAdapter();
        recyclerView.setAdapter(formAdapter);

        formDAO = FormDatabase.getInstance(SendingActivity.this).formDAO();
        formRepository = new FormRepository((Application) getApplicationContext());

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);
        formViewModel.getAllData().observe(this, new Observer<List<Form>>() {
            @Override
            public void onChanged(List<Form> formList) {
                formAdapter.setForms(formList);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                WorkManager.getInstance(SendingActivity.this).cancelUniqueWork("UCI_WorkManager");
                int checkedCount = formAdapter.getItemCount();
                if (checkedCount > 0){
                    setAllToCheckedState();
                    ArrayList<Long> count = new ArrayList<>();
                    ArrayList<String> counting = new ArrayList<>();
                    for (int i = 0; i < formAdapter.getSelected().size(); i++){
                        index = formAdapter.getSelected().get(i).getKey();
                        count.add(index);
                        Log.i("TAG", "uploadSelectedFiles: " + count);
                        if(i == (formAdapter.getSelected().size()-1)){
                            Log.i("TAG2", "this is final" + count);
                        }
                    }

                    for (Long i: count) {
                        counting.add(String.valueOf(i));
                    }

                    StringBuilder str = new StringBuilder("");
                    for (String eachString : counting) {
                        str.append(eachString).append(",");
                    }
                    String commaseparatedlist = str.toString();

                    if (commaseparatedlist.length() > 0)
                        commaseparatedlist = commaseparatedlist.substring(0, commaseparatedlist.length() - 1);

                    Toast.makeText(SendingActivity.this, "All the collected information will be sent to the server", Toast.LENGTH_SHORT).show();
                    
                    Data myData = new Data.Builder()
                            .putString(KEY_X_ARG, commaseparatedlist)
                            .build();

                    Constraints constraints = new Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .setRequiresBatteryNotLow(false)
                            .build();

                    WorkRequest uploadWorkRequest = new OneTimeWorkRequest
                            .Builder(LocationUploadWorker.class)
                            .setConstraints(constraints)
                            .setInputData(myData)
                            .build();

                    WorkManager.getInstance(SendingActivity.this).enqueue(uploadWorkRequest);
                }
            }
        });

    }

    private void setAllToCheckedState() {
        formAdapter.selectAll();
    }

    private void uploadSelectedFiles() {
        ArrayList<Long> count = new ArrayList<>();
        for (int i = 0; i < formAdapter.getSelected().size(); i++){
            index = formAdapter.getSelected().get(i).getKey();
            count.add(index);
            Log.i("TAG", "uploadSelectedFiles: " + count);
            if(i == (formAdapter.getSelected().size()-1)){
                Log.i("TAG2", "this is final" + count);
            }
        }

        for( Long value : count ){
//            sendData(value);
            formDAO.DeleteForm(value);

        }


    }

//    private void sendData(Long value) {
//        progressDialog.setMessage("Sending Data, Please wait...");
//        progressDialog.show();
//        form = formRepository.getOnlyOne(value);
//        String studyID = form.getStudyID();
//        String initial = form.getInitials();
//        int age = form.getAge();
//        String district = form.getDistrict();
//        String county = form.getCounty();
//        String zone = form.getVillage();
//        String text = form.getHave_symptoms();
//        String ss = form.getSymptoms();
//        String symptom = form.getOther_symptoms();
//        text2 = form.getScreened_for_cancer();
//        String past = form.getScreening_results();
//        String sss = form.getScreening_process();
//        String datey = form.getLast_screened();
//        String treat = form.getTreatment();
//        String value3 = form.getHiv_status();
//        String valuex = form.getOn_haart();
//        int year = form.getYears_on_haart();
//        String valuev = form.getPregnant();
//        String time = form.getLast_menstrual();
//        int child = form.getParity();
//        int abort = form.getAbortion();
//        String choice = form.getOn_contraceptives();
//        String s4 = form.getContraceptives();
//        String sImage = form.getImage1();
//        String sImage2 = form.getImage2();
//        String sImage3 = form.getImage3();
//        String sImage4 = form.getImage4();
//        String via = form.getVia();
//        String notes = form.getNotes();
//        String location = form.getLocation();
//        String date = form.getDate();
//
//        String instanceID = form.getInstanceID();
//        float neg = form.getPicture1_nc();
//        float pos = form.getPicture1_pc();
//        String var = form.getPicture1_via();
//        float neg2 = form.getPicture2_nc();
//        float pos2 = form.getPicture2_pc();
//        String var2 = form.getPicture2_via();
//        float neg3 = form.getPicture3_nc();
//        float pos3 = form.getPicture3_pc();
//        String var3 = form.getPicture3_via();
//        float neg4 = form.getPicture4_nc();
//        float pos4 = form.getPicture4_pc();
//        String var4 = form.getPicture4_via();
//        String ml_result = form.getDiagnosis();
//        Boolean consult = form.getConsult();
//
//
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
//        token = sharedPreferences.getString(TOKEN, "");
//        username = sharedPreferences.getString(EMAIL, "");
//        facility = sharedPreferences.getString(FACID, "");
//
//        try {
//            if (!datey.isEmpty()){
//                if (!datey.equals("Not Sure")){
//                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(datey);
//                }
//            }
//            if (!time.equals("Not Sure")){
//                date3 = new SimpleDateFormat("dd/MM/yyyy").parse(time);
//            }
//            date1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(date);
//            Log.v("TAG1", ""+date1);
//            Log.v("TAG2", ""+date2);
//            Log.v("TAG3", ""+date3);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Picture picture1 = new Picture(neg, pos, var, sImage, "");
//        Picture picture2 = new Picture(neg2, pos2, var2, sImage2, "");
//        Picture picture3 = new Picture(neg3, pos3, var3, sImage3, "");
//        Picture picture4 = new Picture(neg4, pos4, var4, sImage4, "");
//
//        String other = form.getNurses();
//        String version = "2.0.0";
//        float threshold = 5.0F;
//
//
//        Capture capture = new Capture(instanceID, date1, username, fac_id, studyID, initial,
//                district, county, zone, age, text, ss, symptom, text2, sss, past, treat, date2,
//                value3, valuex, year, valuev, date3, child, abort, s4, choice, location, via, notes,
//                ml_result, consult, other, threshold, version, picture1, picture2, picture3, picture4);
//
//        Capture2 capture2 = new Capture2(instanceID, date1, username, fac_id, studyID, initial,
//                district, county, zone, age, text, ss, symptom, text2,
//                value3, valuex, year, valuev, date3, child, abort, s4, choice, location, via, notes,
//                ml_result, consult, other, threshold, version, picture1, picture2, picture3, picture4);
//
//        Log.v("TAG", "data3");
//
//        sendOverNetwork(token, capture, capture2, value);
//    }
//
//    private void sendOverNetwork(String token, Capture capture, Capture2 capture2, Long value) {
//
//        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);
//
//        if (text2.equals("No")){
//            call = jsonPlaceHolder.capture2("Bearer " + token, capture2);
//        }else {
//            call = jsonPlaceHolder.capture("Bearer " + token, capture);
//        }
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(SendingActivity.this, "Connection Issue: " + response.message(), Toast.LENGTH_LONG).show();
//                    progressDialog.dismiss();
//                    Log.v("TAG", ""+response.code());
//                    return;
//                }
//
//                String message = response.body();
//                Toast.makeText(SendingActivity.this, "Form Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                Log.v("TAG", message);
//                formDAO.DeleteForm(value);
//                progressDialog.dismiss();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(SendingActivity.this, "Something went wrong: " + t.getMessage() , Toast.LENGTH_LONG).show();
//                Log.v("TAG", "Something went wrong: " + t.getMessage());
//                progressDialog.dismiss();
//
//            }
//        });
//    }
}