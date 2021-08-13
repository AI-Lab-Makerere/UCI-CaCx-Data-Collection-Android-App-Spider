/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.R;

import java.util.ArrayList;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormHolder> {

    List<Form> formList, selected;
    Context context;
    private OnItemClickListener mListener;

    public FormAdapter(Context context, List<Form> formList, List<Form> selected) {
        this.formList = formList;
        this.selected = selected;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onLoadClick(int position);
        void onViewClick(int position);
        void onConsultClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public FormHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formxx, parent, false);
        FormHolder formHolder = new FormHolder(view, mListener);
        return formHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FormHolder holder, int position) {
        Form form = formList.get(position);
        holder.studyID.setText("StudyID: " + form.getStudyID());
        holder.initials.setText("Initials: " + form.getInitials());
        holder.age.setText("Age: " + String.valueOf(form.getAge()));
        holder.via.setText("VIA Results: " + form.getVia());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected.contains(form)){
                    selected.remove(form);
                    unhighlightView(holder);
                }else {
                    selected.add(form);
                    highlightView(holder);
                }
            }
        });

        if (selected.contains(form))
            highlightView(holder);
        else
            unhighlightView(holder);

    }

    private void highlightView(FormHolder holder) {
        holder.imageView.setVisibility(View.VISIBLE);
    }

    private void unhighlightView(FormHolder holder) {
        holder.imageView.setVisibility(View.INVISIBLE);
    }

    public void addAll(List<Form> forms) {
        clearAll(false);
        this.formList= forms;
        notifyDataSetChanged();
    }

    public void clearAll(boolean isNotify) {
        formList.clear();
        selected.clear();
        if (isNotify) notifyDataSetChanged();
    }
    public void clearSelected() {
        selected.clear();
        notifyDataSetChanged();
    }

    public void selectAll() {
        selected.clear();
        selected.addAll(formList);
        notifyDataSetChanged();
    }


    public List<Form> getSelected() {
        return selected;
    }

    @Override
    public int getItemCount() {
        return formList.size();
    }

    public void notifying() {
        notifyDataSetChanged();
    }


    public static class FormHolder extends RecyclerView.ViewHolder{

        private TextView studyID, initials, age, via;
        private Button load, images, consult;
        private ImageView imageView;

        public FormHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            studyID = itemView.findViewById(R.id.study);
            initials = itemView.findViewById(R.id.initials);
            age = itemView.findViewById(R.id.age);
            via = itemView.findViewById(R.id.results);
            load = itemView.findViewById(R.id.load);
            images = itemView.findViewById(R.id.images);
//            consult = itemView.findViewById(R.id.consult);
            imageView= itemView.findViewById(R.id.ok);

            images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onViewClick(position);
                        }
                    }
                }
            });

            load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onLoadClick(position);
                        }
                    }
                }
            });
        }
    }
}
