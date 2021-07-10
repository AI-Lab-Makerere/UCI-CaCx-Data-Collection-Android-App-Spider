package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Feedback;
import com.ug.cancerapp.Models.Review;
import com.ug.cancerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.EMAIL;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class FeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText notes;
    Spinner spinner;
    Button save, model, cont;
    ProgressDialog progressDialog;
    RelativeLayout feedback;
    LinearLayout mml;
    TextView txtnurse, txtmodel, txtgyne;

    String instanceId, viaResults, moreNotes, nurse, ml_result;
    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    String username, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner = findViewById(R.id.via);
        notes = findViewById(R.id.notes);
        save = findViewById(R.id.save);
        model = findViewById(R.id.model);
        cont = findViewById(R.id.cont);
        feedback = findViewById(R.id.feedback);
        mml = findViewById(R.id.mml);
        txtnurse = findViewById(R.id.nurse);
        txtmodel = findViewById(R.id.models);
        txtgyne = findViewById(R.id.gyne);


        instanceId = getIntent().getStringExtra("uuid");
        nurse = getIntent().getStringExtra("nurse");
        ml_result = getIntent().getStringExtra("ml_result");


        progressDialog = new ProgressDialog(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.information, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moreNotes = notes.getText().toString().trim();

                if (viaResults.equals("Select One")){
                    Toast.makeText(FeedbackActivity.this, "Please select either Positive or Negative", Toast.LENGTH_SHORT).show();
                }
                else if (moreNotes.isEmpty()){
                    Toast.makeText(FeedbackActivity.this, "Please provide some notes supporting your VIA Results", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                    startActivity(new Intent(FeedbackActivity.this, GynaecologistActivity.class));
                    finish();
                }
            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreNotes = notes.getText().toString().trim();

                if (viaResults.equals("Select One")){
                    Toast.makeText(FeedbackActivity.this, "Please select either Positive or Negative", Toast.LENGTH_SHORT).show();
                }
                else if (moreNotes.isEmpty()){
                    Toast.makeText(FeedbackActivity.this, "Please provide some notes supporting your VIA Results", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                    loadModel();
                }
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, GynaecologistActivity.class));
                finish();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        viaResults = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void saveData() {

        progressDialog.setMessage("Just a second");
        progressDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");

        Feedback feedback = new Feedback(instanceId, viaResults, username, moreNotes);

        Call<Review> call = jsonPlaceHolder.feedback("Bearer " + token, feedback);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
               if (!response.isSuccessful()){
                   progressDialog.dismiss();
                   Toast.makeText(FeedbackActivity.this, "Connection Issue: " + response.code(), Toast.LENGTH_SHORT).show();
                   return;
               }

//               String studyId = response.body().getStudyID();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedbackActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadModel() {

        feedback.setVisibility(View.GONE);
        mml.setVisibility(View.VISIBLE);

        txtnurse.setText(nurse);
        if (nurse.equals("Positive")){
            txtnurse.setTextColor(Color.parseColor("#FFA726"));
        }else {
            txtnurse.setTextColor(Color.parseColor("#C33B2F"));
        }
        txtmodel.setText(ml_result);
        if (ml_result.equals("Positive")){
            txtmodel.setTextColor(Color.parseColor("#FFA726"));
        }else {
            txtmodel.setTextColor(Color.parseColor("#C33B2F"));
        }
        txtgyne.setText(viaResults);
        if (viaResults.equals("Positive")){
            txtgyne.setTextColor(Color.parseColor("#FFA726"));
        }else {
            txtgyne.setTextColor(Color.parseColor("#C33B2F"));
        }
    }
}