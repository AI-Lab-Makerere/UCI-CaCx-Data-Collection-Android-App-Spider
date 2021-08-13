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

import com.ug.cancerapp.Models.Case;
import com.ug.cancerapp.Models.Gynecologist;
import com.ug.cancerapp.Models.Model;
import com.ug.cancerapp.R;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseHolder>{

    List<Gynecologist> gynecologist;
    private OnItemClickListener mListener;

    public CaseAdapter(List<Gynecologist> gynecologist) {
        this.gynecologist = gynecologist;
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
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply, parent, false);
        CaseHolder caseHolder = new CaseHolder(view, mListener);
        return caseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaseHolder holder, int position) {
        holder.study.setText("StudyID: " + gynecologist.get(position).getStudyId());
        holder.age.setText("Age: " + gynecologist.get(position).getAge());
        holder.via.setText("Expert's Via Results: " + gynecologist.get(position).getViaResults());
    }

    @Override
    public int getItemCount() {
        return gynecologist.size();
    }

    public static class CaseHolder extends RecyclerView.ViewHolder {

        TextView study, age, via;
        Button image, data, feedback;

        public CaseHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            study = itemView.findViewById(R.id.studyId);
            age = itemView.findViewById(R.id.age);
            via = itemView.findViewById(R.id.via);
            image = itemView.findViewById(R.id.images);
            data = itemView.findViewById(R.id.data);
            feedback = itemView.findViewById(R.id.feedback);

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
