/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class FormRepository {

    private FormDAO formDAO;
    private ClientDAO clientDAO;
//    private LiveData<List<Form>> allForms;

    public FormRepository(Application application){
        FormDatabase database = FormDatabase.getInstance(application);
        formDAO = database.formDAO();
        clientDAO = database.clientDAO();
//        allForms = formDAO.getAllForms();
    }

    public void insert(Form form){
        new InsertFormAsyncTask(formDAO).execute(form);
    }

    public void insertNurse(Client client){
        new InsertClientAsyncTask(clientDAO).execute(client);
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

    private static class InsertClientAsyncTask extends AsyncTask<Client, Void, Void>{

        private ClientDAO clientDAO;

        private InsertClientAsyncTask(ClientDAO clientDAO){
            this.clientDAO = clientDAO;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDAO.insertClient(clients[0]);
            return null;
        }
    }


    public List<Form> getAllForms(){
        List<Form> formList = formDAO.getAllForms();
        return formList;
    }

    public List<Form> getAllFormsUploaded(){
        List<Form> formList = formDAO.getAllFormsUploaded();
        return formList;
    }

    public Form getOnlyOne(Long id){

        Form form = formDAO.getOne(id);
        return form;
    }


}
