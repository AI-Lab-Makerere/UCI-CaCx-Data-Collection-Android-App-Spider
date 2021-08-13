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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Adapter.CaseAdapter;
import com.ug.cancerapp.Adapter.GynecologistAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.Models.Model;
import com.ug.cancerapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class CaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout ns;
    TextView error, message, swipe;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Gynecologist> gynecologistList;
    CaseAdapter caseAdapter;
    JsonPlaceHolder jsonPlaceHolder;
    Dialog dialog;
    RelativeLayout root_layout;
    boolean InternetCheck = true;

    public static final String SHARED_API = "sharedApi";
    public static final String CHOD = "chodrine";
    String username, token, facility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviewed Cases");

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
        editor.putString(CHOD, "case");
        editor.apply();

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        caseAdapter = new CaseAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(caseAdapter);

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

        caseAdapter.setOnItemClickListener(new CaseAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(CaseActivity.this, FeedbackActivity.class);
                intent.putExtra("uuid", instanceId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDateClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(CaseActivity.this, DataActivity.class);
                intent.putExtra("uuid", instanceId);
                intent.putExtra("extra", "case");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFeedClick(int position) {
                String via = gynecologistList.get(position).getViaResults();
                String notes = gynecologistList.get(position).getGynNotes();

                dialog = new Dialog(CaseActivity.this);
                dialog.setContentView(R.layout.feedback);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                TextView results = dialog.findViewById(R.id.results);
                TextView note = dialog.findViewById(R.id.notes);

                results.setText(via);
                note.setText(notes);

                dialog.show();
            }
        });
    }

    private void handleCases() {

        gynecologistList.clear();
        progressBar.setVisibility(View.VISIBLE);

        Call<List<Model>> call = jsonPlaceHolder.model("Bearer " + token);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (!response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    ns.setVisibility(View.VISIBLE);
                    return;
                }

                List<Model> cases = response.body();
                for (Model cas: cases){
                    String instanceID = "";
                    String studyId = "";
                    String age = "";
                    String via = "";
                    String notes = "";
                    instanceID += cas.getEntry().getInstanceID();
                    studyId += cas.getEntry().getStudyID();
                    age += cas.getEntry().getAge();
                    via += cas.getViaResult();
                    notes += cas.getNotes();
                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, "", via, "", "", notes);
//                    (instanceID, studyId, age, date, via, "", gyneResults, gynenotes)
                    gynecologistList.add(gynecologist);

                }

                caseAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                ns.setVisibility(View.VISIBLE);
                error.setText("Its not you, its us");
                message.setText("Try again later");
                swipe.setVisibility(View.GONE);
                Toast.makeText(CaseActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CaseActivity.this, HomeActivity.class));
        finish();
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