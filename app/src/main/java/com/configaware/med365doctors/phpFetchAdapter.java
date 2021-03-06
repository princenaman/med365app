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
 * Created by naman on 5/4/2015.
 */
public class phpFetchAdapter extends AsyncTask<String,Void,String> {

    private Context context;
    private TextView resultview;
    ProgressDialog progressDialog;
    String errorFlag=null,vName,vPassword,vIP,vHospital,vPartnerKey,ifLocal=null,vUserType;

    public phpFetchAdapter(Context context,TextView resultView) {
        this.context=context;
        this.resultview=resultView;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        //progressDialog.setIcon(R.drawable.);
        //progressDialog.setTitle("Login ");
        progressDialog.setMessage("Please wait while we connect!!");
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
        ifLocal = arg1;
        vName=arg2;
        vPassword=arg3;

        String result = "";

        String url="http://54.66.144.238/med365app/login.php";
        InputStream isr = null;
        try{
            if (arg1.equals("opd") || arg1.equals("ipd") || arg1.equals("tpa") || arg1.equals("lic"))
            {
                url="http://54.66.144.238/med365app/opd.php";
            }


            HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", arg2));
            nameValuePairs.add(new BasicNameValuePair("password", arg3));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpParams params1 = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params1, 15000);
            HttpClient httpclient = new DefaultHttpClient(params1);
            HttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode() != 200) //which means there is no connection
                return "No Internet";
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
                        //""+json.getString("GLOBAL_IP")+"\n"+
                        ""+json.getString("PARTNER_KEY")+"\n";//+
                        //""+json.getString("PARTNER_COMPANY")+"\n\n";
                vUserType = json.getString("USER_TYPE_CODE");
                Log.e("User Type",vUserType);
                vHospital = json.getString("PARTNER_COMPANY");
                vIP = "54.66.144.238";
                vPartnerKey=json.getString("PARTNER_KEY");
                errorFlag = vIP;
            }
            return (s);

        } catch (JSONException e) {
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            return null;
        }
 }

    @Override
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        if(result==null){
            //this.resultview.setText("Error in Connection");
                Toast.makeText(context, "Please Check Your Internet Connectivity ",Toast.LENGTH_LONG).show();
        }
        else
            this.resultview.setText(result);

        if (errorFlag==null){
            this.resultview.setText("Error in Connection");
        }
        else if (errorFlag.equals("Error"))
        {
            this.resultview.setText("Credentials Incorrect or Connection Error");
        }
        else if(!errorFlag.equals("Error") && !ifLocal.equals("Local Login"))
        {
            this.resultview.setText(result);
            dataBaseAdapter dbAdapter =new dataBaseAdapter(vName,vPassword,vIP,vHospital,vPartnerKey,vUserType,context);
            dbAdapter.insertData();
            Intent intent;
            intent = new Intent(context,NavDrawer.class);

            intent.putExtra("IP",vIP);
           context.startActivity(intent);
            ((Activity)context).finish();
        }
        else if (!errorFlag.equals("Error"))
        {
            this.resultview.setText(result);
           // Intent intent =new Intent(context, MainActivity.class);
            Intent intent =new Intent(context, NavDrawer.class);
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