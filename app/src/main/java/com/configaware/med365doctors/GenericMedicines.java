package com.configaware.med365doctors;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class GenericMedicines extends ActionBarActivity implements View.OnClickListener {

    View rootView;
    EditText searchquery;
    Button searchBtn;
    ImageButton crocin, ampicillin, ventrolyn, benadryl, otrivin, pain, fever, pressure, cold, cough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_medicines);
        searchquery = (EditText) findViewById(R.id.search_med_text);
        searchBtn = (Button) findViewById(R.id.search_med);
        crocin = (ImageButton) findViewById(R.id.crocin_btn);
        ampicillin = (ImageButton) findViewById(R.id.ampicillin_btn);
        ventrolyn = (ImageButton) findViewById(R.id.ventrolin_btn);
        benadryl = (ImageButton) findViewById(R.id.benadryl_btn);
        otrivin = (ImageButton) findViewById(R.id.otrivin_btn);
        pain = (ImageButton) findViewById(R.id.cure1);
        fever = (ImageButton) findViewById(R.id.cure2);
        pressure = (ImageButton) findViewById(R.id.cure3);
        cold = (ImageButton) findViewById(R.id.cure4);
        cough = (ImageButton) findViewById(R.id.cure5);
        searchBtn.setOnClickListener(this);
        crocin.setOnClickListener(this);
        ampicillin.setOnClickListener(this);
        ventrolyn.setOnClickListener(this);
        benadryl.setOnClickListener(this);;
        otrivin.setOnClickListener(this);
        pain.setOnClickListener(this);
        fever.setOnClickListener(this);
        pressure.setOnClickListener(this);
        cold.setOnClickListener(this);
        cough.setOnClickListener(this);
    }

   @Override
    public void onClick(View v) {
    /*    if(v == searchBtn){
            String text = searchquery.getText().toString();
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            startActivity(intent);
            finish();
        }
        if(v == crocin){
            String text = "Crocin Pain Relief Tab";
            Intent intent = new Intent(GenericMedicines.this, Location.class);
            intent.putExtra("type","home");
            intent.putExtra("brand-name", text);
            startActivity(intent);
            finish();
        }
        if(v == ampicillin){
            String text = "Ampicillin (100 mg) (Prem Pharma)";
            Intent intent = new Intent(GenericMedicines.this, MedDetails.class);
            intent.putExtra("type","home");
            intent.putExtra("brand-name", text);
            startActivity(intent);
           finish();
        }
        if(v == ventrolyn){
            String text = "Ventorlin (100 mcg)";
            Intent intent = new Intent(GenericMedicines.this, MedDetails.class);
            intent.putExtra("type","home");
            intent.putExtra("brand-name", text);
            startActivity(intent);
            finish();
        }
        if(v == benadryl){
            String text = "Benadryl (100 ml) (J & J Chemicals)";
            Intent intent = new Intent(GenericMedicines.this, MedDetails.class);
            intent.putExtra("type","home");
            intent.putExtra("brand-name", text);
            startActivity(intent);
            finish();
        }
        if(v == otrivin){
            String text = "Otrivin (Nasal) (0.05%)";
            Intent intent = new Intent(GenericMedicines.this, MedDetails.class);
            intent.putExtra("type","home");
            intent.putExtra("brand-name", text);
            startActivity(intent);
          finish();
        }
        if(v == pain){
            String text = "cure";
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("generic","paracetamol");
            intent.putExtra("disease","Pain");
            startActivity(intent);
            finish();
        }
        if(v == fever){
            String text = "cure";
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("generic","acetaminophen");
            intent.putExtra("disease","Fever");
            startActivity(intent);
            finish();
        }
        if(v == pressure){
            String text = "cure";
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("generic","amlodipine");
            intent.putExtra("disease","Blood Pressure");
            startActivity(intent);
            finish();
        }
        if(v == cold){
            String text = "cure";
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("generic","chlorpheniramine");
            intent.putExtra("disease","Common Cold");
            startActivity(intent);
            finish();
        }
        if(v == cough){
            String text = "cure";
            Intent intent = new Intent(GenericMedicines.this, SearchResultsActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("generic","diphenhydramine");
            intent.putExtra("disease","Cough");
            startActivity(intent);
            finish();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generic_medicines, menu);
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
