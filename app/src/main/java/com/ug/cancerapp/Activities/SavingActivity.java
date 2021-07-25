package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;
import com.ug.cancerapp.ml.Cancer;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.Fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.Fragments.FifthFragment.ABORTION;
import static com.ug.cancerapp.Fragments.FifthFragment.DATS;
import static com.ug.cancerapp.Fragments.FifthFragment.PARITY;
import static com.ug.cancerapp.Fragments.FifthFragment.PREGNANT;
import static com.ug.cancerapp.Fragments.FirstFragment.AGE;
import static com.ug.cancerapp.Fragments.FirstFragment.COUNTY;
import static com.ug.cancerapp.Fragments.FirstFragment.DISTRICT;
import static com.ug.cancerapp.Fragments.FirstFragment.INITIAL;
import static com.ug.cancerapp.Fragments.FirstFragment.STUDY;
import static com.ug.cancerapp.Fragments.FirstFragment.ZONE;
import static com.ug.cancerapp.Fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.Fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.Fragments.HaartFragment.CHOICE2;
import static com.ug.cancerapp.Fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.Fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.Fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.Fragments.ScreenFragment.METHOD;
import static com.ug.cancerapp.Fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.Fragments.SecondFragment.TEXT;
import static com.ug.cancerapp.Fragments.SixtyFragment.S4;
import static com.ug.cancerapp.Fragments.ThirdFragment.TEXT2;
import static com.ug.cancerapp.Fragments.ViaFragment.LESION;
import static com.ug.cancerapp.Fragments.ViaFragment.NOTES;
import static com.ug.cancerapp.Fragments.ViaFragment.VIA;
import static com.ug.cancerapp.Fragments.YesFragment.SS;
import static com.ug.cancerapp.Fragments.YesOrNoFragment.CHOICES;

public class SavingActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
//    ProgressBar progressBar;
    String s = "";
    int num2 = 0;
    int number = 0;
    int act = 0;
    int ident = 0;

    private FormViewModel formViewModel;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static String uniqueID;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    String via;
    float pos, pos2, pos4, pos3, neg, neg2, neg3, neg4;
    String viar, viar2, viar4, viar3;

    String mmv, sImage, sImage2, sImage3, sImage4;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    String studyID, initial, age, district, county, zone, text, ss, symptom,
            text2, past, method, datey, treat, value3, valuex, year, value, time, child, abort,
            choice, s4, notes, location, diagnosis, format;

    Boolean consult;

    int abortion, children;

    LinearLayout linearLayout;

    TextView nurse, model, agree, message;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Final Screen");


        linearLayout = findViewById(R.id.finals);

        progressDialog = new ProgressDialog(this);

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        format = simpleDateFormat.format(new Date());
        via = sharedPreferences.getString(VIA, "");
        makePredictions();
    }

    private void makePredictions() {

        new newTask().execute();
    }

    class newTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Saving Data, Please give us a second...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            sImage = sharedPreferences.getString(IMAGE, "");
            byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
            bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ident = 1;
            runTensorflowModel(bitmap1);
            sImage2 = sharedPreferences.getString(IMAGE2, "");
            byte[] bytes2 = Base64.decode(sImage2, Base64.DEFAULT);
            bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            ident = 2;
            runTensorflowModel(bitmap2);
            sImage3 = sharedPreferences.getString(IMAGE3, "");
            byte[] bytes3 = Base64.decode(sImage3, Base64.DEFAULT);
            bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
            ident = 3;
            runTensorflowModel(bitmap3);
            sImage4 = sharedPreferences.getString(IMAGE4, "");
            byte[] bytes4 = Base64.decode(sImage4, Base64.DEFAULT);
            bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);
            ident = 4;
            runTensorflowModel(bitmap4);
            getModelResults();
            savingData();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
