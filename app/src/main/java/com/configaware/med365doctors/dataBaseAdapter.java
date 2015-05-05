package com.configaware.med365doctors;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


/**
 * Created by Shubham on 5/4/2015.
 */
public class dataBaseAdapter {

    String vName,vPassword,vIP,vHospital;
    SQLiteDatabase myDatabase;
    Context context;
    public dataBaseAdapter(String vName,String vPassword,String vIP,String vHospital, Context context) {

        this.vName=vName;
        this.vPassword=vPassword;
        this.vIP=vIP;
        this.vHospital=vHospital;
        this.context = context;
        initDB();
    }

    public dataBaseAdapter(Context context) {
        this.context=context;
        initDB();
    }
    public void initDB()
    {
        Log.e("Try","Create Table");
        try {
            myDatabase = context.openOrCreateDatabase("MED365.db", Context.MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS doctors (_id INTEGER PRIMARY KEY AUTOINCREMENT, doctor_name VARCHAR(50), doctor_password  VARCHAR(100), doctor_hospital  VARCHAR(100), doctor_ip  VARCHAR(50))");
        }catch (SQLiteException e){
            Log.e("Error",""+e.toString());
        }
    }

    public void insertData()
     {

         myDatabase.execSQL("INSERT INTO doctors (doctor_name,doctor_password ,doctor_hospital, doctor_ip) values ('"+ vName + "','" + vPassword + "','" + vIP + "','" + vHospital+ "')");
         Log.e("DB","Inserted Values:"+vName+", "+vPassword+", "+vIP+", "+vHospital);
     }
    public Cursor fetchData()
    {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM doctors limit 1",null);
        return cursor;
    }
    public  boolean isEmpty()
    {
        Cursor cur = myDatabase.rawQuery("SELECT count(*) FROM doctors", null);
        if(cur!=null) {
            cur.moveToFirst();
            if(cur.getInt(0)==0)
                return true;
        }
        return false;
    }



}
