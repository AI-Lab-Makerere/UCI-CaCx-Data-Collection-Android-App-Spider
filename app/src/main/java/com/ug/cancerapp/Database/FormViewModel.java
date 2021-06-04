package com.ug.cancerapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FormViewModel extends AndroidViewModel {

    private FormRepository repository;
    private LiveData<List<Form>> allForms;
    long rowId;

    public FormViewModel(@NonNull Application application) {
        super(application);
        repository = new FormRepository(application);
//        allForms = repository.getAllNotes();
    }

    public long insert(Form form)
    {
        repository.insert(form);
        return rowId;
    }
}
