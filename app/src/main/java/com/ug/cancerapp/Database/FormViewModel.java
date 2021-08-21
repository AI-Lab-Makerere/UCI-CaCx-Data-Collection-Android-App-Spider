/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FormViewModel extends AndroidViewModel {

    private FormRepository repository;
    private LiveData<List<Form>> allData;

    public FormViewModel(@NonNull Application application) {
        super(application);
        repository = new FormRepository(application);
        allData = repository.getAllData();
    }

    public void insert(Form form)
    {
        repository.insert(form);
    }

    public void insertNurse(Client client){
        repository.insertNurse(client);
    }

    public LiveData<List<Form>> getAllData() {
        return allData;
    }

//    public void delete(Form form) {
//        repository.delete(form);
//    }


//    public LiveData<List<Form>> getAllForms() {
//        return allForms;
//    }
}
