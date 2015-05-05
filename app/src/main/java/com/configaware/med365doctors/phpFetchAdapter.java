package com.configaware.med365doctors;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naman on 5/4/2015.
 */
public class phpFetchAdapter extends AsyncTask<String,Void,String> {

    private Context context;
    private TextView resultview;
    ProgressDialog progressDialog;
    String errorFlag,vName,vPassword,vIP,vHospital,ifLocal;

    public phpFetchAdapter(Context context,TextView resultView) {
        this.context=context;
        this.resultview=resultView;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        //progressDialog.setIcon(R.drawable.);
        //progressDialog.setTitle("Login ");
        progressDialog.setMessage("Keep Calm while we log you in.");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String arg1,arg2,arg3;
        arg1 = (String)params[0];
        arg2 = (String)params[1];
        arg3 = (String)params[2];
        ifLocal = arg1;
        vName=arg2;
        vPassword=arg3;

        String result = "";
        InputStream isr = null;
        try{
            String url="http://54.66.224.33/med365app/login.php";
            HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", arg2));
            nameValuePairs.add(new BasicNameValuePair("password", arg3));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpParams params1 = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params1, 15000);
            HttpClient httpclient = new DefaultHttpClient(params1);
            HttpResponse response = httpclient.execute(httppost);
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

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                s = s +
                        ""+json.getString("doctor_hospital")+"\n"+
                        ""+json.getString("doctor_ip")+"\n\n";
                errorFlag=json.getString("doctor_hospital");
                vHospital = json.getString("doctor_hospital");
                vIP = json.getString("doctor_ip");
            }

            return (s);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        this.resultview.setText(result);
        if(!errorFlag.equals("Error") && !ifLocal.equals("Local Login"))
        {
            dataBaseAdapter dbAdapter =new dataBaseAdapter(vName,vPassword,vIP,vHospital,context);
            dbAdapter.insertData();
        }
    }
}