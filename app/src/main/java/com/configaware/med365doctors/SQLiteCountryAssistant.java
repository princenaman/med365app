package com.configaware.med365doctors;

/**
 * Created by naman on 3/28/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteCountryAssistant extends SQLiteOpenHelper
{
    public static String DB_PATH = "/data/data/naman.com.med/databases/";
    public static final String DB_NAME = "meds.sqlite";
    private static final int DB_VERSION= 1;
    private static final String DB_TABLE_NAME = "drug";
    private static final String DB_COLUMN_1_NAME = "BRAND_NAME";

    //private static final String DB_CREATE_SCRIPT = "create table " + DB_TABLE_NAME +
    //        " (_id integer primary key autoincrement, country_name text not null);)";

    private SQLiteDatabase sqliteDBInstance;
    private Context context;

    public SQLiteCountryAssistant(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void close(){
        if(sqliteDBInstance!=null){
            sqliteDBInstance.close();
        }
        super.close();
    }

    public void openDB() throws SQLException
    {
        Log.i("openDB", "Checking sqliteDBInstance...");
        String myPath = DB_PATH + DB_NAME;
        sqliteDBInstance = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void closeDB()
    {
        if(this.sqliteDBInstance != null)
        {
            if(this.sqliteDBInstance.isOpen())
                this.sqliteDBInstance.close();
        }
    }

    /***
     * Copy database from source code assets to device
     * @throws java.io.IOException
     */
    public void copyDataBase() throws IOException{
        try {
            Log.e("Copy", "copyDataBase() called");
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("CopyDatabase", "Error copy");
        }

    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws java.io.IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        Log.e("Create", "createDataBase() called");
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("Create", "Error create");
            }
        }
    }

    /***
     * Check if the database is exist on device or not
     * @return
     */
    private boolean checkDataBase() {
        Log.e("Check", "checkDataBase() called");
        boolean checkDB = false;
        try{
            String mypath = context.getFilesDir().getAbsolutePath().replace("files","databases")+ File.separator  + DB_NAME;
            File dbfile = new File(mypath);
            checkDB = dbfile.exists();
            Log.e("Check", "checkDataBase() close");
        }catch (SQLiteException e){
            Log.e("CheckDB", "error checkdb");
        }
        return checkDB;
    }

    public String[] getAllCountries()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME , null);
        if(cursor.getCount() >0)
        {
            String[] str = new String[cursor.getCount()];
            int i = 0;
            String name;
            while (cursor.moveToNext())
            {
                name = cursor.getString(1);
                str[i] = name;
                i++;
            }
            cursor.close();
            return str;
        }
        else
        {
            return new String[] {"Null"};
        }
    }

    public Cursor searchQuery(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME +" WHERE BRAND_NAME LIKE '" + item + "%'" , null);
        if(c.getCount()==0)
        {
            return null;
        }
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor searchGeneric(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME +" WHERE GNERIC_NAME_ALIAS LIKE '%" + item + "%'" , null);
        if(c.getCount()==0)
        {
            return null;
        }
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

}