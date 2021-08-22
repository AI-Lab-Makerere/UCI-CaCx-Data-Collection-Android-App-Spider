/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and 
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Feedback;
import com.ug.cancerapp.Models.Information;
import com.ug.cancerapp.Models.Review;
import com.ug.cancerapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static com.ug.cancerapp.Activities.GynaecologistActivity.CHOD;
import static com.ug.cancerapp.Activities.LoginActivity.EMAIL;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class FeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText notes;
    Spinner spinner;
    Button save, model, cont;
    ProgressDialog progressDialog;
    RelativeLayout feedback;
    LinearLayout mml, mut;
    TextView txtnurse, txtmodel, txtgyne, txttitle;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    URL url1, url2, url3, url4;

    String instanceId, viaResults, moreNotes, nurse, ml_result, image1, image2, image3, image4;
    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    String username, token, chod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spinner = findViewById(R.id.via);
        notes = findViewById(R.id.notes);
        save = findViewById(R.id.save);
        feedback = findViewById(R.id.feedback);
        mut = findViewById(R.id.gyn);
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        txttitle = findViewById(R.id.title);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");
        chod = sharedPreferences.getString(CHOD, "");

        if (chod.equals("gyn")){
            getSupportActionBar().setTitle("Feedback");
            txttitle.setText("Provide your Feedback basing on the images below");
            mut.setVisibility(View.VISIBLE);
        }else {
            getSupportActionBar().setTitle("Images");
            txttitle.setText("Images of the selected Case");
        }

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

                if (viaResults.equals("Select either Positive or Negative")){
                    Toast.makeText(FeedbackActivity.this, "Please select either Positive or Negative", Toast.LENGTH_SHORT).show();
                }
                else if (moreNotes.isEmpty()){
                    Toast.makeText(FeedbackActivity.this, "Please provide some notes supporting your VIA Results", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                }
            }
        });

        loadImages();

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, ImageViewActivity.class);
                intent.putExtra("image", image1);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, ImageViewActivity.class);
                intent.putExtra("image", image2);
                startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, ImageViewActivity.class);
                intent.putExtra("image", image3);
                startActivity(intent);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, ImageViewActivity.class);
                intent.putExtra("image", image4);
                startActivity(intent);
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


    private void loadImages() {

        progressDialog.setMessage("Loading Images...");
        progressDialog.show();

        Call<Information> call = jsonPlaceHolder.patient("Bearer " + token, instanceId);
        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (!response.isSuccessful()){
//                    progressBar.setVisibility(View.INVISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(FeedbackActivity.this, "Connection issue: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                image1 = response.body().getImages().get(0).getPicture1_before().getRequest_image_url();
                image2 = response.body().getImages().get(1).getPicture2_before().getRequest_image_url();
                image3 = response.body().getImages().get(2).getPicture3_before().getRequest_image_url();
                image4 = response.body().getImages().get(3).getPicture4_before().getRequest_image_url();

//                Toast.makeText(FeedbackActivity.this, image1, Toast.LENGTH_SHORT).show();
                Picasso.get().load(image1).into(imageView1);
                Picasso.get().load(image2).into(imageView2);
                Picasso.get().load(image3).into(imageView3);
                Picasso.get().load(image4).into(imageView4);

                progressDialog.dismiss();
//                loadBitmaps();

            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedbackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadBitmaps() throws IOException {

//        new loadingImages().execute();
        Picasso.get().load(image1).into(imageView1);
        progressDialog.dismiss();

    }

    class loadingImages extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                url1 = new URL(image1);
                bitmap1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            imageView1.setImageBitmap(bitmap1);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (chod.equals("gyn")){
            startActivity(new Intent(FeedbackActivity.this, GynaecologistActivity.class));
        }else if (chod.equals("case")){
            startActivity(new Intent(FeedbackActivity.this, CaseActivity.class));
        }else if (chod.equals("saved")){
            startActivity(new Intent(FeedbackActivity.this, SavedActivity.class));
        }else {
            startActivity(new Intent(FeedbackActivity.this, ResultsActivity.class));
        }
    }
}