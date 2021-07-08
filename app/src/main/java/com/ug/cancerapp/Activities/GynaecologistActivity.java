package com.ug.cancerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.ug.cancerapp.Adapter.GynecologistAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.R;

import java.util.ArrayList;
import java.util.List;

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

    List<Gynecologist> gynecologistList;
    GynecologistAdapter gynecologistAdapter;
    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    String username, token, facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gynaecologist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gynaecologist");

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

//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        gynecologistAdapter = new GynecologistAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(gynecologistAdapter);

        handleCases();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleCases();
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
                startActivity(intent);
            }

            @Override
            public void onFeedClick(int position) {
                String instanceId = gynecologistList.get(position).getInstanceID();
                Intent intent = new Intent(GynaecologistActivity.this, FeedbackActivity.class);
                intent.putExtra("uuid", instanceId);
                startActivity(intent);
            }
        });
    }

    private void handleCases() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");

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
                    String via = "";
                    instanceID += cas.getInstanceID();
                    studyId += cas.getStudyID();
                    age += cas.getAge();
                    via += cas.getViaResults();

                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, via, "", "");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            //do something
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(GynaecologistActivity.this, WelcomeActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}