package com.configaware.med365doctors;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shubham on 5/13/2015.
 */
public class reportCollectionAdapter extends AsyncTask<String,Void,String> {

    private Context context;
    private TextView resultview;
    private ListView listView;
    ProgressDialog progressDialog;
    String errorFlag=null,vName,vPassword,vIP,vHospital,vPartnerKey,ifLocal=null;
    Cursor cursor;
    public reportCollectionAdapter(Context context, TextView resultView, ListView listView) {
        this.context=context;
        this.resultview=resultView;
        this.listView=listView;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching Report Collections!!");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        dataBaseAdapter dbAdapter =new dataBaseAdapter(context);
        cursor = dbAdapter.fetchData();
        if (cursor.moveToFirst()){
            do{
                vPartnerKey=cursor.getString(cursor.getColumnIndex("partner_key"));
                Log.e("Column Key",vPartnerKey);
            }while(cursor.moveToNext());
        }
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

        String url="http://54.66.144.238/med365app/getcollectionreport.php";
        InputStream isr = null;
        try{

        HttpPost httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("dateFrom", arg2));
            nameValuePairs.add(new BasicNameValuePair("dateTo", arg3));
            nameValuePairs.add(new BasicNameValuePair("partnerKey", vPartnerKey));
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
            return result;
        }
        catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        final ArrayList <HashMap<String,String>> collectionList=new ArrayList<HashMap<String, String>>();
        if(result==null){
            this.resultview.setText("Error in Connection");
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            this.resultview.setText(result);
            //parse json data
            try {

                JSONArray jArray = new JSONArray(result);

                for(int i=0; i<jArray.length();i++){
                    JSONObject json = jArray.getJSONObject(i);
                    String date = json.getString("ORDER_DATE");
                    String amount = json.getString("AMOUNT");
                    String orders = json.getString("ORDER_NUMBER");

                    HashMap<String,String> hashMap=new HashMap<String,String>();
                    hashMap.put("DATE",date);
                    hashMap.put("AMOUNT", amount);
                    hashMap.put("ORDERS",orders);
                    collectionList.add(hashMap);
                    ListAdapter listAdapter=new SimpleAdapter(context,collectionList,R.layout.list_item,new String[]{"DATE","AMOUNT","ORDERS"},new int[]{R.id.listDate,R.id.listAmount,R.id.listOrderNum});
                    listView.setAdapter(listAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(context,"Order Numbers on "+collectionList.get(+position).get("DATE"),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (JSONException e) {
                // TODO: handle exception
                Log.e("log_tag", "Error Parsing Data "+e.toString());
            }
        }
    }
}