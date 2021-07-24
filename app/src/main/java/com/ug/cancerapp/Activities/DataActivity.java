package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.Information;
import com.ug.cancerapp.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class DataActivity extends AppCompatActivity {

    String instanceId, act;
    ProgressBar progressBar;
    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    String token;

    LinearLayout ns, models;
    TextView error, message, swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient's Data");

        instanceId = getIntent().getStringExtra("uuid");
        act = getIntent().getStringExtra("extra");

//        models = findViewById(R.id.models);

        progressBar = findViewById(R.id.spin_kit);
        ns = findViewById(R.id.empty);
        error = findViewById(R.id.error);
        message = findViewById(R.id.message);
        swipe = findViewById(R.id.swip);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Sprite chasingDots = new ThreeBounce();
        progressBar.setIndeterminateDrawable(chasingDots);

        loadData();

    }

    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");

        progressBar.setVisibility(View.VISIBLE);

        Call<Information> call = jsonPlaceHolder.patient("Bearer " + token, instanceId);
        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (!response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(DataActivity.this, "Connection issue: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    loadmoreData(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(DataActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadmoreData(Response<Information> response) throws ParseException {

        TextView study = findViewById(R.id.studyId);
        study.setText("StudyID: " + response.body().getStudyID());
        TextView initial = findViewById(R.id.initials);
        initial.setText("Age: " + response.body().getAge());
        TextView hav_symptoms = findViewById(R.id.signs);
        hav_symptoms.setText("Presence of symptoms: " + response.body().getSymptoms());
        TextView symptoms = findViewById(R.id.symptoms);
        String hg = response.body().getSelectSymptoms();
        if (hg == null){
            symptoms.setText("Symptoms: " );
        }else {
            symptoms.setText("Symptoms: " + hg);
        }

        TextView other_symptoms = findViewById(R.id.other_symptoms);
        String ws = response.body().getOtherSymptoms();
        if (ws == null){
            other_symptoms.setText("Other Symptoms: ");
        }else {
            other_symptoms.setText("Other Symptoms: " + ws);
        }

        TextView screened = findViewById(R.id.screened);
        screened.setText("Ever Screened for Cancer: " + response.body().getPriorCaCxScreening());
        TextView screened_date = findViewById(R.id.date);
        String date = response.body().getWhenLastScreening();
        if (date == null){
            screened_date.setText("Screening Date: ");
        }else {
            screened_date.setText("Screening Date: " + formatDate(date));
        }

        TextView vr = findViewById(R.id.results);
        String ty = response.body().getPastCaCxScreeningResults();
        if (ty == null){
            vr.setText("VIA Results: ");
        }else {
            vr.setText("VIA Results: " + ty);
        }

        TextView screened_pr = findViewById(R.id.process);
        String re = response.body().getPastScreeningMethod();
        if (re == null){
            screened_pr.setText("Screening Process: ");
        }else {
            screened_pr.setText("Screening Process: " + re);
        }

        TextView treatement = findViewById(R.id.treatment);
        String rn = response.body().getPriorTreatment();
        if (rn == null){
            treatement.setText("Treatment Provided: ");
        }else {
            treatement.setText("Treatment Provided: " + rn);
        }

        TextView hiv = findViewById(R.id.hiv);
        hiv.setText("HIV Status: " + response.body().getHivStatus());
        TextView on_haart = findViewById(R.id.onHaart);
        on_haart.setText("On Haart: " + response.body().getOnHAART());
        TextView years = findViewById(R.id.years);
        years.setText("Years on Haart: " + response.body().gethAARTDuration());
        TextView preg = findViewById(R.id.pregnant);
        preg.setText("Patient Pregnant: " + response.body().getUntitled67());
        TextView last = findViewById(R.id.last);
        String datey = response.body().getLnmp();
        last.setText("Last Menstrual period: " + formatDate(datey));
        TextView par = findViewById(R.id.parity);
        par.setText("Parity: " + response.body().getParity());
        TextView abo = findViewById(R.id.abortion);
        abo.setText("Abortions: " + response.body().getAbortions());
        TextView con = findViewById(R.id.contra);
        con.setText("Contraceptives Used: " + response.body().getContraceptives());
//        TextView via = findViewById(R.id.via);
//        via.setText("Nurse's Via Results: " + response.body().getViaResult());
        progressBar.setVisibility(View.INVISIBLE);

    }

    private String formatDate(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        long time = sdf.parse(date).getTime();
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(time));

        return ago;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (act.equals("gyne")){
            Intent intent = new Intent(DataActivity.this, GynaecologistActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(DataActivity.this, CaseActivity.class);
            startActivity(intent);
        }
    }
}