package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.ug.cancerapp.R;

import java.util.Random;

public class LoopActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

        btn1 = findViewById(R.id.one);
        btn2 = findViewById(R.id.one1);
        btn3 = findViewById(R.id.one2);
        btn4 = findViewById(R.id.one3);

        Random ran = new Random();
        final Button[] all = {btn1, btn2, btn3, btn4};
        Handler handler = new Handler();
        for (int a = 0; a <= all.length; a++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Button btn5 = all[ran.nextInt(all.length)];
                    btn5.setBackgroundColor(Color.RED);
                }
            }, 1000 * a);
        }
    }
}