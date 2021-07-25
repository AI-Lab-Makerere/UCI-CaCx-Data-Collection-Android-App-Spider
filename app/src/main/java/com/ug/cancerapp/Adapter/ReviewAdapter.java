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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{

    List<Gynecologist> gynecologists;
    private OnItemClickListener mListener;

    public ReviewAdapter(List<Gynecologist> gynecologists) {
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
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        ReviewHolder reviewHolder = new ReviewHolder(view, mListener);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.study.setText("StudyID: " + gynecologists.get(position).getStudyId());
        holder.age.setText("Nurse's VIA Results: " + gynecologists.get(position).getViaResults());
        holder.via.setText("Expert's VIA Results: " + gynecologists.get(position).getGynResults());
        holder.date.setText("Model Predictions: " + gynecologists.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return gynecologists.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView study, age, via, date;
        Button image, data, feedback;


        public ReviewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            study = itemView.findViewById(R.id.studyId);
            age = itemView.findViewById(R.id.age);
            via = itemView.findViewById(R.id.via);
            date = itemView.findViewById(R.id.date);
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
