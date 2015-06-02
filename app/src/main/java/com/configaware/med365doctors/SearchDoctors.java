package com.configaware.med365doctors;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class SearchDoctors extends ActionBarActivity {

    private Spinner location;
    private Button btn;
    private EditText searchquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);
        searchquery = (EditText) findViewById(R.id.searchquery);
        location = (Spinner) findViewById(R.id.location);
        btn = (Button) findViewById(R.id.btn_search);
        location = (Spinner) findViewById(R.id.location);
        location.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn) {
                    Intent intent = new Intent(SearchDoctors.this, FindDoctors.class);
                    String searchquery_text = searchquery.getText().toString();
                    String location_text = String.valueOf(location.getSelectedItem());
                    if (location_text.equals("Select Your Location")) {
                        location_text = "";
                    }
                    intent.putExtra("searchquery", searchquery_text);
                    intent.putExtra("location", location_text);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_doctors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
