package com.example.cardiacrecorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder>  {
    private ArrayList<Measurement> measurementList;
    private RecyclerViewClickListener listener;

    public recyclerAdapter(ArrayList<Measurement> measurementList, RecyclerViewClickListener listener){
        this.measurementList = measurementList;
        //Collections.reverse(this.measurementList);
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sysOut, diasOut, heartOut, dateOut, timeOut;
        LinearLayout bg;

        public MyViewHolder(final View view){
            super(view);

            bg = view.findViewById(R.id.bg_item);
            sysOut = view.findViewById(R.id.sysOutput);
            diasOut = view.findViewById(R.id.diasOutput);
            heartOut = view.findViewById(R.id.heartOutput);
            dateOut = view.findViewById(R.id.dateOutput);
            timeOut = view.findViewById(R.id.timeOutput);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        holder.sysOut.setText(measurementList.get(position).getSystolicPressure());
        holder.diasOut.setText(measurementList.get(position).getDiastolicPressure());
        holder.heartOut.setText(measurementList.get(position).getHeartRate());
        holder.dateOut.setText(measurementList.get(position).getDate());
        holder.timeOut.setText(measurementList.get(position).getTime());

        int sys, dia;
        sys = Integer.parseInt(measurementList.get(position).getSystolicPressure());
        dia = Integer.parseInt(measurementList.get(position).getDiastolicPressure());
        if(sys < 90 || sys > 140 || dia < 60 || dia > 90)
            holder.bg.setBackgroundResource(R.drawable.rounded_corner_red);
    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
