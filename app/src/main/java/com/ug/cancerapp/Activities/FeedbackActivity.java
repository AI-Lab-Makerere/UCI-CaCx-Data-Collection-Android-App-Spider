package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    Button save;
    ProgressDialog progressDialog;

    String instanceId, viaResults, moreNotes;
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

        instanceId = getIntent().getStringExtra("uuid");

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
                }
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

        progressDialog.setMessage("Submitting your Feedback");
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
               startActivity(new Intent(FeedbackActivity.this, GynaecologistActivity.class));
               finish();
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedbackActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}