//            Toast.makeText(SavingActivity.this, "Via: " + viar + "\nVia2: " + viar2 +
//                    "\nVia3: " + viar3 + "\nVia4: " + viar4 + "\nDiagnosis: " + mmv, Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void runTensorflowModel(Bitmap bitmap) {

        try {
            Cancer model = Cancer.newInstance(this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 300, 300, 3}, DataType.FLOAT32);

            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
            tensorImage.load(bitmap);
            ByteBuffer byteBuffer = tensorImage.getBuffer();
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Cancer.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Releases model resources if no longer used.
            model.close();

            if (ident == 1){
                neg = outputFeature0.getFloatArray()[0];
                pos = outputFeature0.getFloatArray()[1];

                if (neg > pos){
                    viar = "Negative";
                }else {
                    viar = "Positive";
                }

            }else if (ident == 2){
                neg2 = outputFeature0.getFloatArray()[0];
                pos2 = outputFeature0.getFloatArray()[1];

                if (neg2 > pos2){
                    viar2 = "Negative";
                }else {
                    viar2 = "Positive";
                }

            }else if (ident == 3){
                neg3 = outputFeature0.getFloatArray()[0];
                pos3 = outputFeature0.getFloatArray()[1];

                if (neg3 > pos3){
                    viar3 = "Negative";
                }else {
                    viar3 = "Positive";
                }

            }else {
                neg4 = outputFeature0.getFloatArray()[0];
                pos4 = outputFeature0.getFloatArray()[1];

                if (neg4 > pos4){
                    viar4 = "Negative";
                }else {
                    viar4 = "Positive";
                }
            }

        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    private void getModelResults() {

        float threshold = (float) 0.5;

        if (viar3.equals(viar4)) {
            mmv = viar3;
        } else {
            if (viar3.equals("Positive")) {
                if (pos3 >= threshold) {
                    mmv = viar3;
                } else {
                    mmv = "Negative";
                }
            } else {
                if (pos4 >= threshold) {
                    mmv = viar4;
                } else {
                    mmv = "Negative";
                }
            }
        }
    }

    private void savingData() {

        studyID = sharedPreferences.getString(STUDY, "");
        initial = sharedPreferences.getString(INITIAL, "");
        age = sharedPreferences.getString(AGE, "");
        number = Integer.parseInt(age);
        district = sharedPreferences.getString(DISTRICT, "");
        county = sharedPreferences.getString(COUNTY, "");
        zone = sharedPreferences.getString(ZONE, "");
        text = sharedPreferences.getString(TEXT, "");
        ss = sharedPreferences.getString(SS, "");
        if (ss.equals("")) {
            ss = "None";
        }
        symptom = sharedPreferences.getString(OTHER, "");
        if (symptom.equals("")) {
            symptom = "None";
        }
        text2 = sharedPreferences.getString(TEXT2, "");
        past = sharedPreferences.getString(CHOICE, "");
        method = sharedPreferences.getString(METHOD, "");
        datey = sharedPreferences.getString(DATEPICKER, "");
        treat = sharedPreferences.getString(TREATMENT, "");
        value3 = sharedPreferences.getString(TEXT3, "");
        valuex = sharedPreferences.getString(CHOICE2, "");
        if (valuex.equals("")) {
            valuex = "No";
        }
        year = sharedPreferences.getString(YEARS, "");
        if (year.isEmpty()) {
            num2 = 0;
        } else {
            num2 = Integer.parseInt(year);
        }
        value = sharedPreferences.getString(PREGNANT, "");
        time = sharedPreferences.getString(DATS, "");
        child = sharedPreferences.getString(PARITY, "");
        children = Integer.parseInt(child);
        abort = sharedPreferences.getString(ABORTION, "");
        abortion = Integer.parseInt(abort);
        choice = sharedPreferences.getString(CHOICES, "");
        s4 = sharedPreferences.getString(S4, "");
        if (s4.equals("")) {
            s4 = "None";
        }

        notes = sharedPreferences.getString(NOTES, "");
        location = sharedPreferences.getString(LESION, "");

        diagnosis = mmv;

        if (mmv.equals(via)){
            consult = false;
        }else {
            consult = true;
        }

        uniqueID = UUID.randomUUID().toString();

        Form form = new Form(format, studyID, initial, district, county, zone, number, text, ss, symptom,
                text2, datey, method, treat, past, value3, valuex, num2, value, time, children, abortion, choice,
                s4, sImage, sImage2, sImage3, sImage4, via, location, notes, diagnosis, consult, false,
                neg, pos, viar, neg2, pos2, viar2, neg3, pos3, viar3, neg4, pos4, viar4, uniqueID);

        formViewModel.insert(form);

    }

    public void viewModel(View view) {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.model);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        nurse = dialog.findViewById(R.id.nurse);
        model = dialog.findViewById(R.id.model);
        agree = dialog.findViewById(R.id.agree);
        message = dialog.findViewById(R.id.check);

        nurse.setText(via);
        model.setText(mmv);

        if (mmv.equals("Positive") && via.equals("Positive")){
            agree.setText("Agreement");
            agree.setTextColor(Color.parseColor("#FFA726"));
        }else if (mmv.equals("Negative") && via.equals("Negative")){
            agree.setText("Agreement");
            agree.setTextColor(Color.parseColor("#FFA726"));
        }else {
            agree.setText("Disagreement");
            agree.setTextColor(Color.parseColor("#C33B2F"));
            message.setVisibility(View.VISIBLE);
        }

        dialog.show();
    }

    public void newForm(View view) {
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, CollectActivity.class));
        finish();
    }

    public void dashboard(View view) {
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, DashBoardActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please click any of the three buttons", Toast.LENGTH_SHORT).show();
    }
}