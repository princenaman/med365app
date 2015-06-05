package com.configaware.med365doctors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AboutUs extends Activity {
    String value;
    TextView mContent;
    String contentString="";
    LinearLayout layoutMiddle,layoutEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Bundle extras = getIntent().getExtras();
        mContent=(TextView)findViewById(R.id.content);
        if (extras != null) {
             value = extras.getString("Activity_name");
        }
        contentString="Med365 Practitioner’s system allows practitioners to focus on delivering excellent care to your patients. Designed for long-term patient-provider relationship  & continuity of care,we provide following services:\n" +
                "• MED365 PRACTITIONER'S SYSTEM\n" +
                "• MED365 VISION\n"+
                "• MED365 EHR";
        mContent.setText(contentString);
        layoutMiddle=(LinearLayout)findViewById(R.id.middle);
        layoutEnd=(LinearLayout)findViewById(R.id.end);

        layoutMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://med365.in";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        layoutEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.med365.in/doctors";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
     /*   tv1=(TextView)findViewById(R.id.TextView1);
        tv2=(TextView)findViewById(R.id.TextView2);
        tv1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        tv2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUs.this,"TV2",Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        switch(value)
        {
            case "NavDrawer":
                startActivity(new Intent(AboutUs.this, NavDrawer.class));
                finish();
                break;
            case "Dashboard":
                startActivity(new Intent(AboutUs.this, Dashboard.class));
                finish();
                break;
            case "HospitalData":
                startActivity(new Intent(AboutUs.this, HospitalData.class));
                finish();
                break;
            case "Tracking":
                startActivity(new Intent(AboutUs.this, Tracking.class));
                finish();
                break;
            case "SearchDoctors":
                startActivity(new Intent(AboutUs.this, SearchDoctors.class));
                finish();
                break;

            default:
                startActivity(new Intent(AboutUs.this, SearchDoctors.class));
                finish();

        }

    }
}
