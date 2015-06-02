package com.configaware.med365doctors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutUs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        TextView title = (TextView) findViewById(R.id.textView20);
        TextView content = (TextView) findViewById(R.id.textView21);
        title.setText("MED365");
        content.setText("• Staying healthy is better ever then before\n" +
                "• Find Doctors and Practitioners near you\n" +
                "• Book Appointments on the go\n" +
                "• Get appointment notifications\n" +
                "• Get e-Prescriptions from Doctors\n" +
                "• Identifying, uploading and maintaining patient vitals, test reports\n" +
                "• Captures and manages clinical documents and notes\n" +
                "• Managing patient vitals with intelligent and configurable graphs\n" +
                "• Cloud-based System, 24x7 access; from any location and device\n" +
                "• Viewing tracking and modifying patient visits, clinical diagnostics and orders/invoices\n" +
                "• Business data and overall collection revenue of the practitioner in (configurable) graphs\n" +
                "• Seamless billing system\n" +
                "• Patient care/Customer Relationship Management\n" +
                "• Find alternatives and Generic Medicines\n" +
                "• Compare medicine prices\n" +
                "Need help? Please tell us more about the issue. http://med365.in/");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        startActivity(new Intent(AboutUs.this, NavDrawer.class));
        finish();
    }
}
