package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.CurrentUser;
import com.ug.cancerapp.Models.User;
import com.ug.cancerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LinearLayout area;
    EditText username, password;
    Button submit;
    ProgressBar progressBar;

    String category, text, token;

    JsonPlaceHolder jsonPlaceHolder;

    public static final String SHARED_API = "sharedApi";
    public static final String EMAIL = "email";
    public static final String USERID = "userId";
    public static final String FACNAME = "fac_name";
    public static final String FACID = "fac_id";
    public static final String TOKEN= "token";

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



        category = getIntent().getStringExtra("category");
        if (category.equals("gynecologist")) {
            username.setText("clinician@styxtechgroup.com");
            password.setText("Admin123");
        } else {
            username.setText("nurse@styxtechgroup.com");
            password.setText("Admin123");
        }


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
                    sendNetworkRequest(user);
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

                String email = response.body().getEmail();
                String userId = String.valueOf(response.body().getUser_id());
                String facId = String.valueOf(response.body().getHealth_facility_id());
                String facName = response.body().getHealth_facility_name();

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

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);
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
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }

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
}