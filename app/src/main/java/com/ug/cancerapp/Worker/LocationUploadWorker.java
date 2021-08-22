/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Worker;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

import static com.ug.cancerapp.Activities.LoginActivity.EMAIL;
import static com.ug.cancerapp.Activities.LoginActivity.FACID;
import static com.ug.cancerapp.Activities.LoginActivity.TOKEN;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ug.cancerapp.Activities.SendingActivity;
import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Apis.ApiClient;
import com.ug.cancerapp.Apis.JsonPlaceHolder;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Models.ApiError;
import com.ug.cancerapp.Models.Capture;
import com.ug.cancerapp.Models.Capture2;
import com.ug.cancerapp.Models.Picture;
import com.ug.cancerapp.R;
import com.ug.cancerapp.helpers.ErrorUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationUploadWorker extends Worker {


    private NotificationManager notificationManager;
    public static final String KEY_X_ARG = "X";
    FormRepository formRepository;
    Form form;
    FormDAO formDAO;
    public static final String SHARED_API = "sharedApi";
    String username, token, facility, text2;
    int fac_id;
    public static JsonPlaceHolder jsonPlaceHolder;
    Date date1, date2, date3;
    Call<String> call;

    public LocationUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        formDAO = FormDatabase.getInstance(getApplicationContext()).formDAO();
        formRepository = new FormRepository((Application) getApplicationContext());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_API, MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");
        username = sharedPreferences.getString(EMAIL, "");
        facility = sharedPreferences.getString(FACID, "");
        fac_id = Integer.parseInt(facility);
    }

    @NonNull
    @Override
    public Result doWork() {

        String progress = "Form Upload In progress";
        setForegroundAsync(createForegroundInfo(progress));


        String x = getInputData().getString(KEY_X_ARG);


        String[] elements = x.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> listOfString = new ArrayList<>(fixedLenghtList);
        ArrayList<Long> counting = new ArrayList<>();
        for (String i: listOfString) {
            counting.add(Long.parseLong(i));
        }


        for( Long value : counting ){
            try {
                Thread.sleep(2000);
                form = formRepository.getOnlyOne(value);
                String studyID = form.getStudyID();
                Log.d("LocationUploadWorker", "Testing: " + studyID);
                sendData(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return Result.success();

    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = "my_channel_id_1";
        String title = "UCI CaCx...";
        String cancel = "Cancel";
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title + progress)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_data)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(100, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Create a Notification channel
        CharSequence name = "Worker Channel";
        String description = "Uploading data";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("my_channel_id_1", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel);

    }

    @SuppressLint("SimpleDateFormat")
    private void sendData(Long value) {
        form = formRepository.getOnlyOne(value);
        String studyID = form.getStudyID();
        String initial = form.getInitials();
        int age = form.getAge();
        String district = form.getDistrict();
        String county = form.getCounty();
        String zone = form.getVillage();
        String text = form.getHave_symptoms();
        String ss = form.getSymptoms();
        String symptom = form.getOther_symptoms();
        text2 = form.getScreened_for_cancer();
        String past = form.getScreening_results();
        String sss = form.getScreening_process();
        String datey = form.getLast_screened();
        String treat = form.getTreatment();
        String value3 = form.getHiv_status();
        String valuex = form.getOn_haart();
        int year = form.getYears_on_haart();
        String valuev = form.getPregnant();
        String time = form.getLast_menstrual();
        int child = form.getParity();
        int abort = form.getAbortion();
        String choice = form.getOn_contraceptives();
        String s4 = form.getContraceptives();
        String sImage = form.getImage1();
        String sImage2 = form.getImage2();
        String sImage3 = form.getImage3();
        String sImage4 = form.getImage4();
        String via = form.getVia();
        String notes = form.getNotes();
        String location = form.getLocation();
        String date = form.getDate();

        String instanceID = form.getInstanceID();
        float neg = form.getPicture1_nc();
        float pos = form.getPicture1_pc();
        String var = form.getPicture1_via();
        float neg2 = form.getPicture2_nc();
        float pos2 = form.getPicture2_pc();
        String var2 = form.getPicture2_via();
        float neg3 = form.getPicture3_nc();
        float pos3 = form.getPicture3_pc();
        String var3 = form.getPicture3_via();
        float neg4 = form.getPicture4_nc();
        float pos4 = form.getPicture4_pc();
        String var4 = form.getPicture4_via();
        String ml_result = form.getDiagnosis();
        Boolean consult = form.getConsult();

        try {
            if (!datey.isEmpty()){
                if (!datey.equals("Not Sure")){
                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(datey);
                }
            }
            if (!time.equals("Not Sure")){
                date3 = new SimpleDateFormat("dd/MM/yyyy").parse(time);
            }
            date1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(date);
            Log.v("TAG1", ""+date1);
            Log.v("TAG2", ""+date2);
            Log.v("TAG3", ""+date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picture picture1 = new Picture(neg, pos, var, sImage, "");
        Picture picture2 = new Picture(neg2, pos2, var2, sImage2, "");
        Picture picture3 = new Picture(neg3, pos3, var3, sImage3, "");
        Picture picture4 = new Picture(neg4, pos4, var4, sImage4, "");

        String other = form.getNurses();
        String version = "2.0.0";
        float threshold = 5.0F;


        Capture capture = new Capture(instanceID, date1, username, fac_id, studyID, initial,
                district, county, zone, age, text, ss, symptom, text2, sss, past, treat, date2,
                value3, valuex, year, valuev, date3, child, abort, s4, choice, location, via, notes,
                ml_result, consult, other, threshold, version, picture1, picture2, picture3, picture4);

        Capture2 capture2 = new Capture2(instanceID, date1, username, fac_id, studyID, initial,
                district, county, zone, age, text, ss, symptom, text2,
                value3, valuex, year, valuev, date3, child, abort, s4, choice, location, via, notes,
                ml_result, consult, other, threshold, version, picture1, picture2, picture3, picture4);

        Log.v("TAG", "data3");

        sendOverNetwork(token, capture, capture2, value);
    }

    private void sendOverNetwork(String token, Capture capture, Capture2 capture2, Long value) {

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

                    ApiError apiError = ErrorUtils.parseError(response);
                    String error = apiError.getMessage();
                    if(error.equals("The given data was invalid.")){
                        formDAO.DeleteForm(value);
                    }
                    Log.d("LocationUploadWorker2", "Testing: " + apiError.getMessage());

                    return;
                }

                Log.d("LocationUploadWorker3", "Testing: " + response.body());
                formDAO.DeleteForm(value);


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("LocationUploadWorker4", "Testing: " + t.getMessage());
            }
        });
    }

}
