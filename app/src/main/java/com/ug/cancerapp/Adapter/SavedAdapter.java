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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.R;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedHolder>{

    List<Gynecologist> gynecologists;
    private OnItemClickListener mListener;

    public SavedAdapter(List<Gynecologist> gynecologists) {
        this.gynecologists = gynecologists;
    }

    public interface OnItemClickListener {
        void onImageClick(int position);
        void onDateClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public SavedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form, parent, false);
        SavedHolder savedHolder = new SavedHolder(view, mListener);
        return savedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedHolder holder, int position) {
        holder.studyID.setText("Name: " + gynecologists.get(position).getStudyId());
        holder.initials.setText("Telephone number: " + gynecologists.get(position).getInitials());
        holder.age.setText("Age: " + gynecologists.get(position).getAge());
        holder.via.setText("VIA Results: " + gynecologists.get(position).getViaResults());
        holder.dat.setText("Form Submitted " + gynecologists.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return gynecologists.size();
    }

    public static class SavedHolder extends RecyclerView.ViewHolder {

       TextView studyID, initials, age, via, dat;
       Button image, load;

       public SavedHolder(@NonNull View itemView, OnItemClickListener listener) {
           super(itemView);

           studyID = itemView.findViewById(R.id.study);
           initials = itemView.findViewById(R.id.initials);
           age = itemView.findViewById(R.id.age);
           dat = itemView.findViewById(R.id.date);
           via = itemView.findViewById(R.id.results);
           image = itemView.findViewById(R.id.images);
           load = itemView.findViewById(R.id.load);

           image.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener != null){
                       int position = getAdapterPosition();
                       if (position != RecyclerView.NO_POSITION){
                           listener.onImageClick(position);
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
                           listener.onDateClick(position);
                       }
                   }
               }
           });

       }
   }
}
