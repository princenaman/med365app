package com.configaware.med365doctors;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class SearchDoctors extends ActionBarActivity {

    private Spinner location;
    private Button btn,btnLogin;
    private EditText searchquery;
    ImageView ivLogIn,ivAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);
        searchquery = (EditText) findViewById(R.id.searchquery);
        location = (Spinner) findViewById(R.id.location);
        btn = (Button) findViewById(R.id.btn_search);
        ivLogIn=(ImageView)findViewById(R.id.ivLogin);
        ivAboutUs=(ImageView)findViewById(R.id.ivAboutUs);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
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
       ivLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==ivLogIn)
                {
                    Intent intent = new Intent(SearchDoctors.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
        ivAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==ivAboutUs)
                {
                    Intent intent = new Intent(SearchDoctors.this, AboutUs.class);
                    intent.putExtra("Activity_name", "SearchDoctors");
                    startActivity(intent);
                    finish();

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnLogin)
                {
                    Intent intent = new Intent(SearchDoctors.this, MainActivity.class);
                    startActivity(intent);
                    finish();

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


        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_aboutUs:
                AlertDialog.Builder builder;
             /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(" MED365 ");
                builder.setMessage(" Under construction ");
                // builder.setIcon(R.drawable.icon_me);
                builder.show();
                return true;*/
                Intent myIntent = new Intent(SearchDoctors.this, AboutUs.class);
                myIntent.putExtra("Activity_name", "SearchDoctors");
                startActivity(myIntent);
                finish();
                return true;


            case R.id.action_shareApp:
                //Share the App
                String shareBody = "Take Health Care with you wherever you go.\n";
                shareBody += "http://med365.in";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Now Health Data comes in all sizes in your pocket!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
