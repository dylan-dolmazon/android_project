package com.example.tp2.final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class EquipementAdapter extends RecyclerView.Adapter<EquipementAdapter.ViewHolder> {

    ArrayList<Data> datas;
    Context context;

    public EquipementAdapter(Context context, ArrayList<Data> datas) {

        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public EquipementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.adapter_equipement_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipementAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Data data = datas.get(position);
        holder.tvName.setText(data.getNom());
        holder.tvDepartement.setText(data.getDepartement());
        holder.tvCommune.setText(data.getCommune());
        holder.tvNatureSol.setText(data.getNatureSol());
        holder.tvNbPlaceTribune.setText("Il y a "+String.valueOf(data.getNbPlaceTribune()) + " place(s) dans la tribune");
        if(data.getNatureLibelle().equals("Découvert"))
            holder.image.setImageResource(R.drawable.stade);
        else {
            holder.image.setImageResource(R.drawable.gymnase);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.adapter.context, datas.get(position).toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent map = new Intent(MainActivity.adapter.context, EquipementMaps.class);
                ArrayList<Object> marker = new ArrayList<>();
                marker.add(datas.get(position).getLat());
                marker.add(datas.get(position).getLng());
                marker.add(datas.get(position).getNom());
                map.putExtra("marker", marker);
                context.startActivity(map);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCommune;
        TextView tvDepartement;
        TextView tvNatureSol;
        TextView tvNbPlaceTribune ;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.image = (ImageView) itemView.findViewById(R.id.Image);
            this.tvName = (TextView) itemView.findViewById(R.id.Name);
            this.tvCommune = (TextView) itemView.findViewById(R.id.Commune);
            this.tvDepartement = (TextView) itemView.findViewById(R.id.Departement);
            this.tvNatureSol = (TextView) itemView.findViewById(R.id.Nature_Sol);
            this.tvNbPlaceTribune = (TextView) itemView.findViewById(R.id.NbPlaceTribune);
        }
    }
}
