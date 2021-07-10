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
import com.ug.cancerapp.R;

import java.util.List;

public class FormAdapter2 extends RecyclerView.Adapter<FormAdapter2.FormHolder> {

    List<Form> formList;
    Context context;
    private OnItemClickListener mListener;

    public FormAdapter2(List<Form> formList, Context context) {
        this.formList = formList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onLoadClick(int position);
        void onViewClick(int position);
        void onConsultClick(int position);
        void onCheckedClick(int position);
        void onNotCheckedClick(int position);
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

    }

    @Override
    public int getItemCount() {
        return formList.size();
    }


    public static class FormHolder extends RecyclerView.ViewHolder{

        private TextView studyID, initials, age, via;
        private Button load, images, consult;
        private CheckBox checkBox;

        public FormHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            studyID = itemView.findViewById(R.id.study);
            initials = itemView.findViewById(R.id.initials);
            age = itemView.findViewById(R.id.age);
            via = itemView.findViewById(R.id.results);
            load = itemView.findViewById(R.id.load);
            images = itemView.findViewById(R.id.images);
//            consult = itemView.findViewById(R.id.consult);
//            checkBox = itemView.findViewById(R.id.ok);

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

//            consult.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onConsultClick(position);
//                        }
//                    }
//                }
//            });

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

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (checkBox.isChecked()){
//                        if (listener != null){
//                            int position = getAdapterPosition();
//                            if (position != RecyclerView.NO_POSITION){
//                                listener.onCheckedClick(position);
//                            }
//                        }
//                    }
//                    else {
//                        if (listener != null){
//                            int position = getAdapterPosition();
//                            if (position != RecyclerView.NO_POSITION){
//                                listener.onNotCheckedClick(position);
//                            }
//                        }
//                    }
//                }
//            });
        }
    }
}
