package com.example.donelistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donelistapp.model.Result;

import java.util.ArrayList;
import java.util.List;

public class activityAdapter extends RecyclerView.Adapter<activityAdapter.CaViewHolder> {

    Context context;
    List<Result> results;

    public activityAdapter(Context con, List<Result> results){
        context = con;
        this.results = results;
    }

    @NonNull
    @Override
    public CaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_row, parent, false);

        return new CaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaViewHolder holder, int position) {
        Result result = results.get(position);
        holder.namaAktivitas.setText(result.getActivity());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class CaViewHolder extends RecyclerView.ViewHolder {
        TextView namaAktivitas;
        public CaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaAktivitas = itemView.findViewById(R.id.tv_namaAktivitas);
        }
    }

}
