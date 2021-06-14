package com.ug.cancerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ug.cancerapp.R;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    LinearLayout area;
    EditText username;
    Spinner spinner;
    Button submit;

    String category, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpStatus();

        area = findViewById(R.id.area);
        username = findViewById(R.id.username);
        spinner = findViewById(R.id.information);
        submit = findViewById(R.id.login);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.information, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        category = getIntent().getStringExtra("category");

        if (category.equals("gynecologist")){
            area.setVisibility(View.GONE);
        }else {
            area.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                if (area.getVisibility() == View.GONE){
                    if(user.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Fill in your username", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        startActivity(new Intent(LoginActivity.this, GynaecologistActivity.class));
                    }
                }else {
                    if(user.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Fill in your username", Toast.LENGTH_SHORT).show();
                    }else if(text.equals("Select One")){
                        Toast.makeText(LoginActivity.this, "Select your region", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                    }
                }
            }
        });
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}