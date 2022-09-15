package com.example.dz_v30.Resources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;

public class Listdb {
    public static final String KEY_ROWID="_id";
    public static final String KEY_TIME="_time";
    public static final String KEY_NAME="med_name";
    public static final String KEY_NUMOFTIMES="num_of_times";
    public static final String KEY_FROM_TIMESTAMP="from_date";
    public static final String KEY_TO_TIMESTAMP="to_date";
    public static final String KEY_STATE = "_state";
    public static final String KEY_DAYS_OF_WEEK="days_of_week";
    public static final String KEY_DESCRIPTION="_description";

    public static final String KEY_TIME_NOTE ="_time";
    public static final String KEY_NOTE = "_note";
    public static final String KEY_MED_LIST = "_med_list";

    private final String DATABASE_NAME="Listdb";
    private final String DATABASE_TABLE_MEDS="MedicineTable";
    private final String DATABASE_TABLE_NOTE="NoteTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;

    public Listdb(Context context){
        ourContext = context;
    }

    public class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sqlcode="CREATE TABLE "+DATABASE_TABLE_MEDS+" ("+
                    KEY_ROWID+" TEXT NOT NULL, "+
                    KEY_TIME+" TEXT, "+
                    KEY_NAME+" TEXT NOT NULL, "+
                    KEY_NUMOFTIMES+" TEXT, "+
                    KEY_FROM_TIMESTAMP+" TEXT NOT NULL, "+
                    KEY_TO_TIMESTAMP+" TEXT NOT NULL, "+
                    KEY_STATE+" TEXT NOT NULL, "+
                    KEY_DAYS_OF_WEEK+" TEXT NOT NULL, "+
                    KEY_DESCRIPTION+" TEXT );";
            String sqlcode2 = "CREATE TABLE "+DATABASE_TABLE_NOTE+" ("+
                    KEY_TIME_NOTE+" TEXT NOT NULL, "+
                    KEY_NOTE+" TEXT, "+
                    KEY_MED_LIST+" TEXT NOT NULL );";
            try {
                db.execSQL(sqlcode);
                db.execSQL(sqlcode2);
            }catch (SQLException sqlException){
                Toast.makeText(ourContext, sqlException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_MEDS);
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_NOTE);
            onCreate(db);
        }
    }
    public Listdb open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long createEntryInMeds(String time, String name, String num_of_times, String from_date, String to_date, String state, ArrayList<String> days_of_week, String description) {
        String id = String.valueOf(Math.floor(Math.random()*(100*10000)));
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID,id);
        cv.put(KEY_TIME,time);
        cv.put(KEY_NAME,name);
        cv.put(KEY_NUMOFTIMES,num_of_times);
        cv.put(KEY_FROM_TIMESTAMP,from_date);
        cv.put(KEY_TO_TIMESTAMP,to_date);
        cv.put(KEY_STATE, state);
        cv.put(KEY_DAYS_OF_WEEK,String.join(",", days_of_week));
        cv.put(KEY_DESCRIPTION, description);
        createEntryInNotes(time,id);
        return ourDatabase.insert(DATABASE_TABLE_MEDS,null,cv);
    }

    public long createEntryInNotes(String time, String med_id){
        Cursor c = ourDatabase.query(DATABASE_TABLE_NOTE,new String[]{KEY_TIME_NOTE,KEY_MED_LIST},KEY_TIME_NOTE+" =?",new String[]{time},null,null,null);
        int MedList = c.getColumnIndex(KEY_MED_LIST);
        int Time = c.getColumnIndex(KEY_TIME_NOTE);
        ArrayList<String> ids = new ArrayList<>();
        ContentValues cv = new ContentValues();
        if(c.getCount()==0) {
            cv.put(KEY_TIME_NOTE, time);
            cv.put(KEY_MED_LIST,med_id);
            cv.put(KEY_NOTE,"");
            ids.add(med_id);
//            ApplicationClass.times.add(new time_model(time,"",ids));
            return ourDatabase.insert(DATABASE_TABLE_NOTE, null, cv);
        }
        else {
            c.moveToFirst();
            String str = c.getString(MedList)+","+med_id;
            cv.put(KEY_MED_LIST,str);
            return ourDatabase.update(DATABASE_TABLE_NOTE,cv,KEY_TIME_NOTE+" =?", new String[]{time});
        }
    }
    public long insertKeyTimeNote(String time, String note){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTE,note);
        return ourDatabase.update(DATABASE_TABLE_NOTE,cv,KEY_TIME_NOTE+" =?",new String[]{time});
    }
    public String getKeyTime(String id){
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS,new String[]{KEY_TIME},KEY_ROWID+" =?",new String[]{id},null,null,null);
        c.moveToFirst();
        return c.getString(0);
    }
    public ArrayList<String> getMedIds(){
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS,new String[]{KEY_ROWID},null,null,null,null,null);
        ArrayList<String> t = new ArrayList<>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            t.add(c.getString(0));
        return t;
    }
    public String getKeyTimeNote(String time){
        Cursor c = ourDatabase.query(DATABASE_TABLE_NOTE,new String[]{KEY_NOTE},KEY_TIME_NOTE+" =?",new String[]{time},null,null,null);
        c.moveToFirst();
        return c.getString(0);
    }
    public ArrayList<String> getTimeArray() {
        Cursor c = ourDatabase.query(DATABASE_TABLE_NOTE, new String[]{KEY_TIME_NOTE}, null, null, null, null, KEY_TIME_NOTE);
        ArrayList<String> t = new ArrayList<>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            t.add(c.getString(0));
        return t;
    }

    public String getKeyName(String id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_NAME}, KEY_ROWID+" =?",new String[]{id}, null, null, KEY_ROWID);
        c.moveToFirst();
        return c.getString(0);
    }
    public String getKeyNumoftimes(String id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_NUMOFTIMES}, KEY_ROWID+" =?", new String[]{id}, null, null, KEY_ROWID);
        c.moveToFirst();
        return c.getString(0);
    }
    public ArrayList<String> getKeyDaysOfWeek (String id){
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_DAYS_OF_WEEK}, KEY_ROWID+" =?", new String[]{id}, null, null, null);
        c.moveToFirst();
        return new ArrayList<String>(Arrays.asList(c.getString(0).split(",")));
    }
    public String getKeyFromTimestamp(String id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_FROM_TIMESTAMP}, KEY_ROWID+" =?", new String[]{id}, null, null, KEY_ROWID);
        c.moveToFirst();
        return c.getString(0);
    }
    public String getKeyDescription(String id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_DESCRIPTION}, KEY_ROWID+" =?", new String[]{id}, null, null, KEY_ROWID);
        c.moveToFirst();
        return c.getString(0);
    }
    public String getKeyToTimestamp(String id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS, new String[]{KEY_TO_TIMESTAMP}, KEY_ROWID+" =?", new String[]{id}, null, null, KEY_ROWID);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getKeyNote(String id) {
        Cursor c= ourDatabase.query(DATABASE_TABLE_MEDS,new String[]{KEY_NOTE},KEY_ROWID+" =?",new String[]{id},null,null,null);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getKeyState(String id){
        Cursor c = ourDatabase.query(DATABASE_TABLE_MEDS,new String[]{KEY_STATE},KEY_ROWID+" =?",new String[]{id},null,null,null);
        c.moveToFirst();
        return c.getString(0);
    }

    public ArrayList<String> getKeyMedList(String time) {
        Cursor c= ourDatabase.query(DATABASE_TABLE_NOTE,new String[]{KEY_MED_LIST},KEY_TIME_NOTE+" =?",new String[]{time},null,null,null);
        c.moveToFirst();
        return new ArrayList<String>(Arrays.asList(c.getString(0).split(",")));
    }

    public long deleteEntry(String rowID)
    {
        return ourDatabase.delete(DATABASE_TABLE_MEDS,KEY_ROWID+"=?",new String[]{rowID});
    }

    public boolean isEmpty()
    {
        boolean e=true;
        Cursor c=ourDatabase.rawQuery("SELECT COUNT(*) FROM "+DATABASE_TABLE_NOTE,null);
        if(c!=null && c.moveToFirst())
            e=(c.getInt(0))==0;
        c.close();
        return e;
    }
}
