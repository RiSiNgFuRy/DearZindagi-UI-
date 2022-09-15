package com.example.dz_v30.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.dz_v30.Resources.DateHelper;

public class med_info_adapter extends RecyclerView.Adapter<med_info_adapter.ViewHolder> {
    private ArrayList<meds_list_model> list;
    Context context;
    public med_info_adapter(Context context, ArrayList<meds_list_model> list)
    {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView med_name,description,quantity,date_range;
        RadioGroup week_day_grp;
        ImageView more_opt;
        LinearLayout description_box;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            med_name = itemView.findViewById(R.id._name);
            week_day_grp = itemView.findViewById(R.id.week_days_grp);
            date_range = itemView.findViewById(R.id.date_range);
            quantity = itemView.findViewById(R.id.med_qty);
            description_box = itemView.findViewById(R.id.description_box);
            description = itemView.findViewById(R.id.description);
            more_opt = itemView.findViewById(R.id.more_opt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.med_detail_layout,parent, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        holder.itemView.setTag(list.get(position).getId());
        holder.med_name.setText(list.get(position).getMedName());
        holder.date_range.setText(dateText(list.get(position)));
        holder.quantity.setText(list.get(position).getNumOfTimes()+" "+ ApplicationClass.stateWithUnit.get(list.get(position).getState()));
        if(list.get(position).getDescription().isEmpty())
            holder.description_box.setVisibility(View.GONE);
        else
            holder.description.setText(list.get(position).getDescription());

        if(holder.week_day_grp.getChildCount()!=list.get(position).getDaysOfWeek().size()) {
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 5, 0);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            for (String days : list.get(position).getDaysOfWeek()) {
                c.set(Calendar.DAY_OF_WEEK, Integer.parseInt(days));
                RadioButton radioButton = new RadioButton(context);
                radioButton.setPadding(20,0,20,0);
                radioButton.setTextSize(10);
                radioButton.setTextColor(Color.WHITE);
                radioButton.setButtonDrawable(null);
                radioButton.setLayoutParams(params);
                radioButton.setText(sdf.format(c.getTime()));
                radioButton.setBackgroundResource(R.drawable.circular_purple_bg);
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setTypeface(null, Typeface.BOLD);
                holder.week_day_grp.addView(radioButton);
            }
        }
        holder.week_day_grp.setOrientation(LinearLayout.HORIZONTAL);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String dateText(meds_list_model obj){
        String format = "dd/MM/yyyy";
        DateHelper dh = new DateHelper(format);
        String t = obj.getDate_from() +" to "+ obj.getDate_to();
        return t;
    }
}
