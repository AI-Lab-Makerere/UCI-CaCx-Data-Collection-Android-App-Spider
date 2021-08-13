/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.cancerapp.Database.Client;
import com.ug.cancerapp.R;

import java.util.List;

public class NurseAdapter extends RecyclerView.Adapter<NurseAdapter.NurseHolder> {

    List<Client> clientList, selected;
    Context context;

    public NurseAdapter(List<Client> clientList, List<Client> selected, Context context) {
        this.clientList = clientList;
        this.selected = selected;
        this.context = context;
    }

    @NonNull
    @Override
    public NurseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nurse, parent, false);
        NurseHolder nurseHolder = new NurseHolder(view);
        return nurseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NurseHolder holder, int position) {
        Client client = clientList.get(position);
        holder.textView.setText(client.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected.contains(client)){
                    selected.remove(client);
                    unhighlight(holder);
                }else {
                    selected.add(client);
                    highlight(holder);
                }
            }
        });

        if (selected.contains(client)){
            highlight(holder);
        }else {
            unhighlight(holder);
        }
    }

    private void highlight(NurseHolder holder){
        holder.imageView.setImageResource(R.drawable.ic_task);
    }

    private void unhighlight(NurseHolder holder){
        holder.imageView.setImageResource(R.drawable.ic_rec);
    }

    public List<Client> getSelected() {
        return selected;
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class NurseHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public NurseHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);
        }
    }
}
