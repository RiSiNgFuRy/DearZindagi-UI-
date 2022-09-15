package com.example.dz_v30.Activities;

import android.app.Application;
import android.content.Context;

import com.example.dz_v30.Models.meds_list_model;
import com.example.dz_v30.Models.time_model;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.dz_v30.Resources.Listdb;

public class ApplicationClass extends Application {
    public static ArrayList<time_model> times = new ArrayList<>();
    public static ArrayList<meds_list_model> meds = new ArrayList<>();
    public static Hashtable<String,String> stateWithUnit = new Hashtable<>();

    @Override
    public void onCreate() {
        super.onCreate();
        fetchTime(this);
        fetchMeds(this);

        stateWithUnit.put("Tablet","tab");
        stateWithUnit.put("Syrup","ml");
    }

    public static void fetchTime(Context context){
        times.clear();
        Listdb db=new Listdb(context);
        db.open();
        if(!db.isEmpty()) {
            ArrayList<String> time = db.getTimeArray();
            for(String t: time){
                times.add(new time_model(t,db.getKeyTimeNote(t),db.getKeyMedList(t)));
            }
        }
        db.close();
    }

    public static void fetchMeds(Context context){
        meds.clear();
        Listdb db = new Listdb(context);
        db.open();
        if(!db.isEmpty()) {
            ArrayList<String> ids = db.getMedIds();
            for(String id: ids)
                meds.add(new meds_list_model(id,db.getKeyTime(id),db.getKeyName(id),db.getKeyNumoftimes(id),db.getKeyState(id),db.getKeyDaysOfWeek(id),db.getKeyDescription(id),db.getKeyFromTimestamp(id),db.getKeyToTimestamp(id)));
        }
    }
}
