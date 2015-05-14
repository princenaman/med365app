package com.configaware.med365doctors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 5/13/2015.
 */
public class reportCollectionAdapter extends AsyncTask<String,Void,String> {

    private Context context;
    private TextView resultview;
    ProgressDialog progressDialog;
    String errorFlag=null,vName,vPassword,vIP,vHospital,ifLocal=null;

    public reportCollectionAdapter(Context context,TextView resultView) {
        this.context=context;
        this.resultview=resultView;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching Report Collections!!");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String arg1,arg2,arg3,arg4 = "blank";
        arg1 = (String)params[0];
        arg2 = (String)params[1];
        arg3 = (String)params[2];
        if(params.length>3 )
            arg4 = (String)params[3];
      /*  ifLocal = arg1;
        vName=arg2;
        vPassword=arg3; */

        String result = "";

        String url="http://192.168.1.105/med365app/data.php";
        InputStream isr = null;
        try{

        HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("dateFrom", arg2));
            nameValuePairs.add(new BasicNameValuePair("dateTo", arg3));
            nameValuePairs.add(new BasicNameValuePair("payment_type", arg3));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpParams params1 = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params1, 15000);
            HttpClient httpclient = new DefaultHttpClient(params1);
            HttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode() != 200) //which means there is no connection
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

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                s = s +
                        ""+json.getString("DATE")+"\n"+
                        ""+json.getString("GROUP_SUM")+"\n"+
                        ""+json.getString("GROUP_DESCRIPTION")+"\n\n";
                //errorFlag=json.getString("PARTNER_COMPANY");

            }

            return (s);

        } catch (JSONException e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        if(result==null){
            this.resultview.setText("Error in Connection");
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
        else
            this.resultview.setText(result);

        if (errorFlag==null){
            this.resultview.setText("Error in Connection");
            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        else if (ifLocal.equals("opd") || ifLocal.equals("ipd") || ifLocal.equals("tpa") || ifLocal.equals("lic")){
            this.resultview.setText(result);
        }
        else if(!errorFlag.equals("Error") && !ifLocal.equals("Local Login"))
        {
            this.resultview.setText(result);
            dataBaseAdapter dbAdapter =new dataBaseAdapter(vName,vPassword,vIP,vHospital,context);
            dbAdapter.insertData();
            Intent intent =new Intent(context, HospitalData.class);
            intent.putExtra("IP",vIP);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        else if (!errorFlag.equals("Error"))
        {
            this.resultview.setText(result);
            Intent intent =new Intent(context, HospitalData.class);
            intent.putExtra("IP",vIP);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        else
        {
            this.resultview.setText("Error in Connection");
            Intent intent =new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
        }

    }
}