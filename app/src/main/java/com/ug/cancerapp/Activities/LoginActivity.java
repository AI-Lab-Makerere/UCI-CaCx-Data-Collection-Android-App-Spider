/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.Client;
import com.ug.cancerapp.Database.ClientDAO;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.CurrentUser;
import com.ug.cancerapp.Models.Nurse;
import com.ug.cancerapp.Models.Settings;
import com.ug.cancerapp.Models.User;
import com.ug.cancerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.SplashActivity.THRESHOLD;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    LinearLayout area;
    LinearLayout root_layout;
    static int SPLASH_TIME_OUT = 1000;
    boolean InternetCheck = true;

    EditText username, password;
    Button submit;
    ProgressBar progressBar;

    String category, text, token;

    JsonPlaceHolder jsonPlaceHolder;
    private FormViewModel formViewModel;
    ClientDAO clientDAO;

    public static final String SHARED_API = "sharedApi";
    public static final String EMAIL = "email";
    public static final String USERID = "userId";
    public static final String FACNAME = "fac_name";
    public static final String FACID = "fac_id";
    public static final String TOKEN= "token";

    String facId, email;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpStatus();

        area = findViewById(R.id.area);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.verify_btn);
        progressBar = findViewById(R.id.otp_progress_bar);
        root_layout = findViewById(R.id.root_layout);

        sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);
        clientDAO = FormDatabase.getInstance(LoginActivity.this).clientDAO();

        category = getIntent().getStringExtra("category");
//        if (category.equals("gynecologist")) {
//            username.setText("clinician@styxtechgroup.com");
//            password.setText("Admin123");
//        } else {
//            username.setText("nurse@styxtechgroup.com");
//            password.setText("Admin123");
//        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userN = username.getText().toString();
                String pass = password.getText().toString();

                if (userN.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Fill in your username", Toast.LENGTH_SHORT).show();
                } else if (pass.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Fill in your password", Toast.LENGTH_SHORT).show();
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    submit.setEnabled(false);

                    User user = new User(userN, pass, getDeviceName());
                    boolean InternetResult = checkConnection();
                    if (InternetResult){
                        sendNetworkRequest(user);
                    }else {
                        DialogAppear();
                    }

                }
            }
        });
    }

    private void sendNetworkRequest(User user) {

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        Call<String> call = jsonPlaceHolder.login(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Connection Issue: " + response.code() + " error", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    submit.setEnabled(true);
                    return;
                }

                token = response.body();
//                Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                LoaduserInformation(token);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something is wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                submit.setEnabled(true);
            }
        });
    }

    private void LoaduserInformation(String token) {

        Call<CurrentUser> call = jsonPlaceHolder.userProfile("Bearer " + token);
        call.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Connection Issue: " + response.code() + " error", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    submit.setEnabled(true);
                    return;
                }

                email = response.body().getEmail();
                String userId = String.valueOf(response.body().getUser_id());
                facId = String.valueOf(response.body().getHealth_facility_id());
                String facName = response.body().getHealth_facility_name();
//                Toast.makeText(LoginActivity.this, facId, Toast.LENGTH_SHORT).show();
                saveData(email, userId, facId, facName, token);

            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something is wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                submit.setEnabled(true);
            }
        });
    }

    private void saveData(String email, String userId, String facId, String facName, String token) {


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, email);
        editor.putString(USERID, userId);
        editor.putString(FACID, facId);
        editor.putString(FACNAME, facName);
        editor.putString(TOKEN, token);

        editor.apply();
        progressBar.setVisibility(View.INVISIBLE);
        submit.setEnabled(true);

        if (category.equals("gynecologist")) {
            progressBar.setVisibility(View.INVISIBLE);
            submit.setEnabled(true);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
//            saveThreshold();
            getNurses();
        }

    }

    private void getNurses() {
        Call<List<Nurse>> call = jsonPlaceHolder.nurses("Bearer " + token);
        call.enqueue(new Callback<List<Nurse>>() {
            @Override
            public void onResponse(Call<List<Nurse>> call, Response<List<Nurse>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Connection Issue: " + response.code() + " error", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    submit.setEnabled(true);
                    return;
                }
                List<Nurse> nurses = response.body();
                for (Nurse nurse: nurses){
                    String name = "";
                    int health_facility_id = 0;
                    int id = 0;
                    String emails = "";
                    name += nurse.getName();
                    emails += nurse.getEmail();
                    id += nurse.getId();
                    health_facility_id += nurse.getHealth_facility_id();
                    if (!clientDAO.clientExist(id)){
                        Client client = new Client(id, health_facility_id, name, emails);
                        formViewModel.insertNurse(client);
                    }

                }
                progressBar.setVisibility(View.INVISIBLE);
                submit.setEnabled(true);
                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<List<Nurse>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something is wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                submit.setEnabled(true);
            }
        });
    }


    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private void setUpStatus() {
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        finish();
    }

    private void saveThreshold() {

        JsonPlaceHolder jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);
        Call<Settings> call = jsonPlaceHolder.setting("Bearer " + token);
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "There is a connection issue, Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

                String thres = String.valueOf(response.body().getPositive_analysis_threshold());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(THRESHOLD, thres);
                editor.apply();
                Log.v("TAG......", thres);
                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Its not you its us, please try again later", Toast.LENGTH_SHORT).show();
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
        submit.setEnabled(true);
    }
}