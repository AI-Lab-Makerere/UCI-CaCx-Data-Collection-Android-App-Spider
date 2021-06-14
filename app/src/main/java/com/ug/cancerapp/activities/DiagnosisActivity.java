package com.ug.cancerapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.ug.cancerapp.Adapter.FormAdapter;
import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormRepository;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisActivity extends AppCompatActivity {

    ArrayList<Form> formList;
    FormAdapter formAdapter;
    RecyclerView recyclerView;
    Dialog dialog;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri uri;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Records");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new LoadDataTask().execute();

    }

    class LoadDataTask extends AsyncTask<Void, Void, Void>{

        FormRepository formRepository;
        List<Form> formList1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            formRepository = new FormRepository((Application) getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            formList1 = formRepository.getAllForms();
            formList = new ArrayList<>();

            for (int i = 0; i < formList1.size(); i++){
                formList.add(formList1.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            formAdapter = new FormAdapter(formList, DiagnosisActivity.this);
            recyclerView.setAdapter(formAdapter);

            formAdapter.setOnItemClickListener(new FormAdapter.OnItemClickListener() {
                @Override
                public void onLoadClick(int position) {

                }

                @Override
                public void onViewClick(int position) {
                    String image1 = formList.get(position).getImage1();
                    String image2 = formList.get(position).getImage2();
                    String image3 = formList.get(position).getImage3();
                    String image4 = formList.get(position).getImage4();

                    dialog = new Dialog(DiagnosisActivity.this);
                    dialog.setContentView(R.layout.viewing_images);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    imageView1 = dialog.findViewById(R.id.image1);
                    imageView2 = dialog.findViewById(R.id.image2);
                    imageView3 = dialog.findViewById(R.id.image3);
                    imageView4 = dialog.findViewById(R.id.image4);

                    byte[] bytes1 = Base64.decode(image1, Base64.DEFAULT);
                    byte[] bytes2 = Base64.decode(image2, Base64.DEFAULT);
                    byte[] bytes3 = Base64.decode(image3, Base64.DEFAULT);
                    byte[] bytes4 = Base64.decode(image4, Base64.DEFAULT);

                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                    bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);

                    imageView1.setImageBitmap(bitmap1);
                    imageView2.setImageBitmap(bitmap2);
                    imageView3.setImageBitmap(bitmap3);
                    imageView4.setImageBitmap(bitmap4);

                    dialog.show();
                }

                @Override
                public void onConsultClick(int position) {

                }
            });
        }
    }
}