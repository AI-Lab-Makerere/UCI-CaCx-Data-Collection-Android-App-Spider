package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Adapter.FormAdapter2;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.Capture2;
import com.ug.cancerapp.Models.Picture;
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
import static com.ug.cancerapp.Activities.LoginActivity.FACID;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

public class SavedActivity extends AppCompatActivity {

    ArrayList<Form> formList, formList_search;
    FormAdapter2 formAdapter;
    RecyclerView recyclerView;
    Dialog dialog;
    Button submit;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri uri;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    EditText etSearch;
    Date parsedDate3;

    FormRepository formRepository;
    Form form;

    public static final String SHARED_API = "sharedApi";
    String username, token, facility, text2;

    JsonPlaceHolder jsonPlaceHolder;
    ProgressDialog progressDialog;

    Date date1, date2, date3;
    long i = 0;
    Call<String> call;
    ArrayList<Long> arrayForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Uploaded Records");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        submit = findViewById(R.id.submit);

        progressDialog = new ProgressDialog(this);

        arrayForm = new ArrayList<>();

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

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if (arrayForm.size() == 0){
////                    Toast.makeText(RecordsActivity.this, "Select the files to Upload", Toast.LENGTH_SHORT).show();
////                }else {
////
////                    new RecordsActivity.SendDataTask().execute();
////
////                }
//
//            }
//        });
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        FormRepository formRepository;
        List<Form> formList1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            formRepository = new FormRepository((Application) getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            formList1 = formRepository.getAllFormsUploaded();
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

            formAdapter = new FormAdapter2(formList, SavedActivity.this);
            recyclerView.setAdapter(formAdapter);

            formAdapter.setOnItemClickListener(new FormAdapter2.OnItemClickListener() {
                @Override
                public void onLoadClick(int position) {
                    dialog = new Dialog(SavedActivity.this);
                    dialog.setContentView(R.layout.load_data);

                    loadData(dialog, position);

                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }

                @Override
                public void onViewClick(int position) {
                    String image1 = formList.get(position).getImage1();
                    String image2 = formList.get(position).getImage2();
                    String image3 = formList.get(position).getImage3();
                    String image4 = formList.get(position).getImage4();

//                    Toast.makeText(RecordsActivity.this, image1, Toast.LENGTH_SHORT).show();

                    dialog = new Dialog(SavedActivity.this);
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
//                    Log.v("TAG", "clicked");
//                    progressDialog.setMessage("Uploading Data");
//                    progressDialog.show();
//                    handleData(position);
                }

                @Override
                public void onCheckedClick(int position) {
//                    long key = formList.get(position).getKey();
//                    arrayForm.add(key);
                }

                @Override
                public void onNotCheckedClick(int position) {
//                    long key = formList.get(position).getKey();
//                    arrayForm.remove(key);
//                    Toast.makeText(SavedActivity.this, "bad", Toast.LENGTH_SHORT).show();
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
                if (form.getStudyID().contains(charText) || form.getVia().contains(charText) || form.getInitials().contains(charText)){
                    formList.add(form);
                }
            }

        }
        formAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SimpleDateFormat")
    private void handleData(int position) {

        String studyID = formList.get(position).getStudyID();
        String initial = formList.get(position).getInitials();
        int age = formList.get(position).getAge();
        String district = formList.get(position).getDistrict();
        String county = formList.get(position).getCounty();
        String zone = formList.get(position).getVillage();
        String text = formList.get(position).getHave_symptoms();
        String ss = formList.get(position).getSymptoms();
        String symptom = formList.get(position).getOther_symptoms();
        text2 = formList.get(position).getScreened_for_cancer();
        String past = formList.get(position).getScreening_results();
        String sss = formList.get(position).getScreening_process();
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
        String sImage = formList.get(position).getImage1();
        String sImage2 = formList.get(position).getImage2();
        String sImage3 = formList.get(position).getImage3();
        String sImage4 = formList.get(position).getImage4();
        String via = formList.get(position).getVia();
        String notes = formList.get(position).getNotes();
        String location = formList.get(position).getLocation();
        String date = formList.get(position).getDate();

        String instanceID = formList.get(position).getInstanceID();
        float neg = formList.get(position).getPicture1_nc();
        float pos = formList.get(position).getPicture1_pc();
        String var = formList.get(position).getPicture1_via();
        float neg2 = formList.get(position).getPicture2_nc();
        float pos2 = formList.get(position).getPicture2_pc();
        String var2 = formList.get(position).getPicture2_via();
        float neg3 = formList.get(position).getPicture3_nc();
        float pos3 = formList.get(position).getPicture3_pc();
        String var3 = formList.get(position).getPicture3_via();
        float neg4 = formList.get(position).getPicture4_nc();
        float pos4 = formList.get(position).getPicture4_pc();
        String var4 = formList.get(position).getPicture4_via();
        String ml_result = formList.get(position).getDiagnosis();

        long key = formList.get(position).getKey();
        Log.v("TAG", "data");

        FormDAO formDAO = FormDatabase.getInstance(SavedActivity.this).formDAO();
        formDAO.UpdateConsult(true, key);
        Log.v("TAG", "" + key);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");
        facility = sharedPreferences.getString(FACID, "");
        int fac_id = Integer.parseInt(facility);
        Log.v("TAG", "data2");


        try {
            if (!datey.isEmpty()){
                date2 = new SimpleDateFormat("dd/MM/yyyy").parse(datey);
            }
            date3 = new SimpleDateFormat("dd/MM/yyyy").parse(time);
            date1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(date);
            Log.v("TAG", ""+date1);
            Log.v("TAG", ""+date2);
            Log.v("TAG", ""+date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picture picture1 = new Picture(neg, pos, var, sImage, "");
        Picture picture2 = new Picture(neg2, pos2, var2, sImage2, "");
        Picture picture3 = new Picture(neg3, pos3, var3, sImage3, "");
        Picture picture4 = new Picture(neg4, pos4, var4, sImage4, "");

        Capture capture = new Capture(instanceID, date1, username, fac_id, studyID, initial,
                district, county, zone, age, text, ss, symptom, text2, sss, past, treat, date2,
                value3, valuex, year, value, date3, child, abort, s4, choice, location, via, notes,
                ml_result,true, picture1, picture2, picture3, picture4);

        Capture2 capture2 = new Capture2(instanceID, date1, username, fac_id, studyID, initial,
                district, county, zone, age, text, ss, symptom, text2,
                value3, valuex, year, value, date3, child, abort, s4, choice, location, via, notes,
                ml_result, true, picture1, picture2, picture3, picture4);

        Log.v("TAG", "data3");

        sendOverNetwork(token, capture, capture2);

    }

    private void sendOverNetwork(String token, Capture capture, Capture2 capture2) {
        Log.v("TAG", "data4");
        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        if (text2.equals("No")){
            call = jsonPlaceHolder.capture2("Bearer " + token, capture2);
        }else {
            call = jsonPlaceHolder.capture("Bearer " + token, capture);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SavedActivity.this, "Connection Issue: " + response.code() + " error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Log.v("TAG", ""+response.code());
                        }
                    });
                    return;
                }

                String message = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SavedActivity.this, message, Toast.LENGTH_SHORT).show();
                        Log.v("TAG", message);
                        progressDialog.dismiss();
                    }
                });


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SavedActivity.this, "Something went wrong: " + t.getMessage() , Toast.LENGTH_SHORT).show();
                        Log.v("TAG", "Something went wrong: " + t.getMessage());
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData(Dialog dialog, int position) {

        TextView study = dialog.findViewById(R.id.studyId);
        study.setText("StudyID: " + formList.get(position).getStudyID());
        TextView initial = dialog.findViewById(R.id.initials);
        initial.setText("Initials: " + formList.get(position).getInitials());
        TextView age = dialog.findViewById(R.id.age);
        age.setText("Age: " + formList.get(position).getAge());
        TextView address = dialog.findViewById(R.id.address);
        address.setText("Address: " + formList.get(position).getVillage() + "-" + formList.get(position).getCounty() + " (" + formList.get(position).getDistrict() + ")");
        TextView hav_symptoms = dialog.findViewById(R.id.signs);
        hav_symptoms.setText("Symptoms for Cancer: " + formList.get(position).getHave_symptoms());
        TextView symptoms = dialog.findViewById(R.id.symptoms);
        symptoms.setText("Symptoms: " + formList.get(position).getSymptoms());
        TextView other_symptoms = dialog.findViewById(R.id.other_symptoms);
        other_symptoms.setText("Other Symptoms: " + formList.get(position).getOther_symptoms());
        TextView screened = dialog.findViewById(R.id.screened);
        screened.setText("Ever Screened for Cancer: " + formList.get(position).getScreened_for_cancer());
        TextView screened_date = dialog.findViewById(R.id.date);
        screened_date.setText("Screening Date: " + formList.get(position).getLast_screened());
        TextView vr = dialog.findViewById(R.id.results);
        vr.setText("VIA Results: " + formList.get(position).getScreening_results());
        TextView screened_pr = dialog.findViewById(R.id.process);
        screened_pr.setText("Screening Process: " + formList.get(position).getScreening_process());
        TextView treatement = dialog.findViewById(R.id.treatment);
        treatement.setText("Treatment Provided: " + formList.get(position).getTreatment());
        TextView hiv = dialog.findViewById(R.id.hiv);
        hiv.setText("HIV Status: " + formList.get(position).getHiv_status());
        TextView on_haart = dialog.findViewById(R.id.onHaart);
        on_haart.setText("On Haart: " + formList.get(position).getOn_haart());
        TextView years = dialog.findViewById(R.id.years);
        years.setText("Years on Haart: " + formList.get(position).getYears_on_haart());
        TextView preg = dialog.findViewById(R.id.pregnant);
        preg.setText("Patient Pregnant: " + formList.get(position).getPregnant());
        TextView last = dialog.findViewById(R.id.last);
        last.setText("Last Menstrual period: " + formList.get(position).getLast_menstrual());
        TextView par = dialog.findViewById(R.id.parity);
        par.setText("Parity: " + formList.get(position).getParity());
        TextView abo = dialog.findViewById(R.id.abortion);
        abo.setText("Abortions: " + formList.get(position).getAbortion());
        TextView oncon = dialog.findViewById(R.id.on_con);
        oncon.setText("On Contraceptives: " + formList.get(position).getOn_contraceptives());
        TextView con = dialog.findViewById(R.id.contra);
        con.setText("Contraceptives Used: " + formList.get(position).getContraceptives());
        TextView via = dialog.findViewById(R.id.via);
        String nurse = formList.get(position).getVia();
        via.setText("My Via Results: " + nurse);
        TextView loc = dialog.findViewById(R.id.lesion);
        loc.setText("Location of the Lesion: " + formList.get(position).getLocation());
        TextView notes = dialog.findViewById(R.id.notes);
        notes.setText("My Notes: " + formList.get(position).getNotes());
        TextView model = dialog.findViewById(R.id.model);
        String mmv = formList.get(position).getDiagnosis();
        model.setText("Model Via Results: " + mmv);
        TextView agree = dialog.findViewById(R.id.agreement);
        if (nurse.equals(mmv)){
            agree.setText("You and the model are in: Agreement");
        }else {
            agree.setText("You and the model are in: Discordance");
        }

    }

}