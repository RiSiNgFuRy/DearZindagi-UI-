package com.example.dz_v30.Fragments;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.example.dz_v30.Activities.ApplicationClass;
import com.example.dz_v30.R;
import com.example.dz_v30.Resources.AlertReceiver;
import com.example.dz_v30.Resources.DateHelper;
import com.example.dz_v30.Resources.Listdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class InputData extends BottomSheetDialogFragment implements View.OnClickListener {

    public InputData() {
        // Required empty public constructor
    }

    View view;
    ArrayList<String> selectedDaysOfWeek = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    RadioGroup radioGroup,daysToogleGrp;
    TextView meridian_stat,state_text,sub_val,med_val,add_val,f_save_btn;
    LinearLayout quantity_area,date_container;
    EditText time_hr,time_min,input_med_name,input_note,date_from,date_to;
    String format = "dd/MM/yyyy";

    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_data, container, false);

        state_text = view.findViewById(R.id.state_text);
        quantity_area = view.findViewById(R.id.quantity_area);
        f_save_btn = view.findViewById(R.id.input_save_btn);
        date_from = view.findViewById(R.id.fromDate);
        date_to = view.findViewById(R.id.toDate);
        radioGroup = view.findViewById(R.id.state_check);
        daysToogleGrp = view.findViewById(R.id.days_toggle_grp);
        time_hr = view.findViewById(R.id.time_hr);
        time_min = view.findViewById(R.id.time_min);
        meridian_stat = view.findViewById(R.id.meridian_stat);
        input_med_name = view.findViewById(R.id.input_med_name);
        input_note = view.findViewById(R.id.input_note);
        sub_val = view.findViewById(R.id.sub_val);
        med_val = view.findViewById(R.id.med_val);
        add_val = view.findViewById(R.id.add_val);
        date_container = view.findViewById(R.id.date_container);

        Set<String> keySet = ApplicationClass.stateWithUnit.keySet();
        for(String s : keySet)
            states.add(s);

        date_from.setOnClickListener(this);
        date_to.setOnClickListener(this);
        sub_val.setOnClickListener(this);
        add_val.setOnClickListener(this);

        date_container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && date_from.getTextSize()!=0 && date_to.getTextSize()!=0){
                    DateHelper helper = new DateHelper(format);
                    try {
                        helper.valid(date_from.getText().toString(),date_to.getText().toString());
                    } catch (ParseException e) {
                        date_to.setError("Invalid date");
                        date_from.setError("Invalid date");
                        Toast.makeText(getContext(), "Invalid date!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        time_hr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    if(Integer.parseInt(time_hr.getText().toString())>=0 && Integer.parseInt(time_hr.getText().toString())<12)
                        meridian_stat.setText("am");
                    else
                        meridian_stat.setText("pm");
            }
        });
        quantity_area.setVisibility(View.GONE);
        f_save_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                for(int i=0;i<7;i++){
                    if(daysToogleGrp.getChildAt(i).getBackground().getConstantState()==getResources().getDrawable(R.drawable.square_purple_bg).getConstantState())
                        selectedDaysOfWeek.add(String.valueOf(daysToogleGrp.getChildAt(i).getId()));
                }
                saveData(time_hr.getText().toString(),time_min.getText().toString(),meridian_stat.getText().toString(),input_med_name.getText().toString(),med_val.getText().toString(),selectedDaysOfWeek,states.get(radioGroup.getCheckedRadioButtonId()),input_note.getText().toString(),date_from.getText().toString(),date_to.getText().toString());
            }
        });
        setCurrentTime(time_hr,time_min,meridian_stat);
        getStateOfMed(radioGroup,states);
        getDaysOfWeek(daysToogleGrp);

        return view;
    }

    public void saveData(String hours,String minutes, String meridian,String med_name, String med_val,ArrayList<String> days_of_week, String state, String note, String date_frm, String date_to){
        try{
            String time = hours+":"+minutes+" "+meridian;
            Listdb db = new Listdb(this.getActivity());
            db.open();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                db.createEntryInMeds(time,med_name,med_val,date_frm,date_to,state,days_of_week,note);
                ApplicationClass.fetchMeds(getContext());
                ApplicationClass.fetchTime(getContext());
//                OutputData outputData = (OutputData) this.getFragmentManager().findFragmentById(R.id.list);
//                outputData.getNotifiedDataChanged();
//                Med_Info.adapter.notifyDataSetChanged();
            }
            db.close();
            setTime(Integer.parseInt(hours),Integer.parseInt(minutes),selectedDaysOfWeek,date_frm,date_to);
            Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();

        }catch (SQLException e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setCurrentTime(EditText time_hr,EditText time_min, TextView meridian_stat){
        Calendar c = Calendar.getInstance();
        time_hr.setText(String.format("%02d",c.get(Calendar.HOUR_OF_DAY)));
        time_min.setText(String.format("%02d",c.get(Calendar.MINUTE)));
        if(c.get(Calendar.AM_PM)==1)
            meridian_stat.setText("pm");
        else
            meridian_stat.setText("am");
    }

    public void setTime(Integer hours, Integer minutes,ArrayList<String> dayOfWeek, String dateFrom, String dateTo){
        DateHelper helper = new DateHelper(format);
        try {
            ArrayList<Date> dates = helper.datesInRange(dateFrom,dateTo,dayOfWeek,hours,minutes);
            for(Date d : dates)
                startAlarm(d);
        } catch (ParseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void startAlarm (Date d) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), d.getHours()*d.getHours(), intent, PendingIntent.FLAG_IMMUTABLE);
        Log.d("Current Time",String.valueOf(d.getTime()-System.currentTimeMillis()));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,d.getTime(),pendingIntent);
    }

    public void getStateOfMed(RadioGroup radioGroup,ArrayList<String> states){
        radioGroup.setPadding(10,2,10,2);
        radioGroup.setGravity(Gravity.CENTER);
        params.setMargins(10,0,10,0);
        for(int i=0; i<states.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(states.get(i));
            radioButton.setId(i);
            radioButton.setTextSize(16);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setLayoutParams(params);
            radioButton.setButtonDrawable(null);
            radioButton.setPadding(20, 10, 20, 10);
            radioButton.setBackgroundResource(R.drawable.square_border);
            radioButton.setTextColor(Color.BLACK);
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                resetBackground(i, radioGroup, states);
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                rb.setBackgroundResource(R.drawable.purple_border);
                rb.setTextColor(Color.WHITE);
                showQuantityArea(rb.getText().toString());
            }
        });
    }

    public void resetBackground(int checkedId, RadioGroup group,ArrayList<String> states) {
        for (int i = 0; i < states.size(); i++) {
            if (i != checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(i);
                radioButton.setBackgroundResource(R.drawable.square_border);
                radioButton.setTextColor(Color.BLACK);
            }
        }
    }

    public void showQuantityArea(String state){
        if(state.toLowerCase().equals("tablet"))
            state_text.setText("Number of tablets at a time : ");
        else if(state.toLowerCase().equals("syrup"))
            state_text.setText("Volume of syrup in ml : ");
        quantity_area.setVisibility(View.VISIBLE);
    }
    public void getDaysOfWeek(RadioGroup daysToogleGrp){
        daysToogleGrp.setGravity(Gravity.CENTER);
        params.setMargins(10,0,10,0);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        for(int i=1;i<=7;i++){
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(sdf.format(calendar.getTime()));
            radioButton.setTextSize(16);
            radioButton.setId(i);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setLayoutParams(params);
            radioButton.setButtonDrawable(null);
            radioButton.setBackgroundResource(R.drawable.circular_bg);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.circular_bg).getConstantState()) {
                        radioButton.setTextColor(Color.WHITE);
                        radioButton.setBackgroundResource(R.drawable.square_purple_bg);
                    }
                    else{
                        radioButton.setTextColor(Color.BLACK);
                        radioButton.setBackgroundResource(R.drawable.circular_bg);
                    }
                }
            });
            daysToogleGrp.addView(radioButton);
        }
    }
    public void dateDialog(final EditText txt_name){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sdf = new SimpleDateFormat(format, Locale.US);
                }
                txt_name.setText(sdf.format(calendar.getTime()));
            }};
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dpDialog=new DatePickerDialog(getContext(), listener, year, month, day);
        dpDialog.show();
    }

    @Override
    public void onClick(View v) {
        int amt = Integer.parseInt(med_val.getText().toString());

        switch (v.getId()){
            case R.id.sub_val:
                if(amt>1)
                    med_val.setText(String.valueOf(amt-1));
                break;
            case R.id.add_val:
                med_val.setText(String.valueOf(amt+1));
                break;
            case R.id.fromDate:
                dateDialog(date_from);
                break;
            case R.id.toDate:
                dateDialog(date_to);
                break;
        }
    }
}
