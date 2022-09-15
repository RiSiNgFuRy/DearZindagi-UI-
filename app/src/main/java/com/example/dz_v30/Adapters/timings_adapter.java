package com.example.dz_v30.Adapters;

import android.app.Activity;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dz_v30.Models.meds_list_model;
import com.example.dz_v30.Models.time_model;
import com.example.dz_v30.R;

import java.util.ArrayList;

import com.example.dz_v30.Resources.Listdb;

public class timings_adapter extends RecyclerView.Adapter<timings_adapter.ViewHolder> {
    private ArrayList<time_model> time = new ArrayList<>();
    ArrayList<meds_list_model> meds = new ArrayList<>();
    Activity activity;
    output_med_list_adapter adapter;

    public timings_adapter(Activity activity, ArrayList<time_model> list)
    {
        time=list;
        this.activity = activity;
    }
       
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView note_area,add_,delete_,_note,shows_time,disease_name,add_note_btn;
        ConstraintLayout add_note_area;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            shows_time = itemView.findViewById(R.id.shows_time);
            disease_name = itemView.findViewById(R.id.disease_name);
            add_note_btn = itemView.findViewById(R.id.add_note_btn);
            add_note_area = itemView.findViewById(R.id.add_note_area);
            note_area = itemView.findViewById(R.id.note_area);
            recyclerView = itemView.findViewById(R.id.med_name_list);
            add_ = itemView.findViewById(R.id.add_);
            delete_ = itemView.findViewById(R.id.delete_);
            _note = itemView.findViewById(R.id._note);

            add_note_area.setVisibility(View.GONE);
            note_area.setVisibility(View.GONE);

            add_note_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(add_note_btn.getText()!="CANCEL") {
                        add_note_btn.setText("CANCEL");
                        note_area.setVisibility(View.GONE);
                        add_note_area.setVisibility(View.VISIBLE);
                        _note.setText(note_area.getText());
                    }
                    else {
                        if(note_area.getText().length()==0)
                            add_note_btn.setText("+ ADD NOTE");
                        else
                            add_note_btn.setText("EDIT NOTE");
                        add_note_area.setVisibility(View.GONE);
                    }
                }
            });
            add_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Listdb db = new Listdb(activity.getApplicationContext());
                        db.open();
                        db.insertKeyTimeNote(shows_time.getText().toString(), _note.getText().toString());
                        db.close();
                        note_area.setText(_note.getText());
                    }catch (SQLException e){
                        Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(add_note_btn.getText()=="CANCEL")
                        if(note_area.getText().length()==0)
                            add_note_btn.setText("+ ADD NOTE");
                        else
                            add_note_btn.setText("EDIT NOTE");
                    note_area.setVisibility(View.VISIBLE);
                    add_note_area.setVisibility(View.GONE);
                }
            });
            delete_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _note.setText("");
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_layout,parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        holder.itemView.setTag(time.get(position));
        holder.shows_time.setText(time.get(position).getTime());
        holder.note_area.setText(time.get(position).getNote());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(),RecyclerView.HORIZONTAL,false));

        if(time.get(position).getNote().length()!=0) {
            holder.note_area.setVisibility(View.VISIBLE);
            holder.add_note_btn.setText("EDIT NOTE");
        }

        Listdb db = new Listdb(this.activity);
        db.open();
        meds.clear();
        for(String id: time.get(position).getMed())
            meds.add(new meds_list_model(id,time.get(position).getTime(),db.getKeyName(id),db.getKeyNumoftimes(id), db.getKeyState(id),db.getKeyDaysOfWeek(id), db.getKeyDescription(id), db.getKeyFromTimestamp(id),db.getKeyToTimestamp(id)));
        db.close();

        adapter = new output_med_list_adapter(meds,activity.getApplicationContext());
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return time.size();
    }


}


