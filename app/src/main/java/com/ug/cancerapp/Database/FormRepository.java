package com.ug.cancerapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FormRepository {

    private FormDAO formDAO;
    private LiveData<List<Form>> allForms;
    long rowId;

    public FormRepository(Application application){
        FormDatabase database = FormDatabase.getInstance(application);
        formDAO = database.formDAO();
        allForms = formDAO.getAllForms();
    }

    public long insert(Form form){
        new InsertFormAsyncTask(formDAO).execute(form);
        rowId = form.getKey();
        return rowId;
    }

    private static class InsertFormAsyncTask extends AsyncTask<Form, Void, Void>{

        private FormDAO formDAO;

        private InsertFormAsyncTask(FormDAO formDAO){
            this.formDAO = formDAO;
        }

        @Override
        protected Void doInBackground(Form... forms) {
            formDAO.insertForm(forms[0]);
            return null;
        }
    }
}
