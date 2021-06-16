package com.ug.cancerapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ug.cancerapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ug.cancerapp.Fragments.YesFragment.SS;

public class DashBoardActivity extends AppCompatActivity {

    public static final String SHARED_API = "sharedApi";
    public static final String SHARED_PREFS = "sharedPrefs";
    Date date1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

//        setUpStatus();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UDA");

//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        String contra = "Apple, Oranges, Mangoes,";
//
//        if(contra.endsWith(","))
//        {
//            contra = contra.substring(0,contra.length() - 1);
//        }
//        Toast.makeText(this, contra, Toast.LENGTH_SHORT).show();
//        String[] elements = contra.split(",");
//        List<String> fixedLengthList = Arrays.asList(elements);
//        ArrayList<String> listOfString = new ArrayList<String>(fixedLengthList);
//
//        String sDate1="16-06-2021-08-25-58";
//        try {
////            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//            date1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(sDate1);
//            Log.v("TAG", ""+date1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(sDate1+"\t"+date1);
//        Log.v("TAG", date.toString());
    }

    public void collect(View view) {
        startActivity(new Intent(DashBoardActivity.this, CollectActivity.class));
    }

    public void results(View view) {
        startActivity(new Intent(DashBoardActivity.this, ResultsActivity.class));
    }

    public void diagnosis(View view) {
        startActivity(new Intent(DashBoardActivity.this, RecordsActivity.class));
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
            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}