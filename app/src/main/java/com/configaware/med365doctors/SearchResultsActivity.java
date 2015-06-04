package com.configaware.med365doctors;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class SearchResultsActivity extends ActionBarActivity {

    private TextView txtQuery;
    private SQLiteCountryAssistant sqlliteCountryAssistant;
    ListView listView;
    ActionBar actionBar;
    String query = null, item = null, generic = null, disease = null;
    String search_query;
    String ifgeneric = null;

    // Custom List View //
   // private List<custom> mycustom = new ArrayList<custom>();
    SQLiteDatabase db;
    private String brand_name1;
    private String generic_name1;
    private String manufacturer1;
    private String cost_quantity1;
    private String form1;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        txtQuery = (TextView) findViewById(R.id.txtQuery);
        listView = (ListView)findViewById(R.id.searchList);
        db=openOrCreateDatabase("meds.sqlite", Context.MODE_PRIVATE, null);
      //  handleIntent(getIntent());
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SearchResultsActivity.this, NavDrawer.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
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

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            actionBar.setTitle(query);
            //createList(query);
            txtQuery.setText("You searched for: " + query);
        }
        else{
            item = intent.getStringExtra("selected-item");
            if(item.equals("cure")){
                generic = intent.getStringExtra("generic");
                disease = intent.getStringExtra("disease");
                actionBar.setTitle(disease);
                txtQuery.setText("You searched for: " + disease);
              //  createGenericList(generic);
            }
            else if(item.equals("back")){
                query = intent.getStringExtra("query");
                //createList(query);
                txtQuery.setText("You searched for: " + query);
            }
            else{
                actionBar.setTitle(item);
                txtQuery.setText("You searched for: " + item);
                //createList(item);
            }
        }

    }

}
