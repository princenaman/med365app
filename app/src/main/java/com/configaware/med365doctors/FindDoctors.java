package com.configaware.med365doctors;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class FindDoctors extends ActionBarActivity {

    String searchquery, location;
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctors);
        Intent intent = getIntent();
        searchquery = intent.getStringExtra("searchquery");
        location = intent.getStringExtra("location");
        Toast.makeText(getBaseContext(),
                "Search Query: " + searchquery + " & Location: " + location,
                Toast.LENGTH_SHORT).show();
        wv = (WebView)findViewById(R.id.searchDoctors);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new SwAWebClient());
        //String currentURL = "http://med365.in/doctors/edocsearch.php?location="+location+"&searchquery="+searchquery;
        String currentURL = "http://med365.in/med365/edocsearch?location="+location+"&searchquery="+searchquery;
        wv.loadUrl(currentURL);
    }
    private class SwAWebClient extends WebViewClient {
        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(wv.canGoBack()){
                        wv.goBack();
                    }
                    else {
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
        getMenuInflater().inflate(R.menu.menu_find_doctors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
