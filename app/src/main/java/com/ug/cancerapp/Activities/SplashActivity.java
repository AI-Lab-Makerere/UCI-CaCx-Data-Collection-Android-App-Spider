package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Models.Settings;
import com.ug.cancerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ug.cancerapp.Activities.LoginActivity.FACID;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;
import static com.ug.cancerapp.Fragments.FirstFragment.STUDY;

public class SplashActivity extends AppCompatActivity {

    LinearLayout root_layout;
    static int SPLASH_TIME_OUT = 5000;
    boolean InternetCheck = true;

    public static final String SHARED_API = "sharedApi";
    public static final String THRESHOLD = "threshold";
    SharedPreferences sharedPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         sharedPreferences = getSharedPreferences(SHARED_API, MODE_PRIVATE);

        setUpStatus();
        root_layout = findViewById(R.id.root_layout);
        PostDelayedMethod();

    }

    private void PostDelayedMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean InternetResult = checkConnection();
                if (InternetResult){
                    goToHome();
                }else{
                    DialogAppear();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void goToHome() {
        token = sharedPreferences.getString(TOKEN, "");
        String fac_id = sharedPreferences.getString(FACID, "");
        if (token.length() == 0){
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }else {
            if (fac_id.equals("0")){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }else {
                saveThreshold();
            }

        }

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
    }

    private void setUpStatus() {
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
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

    private void saveThreshold() {

        JsonPlaceHolder jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);
        Call<Settings> call = jsonPlaceHolder.setting("Bearer " + token);
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(SplashActivity.this, "There is a connection issue, Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

                String thres = String.valueOf(response.body().getPositive_analysis_threshold());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(THRESHOLD, thres);
                editor.apply();
                Log.v("TAG......", thres);
                startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Its not you its us, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}