package com.configaware.med365doctors;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nk on 6/4/2015.
 */
public class chartAdapter extends AsyncTask<String,Void,String> {

    String vUserType,vPartnerKey;
    Context context;
    Cursor cursor;

    public chartAdapter(Context context) {
        this.context=context;
    }

    @Override
    protected void onPreExecute(){
        dataBaseAdapter dbAdapter =new dataBaseAdapter(context);
        cursor = dbAdapter.fetchData();
        if (cursor.moveToFirst()){
            do{
                vPartnerKey=cursor.getString(cursor.getColumnIndex("partner_key"));
                vUserType=cursor.getString(cursor.getColumnIndex("doctor_type"));
                Log.e("Details",vPartnerKey+" "+vUserType);
            }while(cursor.moveToNext());
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String url="http://54.66.144.238/med365app/getchartdata.php";
        InputStream isr = null;
        try{

            HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("partnerKey", vPartnerKey));
            nameValuePairs.add(new BasicNameValuePair("userType", vUserType));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpParams params1 = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params1, 15000);
            HttpClient httpclient = new DefaultHttpClient(params1);
            HttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode() != 200) //which means there no is connection
                return null;
            HttpEntity entity1 = response.getEntity();
            isr = entity1.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
            return ("Couldn't connect to Server");
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            result=sb.toString();
            Log.e("Result", "Data: " + sb.toString());
        }
        catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }
        return result;
    }

}
