package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.ug.cancerapp.Adapter.ReviewAdapter;
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

    public static final String SHARED_API = "sharedApi";
    String username, token, facility;

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

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Sprite chasingDots = new ThreeBounce();
        progressBar.setIndeterminateDrawable(chasingDots);

//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gynecologistList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(gynecologistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(reviewAdapter);

        ReviewedCases();

        reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(int position) {

            }

            @Override
            public void onDateClick(int position) {

            }

            @Override
            public void onFeedClick(int position) {
                String via = gynecologistList.get(position).getGyneVia();
                String notes = gynecologistList.get(position).getGyneNotes();

                dialog = new Dialog(ResultsActivity.this);
                dialog.setContentView(R.layout.feedback);

                results = dialog.findViewById(R.id.results);
                note = dialog.findViewById(R.id.notes);

                results.setText(via);
                note.setText(notes);

                dialog.show();
            }
        });

    }

    private void ReviewedCases() {


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");

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
                    instanceID += cas.getInstanceID();
                    studyId += cas.getStudyID();
                    age += cas.getAge();
                    via += cas.getViaResults();
                    gyneResults += cas.getGyneco().get(0).getViaResult();
                    gynenotes += cas.getGyneco().get(0).getNotes();
                    Gynecologist gynecologist = new Gynecologist(instanceID, studyId, age, via, gynenotes, gyneResults);
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
}