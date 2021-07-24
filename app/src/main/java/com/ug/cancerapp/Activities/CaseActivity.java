package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final String SHARED_API = "sharedApi";
    String username, token, facility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviewed Cases");
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

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        caseAdapter = new CaseAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(caseAdapter);

        handleCases();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleCases();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        caseAdapter.setOnItemClickListener(new CaseAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {

            }

            @Override
            public void onDateClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(CaseActivity.this, DataActivity.class);
                intent.putExtra("uuid", instanceId);
                intent.putExtra("extra", "case");
                startActivity(intent);
            }

            @Override
            public void onFeedClick(int position) {

            }
        });
    }

    private void handleCases() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");

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
                    instanceID += cas.getEntry().getInstanceID();
                    studyId += cas.getEntry().getStudyID();
                    age += cas.getEntry().getAge();
//                    via += cas.getViaResult();
//                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, via, "", "", "");
//                    gynecologistList.add(gynecologist);

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
    }
}