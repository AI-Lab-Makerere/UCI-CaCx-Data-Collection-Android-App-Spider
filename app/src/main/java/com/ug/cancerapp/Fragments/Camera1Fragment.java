package com.ug.cancerapp.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ug.cancerapp.R;
import com.ug.cancerapp.ml.Cancer;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.ug.cancerapp.Fragments.SixtyFragment.CHECKS;


public class Camera1Fragment extends Fragment {

    View view;
    Button back, next, camera, gallery;
    ImageView imageView;
    Uri uri;
    Bitmap bitmap;

    private static final int IMAGE_PICKER_CODE = 1000;
    private static final int IMAGE_CODE = 100;
    private static final int PERMISSIONS_CODE = 1001;

    String sImage, value, negative, positive, viar;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IMAGE = "image";
    public static final String FLON = "negative";
    public static final String FLOP = "positive";
    public static final String VR = "viaR";

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_camera1, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        camera = view.findViewById(R.id.camera);
        gallery = view.findViewById(R.id.gallery);
        imageView = view.findViewById(R.id.image);

//        progressDialog = new ProgressDialog(getActivity());

        loadData();
        updateViews();
        getData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sImage.isEmpty()){
                    Toast.makeText(getActivity(), "Take a picture or load one from gallery", Toast.LENGTH_SHORT).show();
                }else {
//                    progressDialog.setMessage("Saving Image...");
//                    progressDialog.show();
//                    byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
//                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    runTensorflowModel(bitmap2);
                    saveData();
//                    progressDialog.dismiss();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new Camera2Fragment());
                    fr.addToBackStack(null);
                    fr.commit();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.isEmpty()){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new YesOrNoFragment());
                    fr.commit();
                }else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new SixtyFragment());
                    fr.commit();
                }

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_CODE);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSIONS_CODE);

                    }else {
                        pickImageFromGallery();
                    }
                }
            }
        });

        return view;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CODE){
            if (resultCode == RESULT_OK){
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                imageView.setImageBitmap(bitmap);
            }else if (resultCode ==  RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }

        }
        if (requestCode == IMAGE_PICKER_CODE && resultCode == -1 && data != null){
            Uri uri =data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                imageView.setImageURI(uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private void saveData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(IMAGE, sImage);
//        editor.putString(VR, viar);
//        editor.putString(FLON, negative);
//        editor.putString(FLOP, positive);

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sImage = sharedPreferences.getString(IMAGE, "");
//        viar = sharedPreferences.getString(VR, "");
//        negative = sharedPreferences.getString(FLON, "");
//        positive = sharedPreferences.getString(FLOP, "");

    }

    public void updateViews(){
        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
    }

    private void getData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(CHECKS, "");

    }


    private void runTensorflowModel(Bitmap bitmap) {

        try {
            Cancer model = Cancer.newInstance(getActivity());

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

            float viaNegative = outputFeature0.getFloatArray()[0];
            float viaPositive = outputFeature0.getFloatArray()[1];

            if (viaNegative > viaPositive){
                viar = "Negative";
                negative = String.valueOf(viaNegative);
                positive = String.valueOf(viaPositive);
//                Toast.makeText(getActivity(), viar, Toast.LENGTH_SHORT).show();
            }else {
                viar = "Positive";
                negative = String.valueOf(viaNegative);
                positive = String.valueOf(viaPositive);
//                Toast.makeText(getActivity(), viar, Toast.LENGTH_SHORT).show();

            }


        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
}