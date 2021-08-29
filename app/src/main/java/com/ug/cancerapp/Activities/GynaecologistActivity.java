/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and 
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Adapter.GynecologistAdapter;
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

public class GynaecologistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout ns;
    TextView error, message, swipe;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout root_layout;
    boolean InternetCheck = true;

    List<Gynecologist> gynecologistList;
    GynecologistAdapter gynecologistAdapter;
    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    public static final String CHOD = "chodrine";
    String username, token, facility, ago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gynaecologist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cases To Review");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHOD, "gyn");
        editor.apply();

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.spin_kit);
        ns = findViewById(R.id.empty);
        swipeRefreshLayout = findViewById(R.id.swipe);
        error = findViewById(R.id.error);
        message = findViewById(R.id.message);
        swipe = findViewById(R.id.swip);
        imageView = findViewById(R.id.wifi);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Sprite chasingDots = new ThreeBounce();
        progressBar.setIndeterminateDrawable(chasingDots);
        root_layout = findViewById(R.id.root_layout);

//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        gynecologistAdapter = new GynecologistAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(gynecologistAdapter);

        boolean InternetResult = checkConnection();
        if (InternetResult){
            handleCases();
        }else {
            DialogAppear();
        }

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

        gynecologistAdapter.setOnItemClickListener(new GynecologistAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {

            }

            @Override
            public void onDateClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(GynaecologistActivity.this, DataActivity.class);
                intent.putExtra("uuid", instanceId);
                intent.putExtra("extra", "gyne");
                startActivity(intent);
            }

            @Override
            public void onFeedClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
//                String nurse = gynecologistList.get(position).getNurse();
//                String ml_result = gynecologistList.get(position).getMl_via_result();
                Intent intent = new Intent(GynaecologistActivity.this, FeedbackActivity.class);
                intent.putExtra("uuid", instanceId);
//                intent.putExtra("nurse", nurse);
//                intent.putExtra("ml_result", ml_result);
                startActivity(intent);
            }
        });
    }

    private void handleCases() {

        gynecologistList.clear();
        progressBar.setVisibility(View.VISIBLE);

        Call<List<Case>> call = jsonPlaceHolder.cases("Bearer " + token);
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
                    String date = "";
                    instanceID += cas.getInstanceID();
                    studyId += cas.getStudyID();
                    age += cas.getAge();
                    date += cas.getCreated_at();

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        long time = 0;
                        time = sdf.parse(date).getTime();
                        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                        ago = prettyTime.format(new Date(time)) + " ago";
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, ago, "", "", "", "");
                    gynecologistList.add(gynecologist);

                }

                gynecologistAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                ns.setVisibility(View.VISIBLE);
                error.setText("Its not you, its us");
                message.setText("Try again later");
                swipe.setVisibility(View.GONE);
                Toast.makeText(GynaecologistActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
        startActivity(new Intent(GynaecologistActivity.this, HomeActivity.class));
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