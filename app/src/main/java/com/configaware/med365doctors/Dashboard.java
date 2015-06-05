package com.configaware.med365doctors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Dashboard extends ActionBarActivity {

    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        webView = (WebView) findViewById(R.id.dashboard);
        dataBaseAdapter mydb=new dataBaseAdapter(this);
        String password="";
        String userlogin="";
        if(!mydb.isEmpty())
        {
            Log.e("isEmpty", "Not Empty");
            Cursor result = mydb.fetchData();
            if (result!=null){
                result.moveToFirst();
                do{
                    userlogin = result.getString(1);
                    password = result.getString(2);
                    Log.e("Local Data", result.getString(1)+" "+result.getString(2));
                }while (result.moveToNext());
            }
        }
        String url = "http://ehr.med365.in/med365/AuthenticateUser?userlogin="+userlogin+"&password="+password+"&singlesignon=true";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new SwAWebClient());
        webView.loadUrl(url);
    }

    private static class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        Intent intent = new Intent(Dashboard.this, NavDrawer.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_aboutUs:
                Intent myIntent = new Intent(Dashboard.this, AboutUs.class);
                myIntent.putExtra("Activity_name","Dashboard");
                startActivity(myIntent);
                finish();
                return true;


            case R.id.action_home:
                 myIntent = new Intent(Dashboard.this, NavDrawer.class);
                Dashboard.this.startActivity(myIntent);
                finish();
                return true;

            /*case R.id.action_contactUs:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(" MED365 ");
                builder.setMessage(" Under construction ");
                // builder.setIcon(R.drawable.icon_me);
                builder.show();
                return true;*/

            case R.id.action_logOut:
                //Logout
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle(" Log Out ");
                builder1.setIcon(R.mipmap.alert);
                builder1.setMessage("Are you sure you want to Logout?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dataBaseAdapter dataBaseAdapter = new dataBaseAdapter(getApplicationContext(), "Log Out");
                                Toast.makeText(Dashboard.this, "Logged Out", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Dashboard.this, SearchDoctors.class);
                                startActivity(intent);
                                finish();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Logout cancelled.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();

                return true;

            case R.id.action_shareApp:
                //Share the App
                String shareBody = "Now Health Data comes in all sizes in your pocket!Take Health Care with you wherever you go.\n";
                shareBody += "http://bit.ly/tXhJ3j";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MED365");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}