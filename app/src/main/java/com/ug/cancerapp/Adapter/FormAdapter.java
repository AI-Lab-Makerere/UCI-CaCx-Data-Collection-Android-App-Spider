package com.ug.cancerapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.R;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormHolder> {

    List<Form> formList;
    Context context;
    private OnItemClickListener mListener;

    public FormAdapter(List<Form> formList, Context context) {
        this.formList = formList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form, parent, false);
        FormHolder formHolder = new FormHolder(view, mListener);
        return formHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FormHolder holder, int position) {
        holder.studyID.setText("StudyID: " + formList.get(position).getStudyID());
        holder.initials.setText("Initials: " + formList.get(position).getInitials());
        holder.age.setText("Age: " + String.valueOf(formList.get(position).getAge()));
        holder.via.setText("VIA Results: " + formList.get(position).getVia());

        if (formList.get(position).getVia().equals("Negative")){
            holder.via.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return formList.size();
    }


    public static class FormHolder extends RecyclerView.ViewHolder{

        private TextView studyID, initials, age, via;
        private Button load, images, consult;

        public FormHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            studyID = itemView.findViewById(R.id.study);
            initials = itemView.findViewById(R.id.initials);
            age = itemView.findViewById(R.id.age);
            via = itemView.findViewById(R.id.results);
            load = itemView.findViewById(R.id.load);
            images = itemView.findViewById(R.id.images);
            consult = itemView.findViewById(R.id.consult);

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
        }
    }
}
