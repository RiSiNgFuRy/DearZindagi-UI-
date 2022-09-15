package com.example.dz_v30.Adapters;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dz_v30.Activities.ApplicationClass;
import com.example.dz_v30.Models.meds_list_model;
import com.example.dz_v30.R;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class output_med_list_adapter extends RecyclerView.Adapter<output_med_list_adapter.ViewHolder> {

    ArrayList<meds_list_model> meds;
    Context context;

    public output_med_list_adapter(ArrayList<meds_list_model> meds, Context context) {
        this.meds = meds;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView list_name,list_qty;
        RadioGroup week_day_list;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_qty = itemView.findViewById(R.id.list_qty);
//            week_day_list = itemView.findViewById(R.id.week_day_list);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.med_name_list_layout,parent,false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(meds.get(position).getId());
        holder.list_name.setText(meds.get(position).getMedName());
        holder.list_qty.setText("x"+meds.get(position).getNumOfTimes()+" "+ ApplicationClass.stateWithUnit.get(meds.get(position).getState().toString()));
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }
}
