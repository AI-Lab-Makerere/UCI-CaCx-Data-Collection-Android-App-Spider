package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.EMAIL;
import static com.ug.cancerapp.Activities.LoginActivity.FACNAME;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class RecordsActivity extends AppCompatActivity {

    ArrayList<Form> formList, formList_search;
    FormAdapter formAdapter;
    RecyclerView recyclerView;
    Dialog dialog;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri uri;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    EditText etSearch;

    public static final String SHARED_API = "sharedApi";
    String username, token, facility;

    JsonPlaceHolder jsonPlaceHolder;

    Date date1, date2, date3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Records");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new LoadDataTask().execute();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                Filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    class LoadDataTask extends AsyncTask<Void, Void, Void>{

        FormRepository formRepository;
        List<Form> formList1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            formRepository = new FormRepository((Application) getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            formList1 = formRepository.getAllForms();
            formList = new ArrayList<>();
            formList_search = new ArrayList<>();

            for (int i = 0; i < formList1.size(); i++){
                formList.add(formList1.get(i));
                formList_search.add(formList1.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            formAdapter = new FormAdapter(formList, RecordsActivity.this);
            recyclerView.setAdapter(formAdapter);

            formAdapter.setOnItemClickListener(new FormAdapter.OnItemClickListener() {
                @Override
                public void onLoadClick(int position) {

                }

                @Override
                public void onViewClick(int position) {
                    String image1 = formList.get(position).getImage1();
                    String image2 = formList.get(position).getImage2();
                    String image3 = formList.get(position).getImage3();
                    String image4 = formList.get(position).getImage4();

                    dialog = new Dialog(RecordsActivity.this);
                    dialog.setContentView(R.layout.viewing_images);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    imageView1 = dialog.findViewById(R.id.image1);
                    imageView2 = dialog.findViewById(R.id.image2);
                    imageView3 = dialog.findViewById(R.id.image3);
                    imageView4 = dialog.findViewById(R.id.image4);

                    byte[] bytes1 = Base64.decode(image1, Base64.DEFAULT);
                    byte[] bytes2 = Base64.decode(image2, Base64.DEFAULT);
                    byte[] bytes3 = Base64.decode(image3, Base64.DEFAULT);
                    byte[] bytes4 = Base64.decode(image4, Base64.DEFAULT);

                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                    bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);

                    imageView1.setImageBitmap(bitmap1);
                    imageView2.setImageBitmap(bitmap2);
                    imageView3.setImageBitmap(bitmap3);
                    imageView4.setImageBitmap(bitmap4);

                    dialog.show();
                }

                @Override
                public void onConsultClick(int position) {
                    Log.v("TAG", "clicked");
                    handleData(position);
                }
            });
        }
    }

    //    filter method
    public void Filter(String charText){

        formList.clear();
        if (charText.length() == 0){
//            load all the data
            formList.addAll(formList_search);
        }else {
//            filter
            for (Form form:formList_search){
                if (form.getStudyID().contains(charText) || form.getVia().contains(charText)  || String.valueOf(form.getAge()).contains(charText)){
                    formList.add(form);
                }
            }

        }
        formAdapter.notifyDataSetChanged();
    }

    private void handleData(int position) {
        
        String studyID = formList.get(position).getStudyID();
        String initial = formList.get(position).getInitials();
        int age = formList.get(position).getAge();
        String district = formList.get(position).getDistrict();
        String county = formList.get(position).getCounty();
        String zone = formList.get(position).getVillage();
        String text = formList.get(position).getHave_symptoms();
        String ss = formList.get(position).getSymptoms();
        if(ss.endsWith(","))
        {
            ss = ss.substring(0,ss.length() - 1);
        }
        String symptom = formList.get(position).getOther_symptoms();
        String text2 = formList.get(position).getScreened_for_cancer();
        String past = formList.get(position).getScreening_results();
        String sss = formList.get(position).getScreening_process();
        if(sss.endsWith(","))
        {
            sss = sss.substring(0,sss.length() - 1);
        }
        String datey = formList.get(position).getLast_screened();
        String treat = formList.get(position).getTreatment();
        String value3 = formList.get(position).getHiv_status();
        String valuex = formList.get(position).getOn_haart();
        int year = formList.get(position).getYears_on_haart();
        String value = formList.get(position).getPregnant();
        String time = formList.get(position).getLast_menstrual();
        int child = formList.get(position).getParity();
        int abort = formList.get(position).getAbortion();
        String choice = formList.get(position).getOn_contraceptives();
        String s4 = formList.get(position).getContraceptives();
        if(s4.endsWith(","))
        {
            s4 = s4.substring(0,ss.length() - 1);
        }
        String sImage = formList.get(position).getImage1();
        String sImage2 = formList.get(position).getImage2();
        String sImage3 = formList.get(position).getImage3();
        String sImage4 = formList.get(position).getImage4();
        String via = formList.get(position).getVia();
        String notes = formList.get(position).getNotes();
        String location = formList.get(position).getLocation();
        String date = formList.get(position).getDate();
        Log.v("TAG", "data");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");
        facility = sharedPreferences.getString(FACNAME, "");
        Log.v("TAG", "data2");

        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(datey);
            date3 = new SimpleDateFormat("dd/MM/yyyy").parse(time);
            date1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(date);
            Log.v("TAG", ""+date1);
            Log.v("TAG", ""+date2);
            Log.v("TAG", ""+date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Capture capture = new Capture(date1, username, facility, studyID, initial,
                district, county, zone, age, text, ss, symptom, text2, sss, past, treat, date2,
                value3, valuex, year, value, date3, child, abort, s4, choice, sImage,
                sImage2, sImage3, sImage4, location, via, notes);

        Log.v("TAG", "data3");

        sendOverNetwork(token, capture);

    }

    private void sendOverNetwork(String token, Capture capture) {
        Log.v("TAG", "data4");
        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Call<String> call = jsonPlaceHolder.capture("Bearer " + token, capture);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(RecordsActivity.this, "Connection Issue: " + response.code() + " error", Toast.LENGTH_SHORT).show();
                    Log.v("TAG", ""+response.code());
                    return;
                }
                String message = response.body();
                Toast.makeText(RecordsActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.v("TAG", message);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RecordsActivity.this, "Something went wrong: " + t.getMessage() , Toast.LENGTH_SHORT).show();
                Log.v("TAG", "Something went wrong: " + t.getMessage());
            }
        });
    }


}