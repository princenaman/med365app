package com.configaware.med365doctors;

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
        String url = "http://ehr.med365.in/med365/AuthenticateUser?userlogin="+userlogin+"&password="+password+"";
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
                        Intent intent = new Intent(Dashboard.this, HospitalData.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            dataBaseAdapter dataBaseAdapter = new dataBaseAdapter(getApplicationContext(), "Log Out");
            dataBaseAdapter.emptyData();
            Toast.makeText(Dashboard.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Dashboard.this, Splash.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}