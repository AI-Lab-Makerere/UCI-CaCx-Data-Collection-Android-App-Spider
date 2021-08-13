/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.R;

import java.util.List;

public class GynecologistAdapter extends RecyclerView.Adapter<GynecologistAdapter.GynecologistHolder> {

    List<Gynecologist> gynecologists;
    private OnItemClickListener mListener;

    public GynecologistAdapter(List<Gynecologist> gynecologists) {
        this.gynecologists = gynecologists;
    }

    public interface OnItemClickListener {
        void onImageClick(int position);
        void onDateClick(int position);
        void onFeedClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public GynecologistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_file, parent, false);
        GynecologistHolder gynecologistHolder = new GynecologistHolder(view, mListener);
        return gynecologistHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GynecologistHolder holder, int position) {

        holder.study.setText("StudyID: " + gynecologists.get(position).getStudyId());
        holder.age.setText("Age: " + gynecologists.get(position).getAge());
        holder.dat.setText("Form filled in: " + gynecologists.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return gynecologists.size();
    }

    public static class GynecologistHolder extends RecyclerView.ViewHolder {

        TextView study, age, dat;
        Button image, data, feedback;

        public GynecologistHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            study = itemView.findViewById(R.id.studyId);
            age = itemView.findViewById(R.id.age);
            dat = itemView.findViewById(R.id.date);
//            image = itemView.findViewById(R.id.images);
            data = itemView.findViewById(R.id.data);
            feedback = itemView.findViewById(R.id.feedback);

//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onImageClick(position);
//                        }
//                    }
//                }
//            });

            data.setOnClickListener(new View.OnClickListener() {
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

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onFeedClick(position);
                        }
                    }
                }
            });

        }
    }
}
