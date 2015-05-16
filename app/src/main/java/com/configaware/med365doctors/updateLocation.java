package com.configaware.med365doctors;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by naman on 5/13/2015.
 */
public class updateLocation extends AsyncTask<String,Void,String> {

    private String activityName = "";
    private Context context;
    private TextView resultview;
    ProgressDialog progressDialog;
    String errorFlag=null,vName,vPassword,vIP,vHospital,ifLocal=null;

    public updateLocation(Context context,TextView resultView) {
        this.context=context;
        this.resultview=resultView;
    }

    public updateLocation(Context context, TextView resultView, String activityName) {
        this.context=context;
        this.resultview=resultView;
        this.activityName = activityName;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait while we connect!!");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        if(!activityName.equals("Splash"))
            progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String arg1,arg2,arg3,arg4;
        arg1 = (String)params[0];
        arg2 = (String)params[1];
        arg3 = (String)params[2];
        arg4 = (String)params[3];
        ifLocal = arg1;
        vName=arg2;
        vPassword=arg3;

        String result = "";
        String url="http://54.66.144.238/med365app/set_locations.php";
        InputStream isr = null;
        try{

            HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", arg1));
            nameValuePairs.add(new BasicNameValuePair("password", arg2));
            nameValuePairs.add(new BasicNameValuePair("latitude", arg3));
            nameValuePairs.add(new BasicNameValuePair("longitude", arg4));
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

    @Override
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        if(result==null){
            this.resultview.setText("Error in Connection");
            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
        }
        else
            this.resultview.setText(result);
    }
}