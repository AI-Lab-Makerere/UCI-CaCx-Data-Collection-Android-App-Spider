/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Fragments;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.Activities.ModelActivity;
import com.ug.cancerapp.Activities.RecordsActivity;
import com.ug.cancerapp.Activities.SavingActivity;
import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Adapter.NurseAdapter;
import com.ug.cancerapp.Database.Client;
import com.ug.cancerapp.Database.ClientDAO;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Activities.DashBoardActivity;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.ug.cancerapp.Activities.SplashActivity.THRESHOLD;
import static com.ug.cancerapp.Fragments.Camera1Fragment.FLON;
import static com.ug.cancerapp.Fragments.Camera1Fragment.FLOP;
import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera1Fragment.VR;
import static com.ug.cancerapp.Fragments.Camera2Fragment.FLON2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.FLOP2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.Fragments.Camera2Fragment.VR2;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLON3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.FLOP3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.Fragments.Camera3Fragment.VR3;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLON4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.FLOP4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.Fragments.Camera4Fragment.VR4;
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


public class Other2Fragment extends Fragment {

    View view;
    Button next, back;
    String nurse1, nurse2, nurse3, total;
    ArrayList<Client> clientList, selected;
    NurseAdapter nurseAdapter;
    RecyclerView recyclerView;

    FormRepository formRepository;
    Client client;
    ClientDAO clientDAO;

    public static final String SHARED_PREFS = "sharedPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String NURSE1 = "nurse1";
    public static final String NURSE2 = "nurse2";
    public static final String NURSE3 = "nurse3";
    public static final String SUM = "nurses";
    String index;
    String mail = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other2, container, false);
        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new loadData().execute();

        formRepository = new FormRepository((Application) getActivity().getApplicationContext());
        clientDAO = FormDatabase.getInstance(getActivity()).clientDAO();


        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//
//        loadData();
//        updateViews();
//        Toast.makeText(getActivity(), total, Toast.LENGTH_SHORT).show();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmailAddress();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ViaFragment());
                fr.commit();
            }
        });

        return view;
    }

    class loadData extends AsyncTask<Void, Void, Void>{

        List<Client> clientList1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            clientList1 = formRepository.getAllClients();
            clientList = new ArrayList<>();
            selected = new ArrayList<>();

            clientList.addAll(clientList1);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            nurseAdapter = new NurseAdapter(clientList, selected, getActivity());
            recyclerView.setAdapter(nurseAdapter);
        }
    }

    public void getEmailAddress(){
        mail = "";
        int si = nurseAdapter.getSelected().size();
        if (si > 0){
            for (int i = si -1; i >= 0; i--){
                index = nurseAdapter.getSelected().get(i).getEmail();
                mail += index + ", ";
            }
            mail = mail.replaceAll(", $", "");
            editor.putString(SUM, mail);
            editor.apply();
            startActivity(new Intent(getActivity(), SavingActivity.class));
        }else {
            Toast.makeText(getActivity(), "Select at least one other nurse", Toast.LENGTH_SHORT).show();
        }

    }

//    private void saveData() {
//
//        getEmailAddress();
//
//    }
//
//    public void loadData(){
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//
//        nurse1 = sharedPreferences.getString(NURSE1, "");
//        nurse2 = sharedPreferences.getString(NURSE2, "");
//        nurse3 = sharedPreferences.getString(NURSE3, "");
//        nurse3 = sharedPreferences.getString(NURSE3, "");
////        total = sharedPreferences.getString(SUM, "");
//
//    }
//
//    public void updateViews(){
//
//    }
}