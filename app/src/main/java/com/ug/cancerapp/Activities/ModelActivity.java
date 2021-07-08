package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;

import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera1Fragment.VR;
import static com.ug.cancerapp.Fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLON3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLOP3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.VR3;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLON4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLOP4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.VR4;
import static com.ug.cancerapp.Fragments.ViaFragment.VIA;

public class ModelActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3, imageView4;
    TextView txtmm, txtnv;
    Button btnAgree, btnCont;

    public static final String SHARED_PREFS = "sharedPrefs";
//    public static String uniqueID;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    String via;
    float pos4, pos3;
    String viar4, viar3;
    float threshold = (float) 0.3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Model Results");

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        txtmm = findViewById(R.id.mmvia);
        txtnv = findViewById(R.id.nursevia);
        btnAgree = findViewById(R.id.agree);
        btnCont = findViewById(R.id.cont);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String sImage = sharedPreferences.getString(IMAGE, "");
        String sImage2 = sharedPreferences.getString(IMAGE2, "");
        String sImage3 = sharedPreferences.getString(IMAGE3, "");
        String sImage4 = sharedPreferences.getString(IMAGE4, "");

        byte[] bytes1 = Base64.decode(sImage, Base64.DEFAULT);
        byte[] bytes2 = Base64.decode(sImage2, Base64.DEFAULT);
        byte[] bytes3 = Base64.decode(sImage3, Base64.DEFAULT);
        byte[] bytes4 = Base64.decode(sImage4, Base64.DEFAULT);

        bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
        bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
        bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
        bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);

        imageView1.setImageBitmap(bitmap1);
        imageView2.setImageBitmap(bitmap2);
        imageView3.setImageBitmap(bitmap3);
        imageView4.setImageBitmap(bitmap4);

        String positive3 = sharedPreferences.getString(FLOP3, "");
        pos3 = Float.parseFloat(positive3);
        viar3 = sharedPreferences.getString(VR3, "");

        String positive4 = sharedPreferences.getString(FLOP4, "");
        pos4 = Float.parseFloat(positive4);
        viar4 = sharedPreferences.getString(VR4, "");

        via = sharedPreferences.getString(VIA, "");
        txtnv.setText(via);

//        nurse = txtnv.getText().toString();
        if (via.equals("Positive")){
            txtnv.setTextColor(Color.parseColor("#FFA726"));
        }else {
            txtnv.setTextColor(Color.parseColor("#C33B2F"));
        }
        getPrediction();

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.clear();
//                editor.apply();
//                Toast.makeText(ModelActivity.this, "Task Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(ModelActivity.this, DashBoardActivity.class));
            }
        });
    }

    private void getPrediction() {


        if (viar3.equals(viar4)){
            txtmm.setText(viar3);
            if (viar3.equals("Positive")){
                txtmm.setTextColor(Color.parseColor("#FFA726"));
            }else {
                txtmm.setTextColor(Color.parseColor("#C33B2F"));
            }
            button(viar3);

        }else {
           if (viar3.equals("Positive")){
              if (pos3 >= threshold){
                  Toast.makeText(this, "first " + pos4, Toast.LENGTH_SHORT).show();
                  txtmm.setText(viar3);
                  txtmm.setTextColor(Color.parseColor("#FFA726"));
                  button(viar3);
              }else {
                  viar3 = "Negative";
                  txtmm.setText(viar3);
                  txtmm.setTextColor(Color.parseColor("#C33B2F"));
                  button(viar3);
              }
           }else {
               if (pos4 >= threshold){
                   Toast.makeText(this, "Second " + pos4, Toast.LENGTH_SHORT).show();
                   txtmm.setText(viar4);
                   txtmm.setTextColor(Color.parseColor("#FFA726"));
                   button(viar4);
               }else {
                   viar4 = "Negative";
                   txtmm.setText(viar4);
                   txtmm.setTextColor(Color.parseColor("#C33B2F"));
                   button(viar4);
               }
           }
        }
    }

    private void button(String im1) {
        if (im1.equals(via)){
            btnAgree.setText("Agreement");
        }else {
            btnAgree.setText("Discordance");
            btnAgree.setBackgroundResource(R.drawable.primary_btn2);
        }
    }
}