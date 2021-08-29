/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and 
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Adapter.GynecologistAdapter;
import com.ug.cancerapp.Adapter.SavedAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class SavedActivity extends AppCompatActivity {

    SavedAdapter savedAdapter;
    List<Gynecologist> gynecologists;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout ns;
    TextView error, message, swipe;
    Button submit;
    EditText search;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri uri;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    RelativeLayout root_layout;
    boolean InternetCheck = true;


//    FormRepository formRepository;
//    Form form;

    public static final String SHARED_API = "sharedApi";
    public static final String CHOD = "chodrine";
    String username, token, facility, text2, ago;
    String yaap = "";
    String look = "";
    Call<List<Case>> call;

    JsonPlaceHolder jsonPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Uploaded Records");
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
        search = findViewById(R.id.search);
        submit = findViewById(R.id.submit);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHOD, "saved");
        editor.apply();


        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Sprite chasingDots = new ThreeBounce();
        progressBar.setIndeterminateDrawable(chasingDots);

//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologists = new ArrayList<>();
        savedAdapter = new SavedAdapter(gynecologists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(savedAdapter);

        boolean InternetResult = checkConnection();
        if (InternetResult){
            handleCases();
        }else {
            DialogAppear();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                look = search.getText().toString();

                if (look.isEmpty()){
                    Toast.makeText(SavedActivity.this, "Please provide a Study ID", Toast.LENGTH_SHORT).show();
                }else {
                    boolean InternetResult = checkConnection();
                    if (InternetResult){
                        handleCases();
                    }else {
                        DialogAppear();
                    }
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean InternetResult = checkConnection();
                if (InternetResult){
                    handleCases();
                }else {
                    DialogAppear();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        savedAdapter.setOnItemClickListener(new SavedAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {
                String instanceId = gynecologists.get(position).getInstanceID();
                Intent intent = new Intent(SavedActivity.this, FeedbackActivity.class);
                intent.putExtra("uuid", instanceId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDateClick(int position) {
                String instanceId = gynecologists.get(position).getInstanceID();
                Intent intent = new Intent(SavedActivity.this, DataActivity.class);
                intent.putExtra("uuid", instanceId);
                intent.putExtra("extra", "nurse");
                startActivity(intent);
                finish();
            }
        });

    }

    private void handleCases() {

        gynecologists.clear();
        progressBar.setVisibility(View.VISIBLE);

        if (look.equals("")){
            call = jsonPlaceHolder.uploaded("Bearer " + token);
        }else {
            call = jsonPlaceHolder.search("Bearer " + token, look);
        }

//        Call<List<Case>> call = jsonPlaceHolder.uploaded("Bearer " + token);
        call.enqueue(new Callback<List<Case>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    String initials = "";
                    String via = "";
                    String date = "";
                    instanceID += cas.getInstanceID();
                    studyId += cas.getStudyID();
                    age += cas.getAge();
                    initials += cas.getInitials();
                    via += cas.getViaResult();
                    date += cas.getCreated_at();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        long time = 0;
                        time = sdf.parse(date).getTime();
                        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                        ago = prettyTime.format(new Date(time));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, ago, via, initials, "", "");
                    gynecologists.add(gynecologist);

                }

                savedAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                ns.setVisibility(View.VISIBLE);
                error.setText("Its not you, its us");
                message.setText("Try again later");
                swipe.setVisibility(View.GONE);
                Toast.makeText(SavedActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
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