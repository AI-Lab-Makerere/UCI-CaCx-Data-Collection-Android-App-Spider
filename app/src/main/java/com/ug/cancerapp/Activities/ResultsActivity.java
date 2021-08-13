/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and 
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Adapter.ReviewAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class ResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout ns;
    TextView error, message, swipe, results, note;
    Dialog dialog;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Gynecologist> gynecologistList;
    ReviewAdapter reviewAdapter;
    JsonPlaceHolder jsonPlaceHolder;
    RelativeLayout root_layout;
    boolean InternetCheck = true;

    public static final String SHARED_API = "sharedApi";
    public static final String CHOD = "chodrine";
    String username, token, facility, ago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.spin_kit);
        ns = findViewById(R.id.empty);
        swipeRefreshLayout = findViewById(R.id.swipe);
        error = findViewById(R.id.error);
        message = findViewById(R.id.message);
        swipe = findViewById(R.id.swip);
        imageView = findViewById(R.id.wifi);
        root_layout = findViewById(R.id.root_layout);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHOD, "result");
        editor.apply();


        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Sprite chasingDots = new ThreeBounce();
        progressBar.setIndeterminateDrawable(chasingDots);

//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(reviewAdapter);


        boolean InternetResult = checkConnection();
        if (InternetResult){
            ReviewedCases();
        }else {
            DialogAppear();
        }

        reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(ResultsActivity.this, FeedbackActivity.class);
                intent.putExtra("uuid", instanceId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDateClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(ResultsActivity.this, DataActivity.class);
                intent.putExtra("uuid", instanceId);
                intent.putExtra("extra", "expert");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFeedClick(int position) {
                String via = gynecologistList.get(position).getGynResults();
                String notes = gynecologistList.get(position).getGynNotes();

                dialog = new Dialog(ResultsActivity.this);
                dialog.setContentView(R.layout.feedback);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                results = dialog.findViewById(R.id.results);
                note = dialog.findViewById(R.id.notes);

                results.setText(via);
                note.setText(notes);

                dialog.show();
            }
        });

    }

    private void ReviewedCases() {


        gynecologistList.clear();
        progressBar.setVisibility(View.VISIBLE);

        Call<List<Case>> call = jsonPlaceHolder.reviewed("Bearer " + token);
        call.enqueue(new Callback<List<Case>>() {
            @Override
            public void onResponse(Call<List<Case>> call, Response<List<Case>> response) {
                if (!response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    ns.setVisibility(View.VISIBLE);
                    return;
                }
                List<Case> cases = response.body();
                for (Case cas: cases){
                    String instanceID = "";
                    String studyId = "";
                    String age = "";
                    String via = "";
                    String gynenotes = "";
                    String gyneResults = "";
                    String date = "";
                    instanceID += cas.getInstanceID();
                    studyId += cas.getStudyID();
                    age += cas.getAge();
                    via += cas.getViaResult();
                    gyneResults += cas.getGyneco().get(0).getViaResult();
                    gynenotes += cas.getGyneco().get(0).getNotes();
                    date += cas.getMl_via_result();
//                    date += cas.getGyneco().get(0).getDate();
//
//                    try {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                        long time = 0;
//                        time = sdf.parse(date).getTime();
//                        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
//                        ago = prettyTime.format(new Date(time)) + " ago";
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, date, via, "", gyneResults, gynenotes);
                    gynecologistList.add(gynecologist);

                }

                reviewAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                ns.setVisibility(View.VISIBLE);
                error.setText("Its not you, its us");
                message.setText("Try again later");
                swipe.setVisibility(View.GONE);
                Toast.makeText(ResultsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Tag", "Error: " + t.getMessage());
            }
        });
    }

    private boolean checkConnection() {
        if (isOnline()){
            return InternetCheck;
        }else{
            InternetCheck = false;
            return InternetCheck;
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private void DialogAppear() {
        Snackbar snackbar = Snackbar.make(root_layout, " ", Snackbar.LENGTH_INDEFINITE);
        View custom = getLayoutInflater().inflate(R.layout.snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0,0,0,0);
        (custom.findViewById(R.id.connect)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        snackbarLayout.addView(custom, 0);
        snackbar.show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}