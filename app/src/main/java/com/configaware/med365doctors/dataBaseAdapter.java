package com.configaware.med365doctors;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by Shubham on 5/4/2015.
 */
public class dataBaseAdapter {

    String vName,vPassword,vIP,vHospital;
    SQLiteDatabase myDatabase;
    public dataBaseAdapter(String vName,String vPassword,String vIP,String vHospital, Context context) {

        this.vName=vName;
        this.vPassword=vPassword;
        this.vIP=vIP;
        this.vHospital=vHospital;
        myDatabase=context.openOrCreateDatabase("MED365.db",Context.MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS doctors (_id int, doctor_name text,doctor_password text,doctor_hospital text, doctor_ip text, primary key (_id)) ");

    }
     public void insertData()
     {

         myDatabase.execSQL("INSERT INTO doctors (_id , doctor_name,doctor_password ,doctor_hospital, doctor_ip) values (1,'"+ vName + "','" + vPassword + "','" + vIP + "','" + vHospital+ "')");
         Log.e("DB","Inserted Values:"+vName+", "+vPassword+", "+vIP+", "+vHospital);
     }
   /* public void fetchData()
    {
        myDatabase.execSQL("SELECT * FROM doctors");
    }*/


}
