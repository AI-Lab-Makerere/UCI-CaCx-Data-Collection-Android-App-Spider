package com.ug.cancerapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FormViewModel extends AndroidViewModel {

    private FormRepository repository;
//    private LiveData<List<Form>> allForms;

    public FormViewModel(@NonNull Application application) {
        super(application);
        repository = new FormRepository(application);
//        allForms = repository.getAllForms();
    }

    public void insert(Form form)
    {
        repository.insert(form);
    }


//    public LiveData<List<Form>> getAllForms() {
//        return allForms;
//    }
}
