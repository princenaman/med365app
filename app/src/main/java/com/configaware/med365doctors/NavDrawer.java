package com.configaware.med365doctors;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


public class NavDrawer extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;


    private PieChart mPieChart;
    private float[] yData={5,10,15,30,40};
    private String[] xData={"Cash","Credit","Cheque","MediClaim","Insurance"};
    RelativeLayout PieChartLayout,PieChartLayout2;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        PieChartLayout=(RelativeLayout)findViewById(R.id.PieChartXMLLayout);
       // PieChartLayout2=(RelativeLayout)findViewById(R.id.PieChartXMLLayout2);



        showNavigationDrawer();
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        showPieChart(PieChartLayout);
       // ((ViewGroup)PieChartLayout.getParent()).removeView(PieChartLayout);



    }
    private void showPieChart(RelativeLayout relativeLayout) {

        mPieChart=new PieChart(this);
        relativeLayout.addView(mPieChart);


        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("Collection Reports");
        mPieChart.setDescriptionTextSize(18f);


        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColorTransparent(true);
        mPieChart.setHoleRadius(40);
        mPieChart.setTransparentCircleRadius(10);

        mPieChart.setRotationEnabled(true);
        mPieChart.setRotationAngle(0);

       // mChart.setDescriptionPosition();
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if(entry==null)
                    return;
                Toast.makeText(NavDrawer.this, xData[entry.getXIndex()] + "=" + entry.getVal() + "%", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        addDataForPieChart();

        mPieChart.animateXY(1000, 1000);


        Legend l=mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

    }

    private void addDataForPieChart() {
        ArrayList<Entry> yValues=new ArrayList<Entry>();
        for(int i=0; i <yData.length;i++)
            yValues.add(new Entry(yData[i],i));

        ArrayList<String> xValues=new ArrayList<String>();
        for( int j=0; j <xData.length;j++)
            xValues.add(xData[j]);

        PieDataSet pieDataSet=new PieDataSet(yValues,"Collection Shares");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setSelectionShift(5);


        ArrayList <Integer> colors=new ArrayList<Integer>();
        for(int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        PieData data=new PieData(xValues,pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mPieChart.setData(data);
        mPieChart.highlightValues(null);

        mPieChart.invalidate();



    }

    private void showNavigationDrawer() {
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // AMS
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Outlook
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // MCD
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Phone
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Taxi
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
        // Restaurant
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        // Events
       // navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_aboutUs:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(" MED365 ");
                builder.setMessage(" Under construction ");
                // builder.setIcon(R.drawable.icon_me);
                builder.show();
                return true;
          /*  case R.id.action_home:
                Intent myIntent = new Intent(NavDrawer.this, NavDrawer.class);
                NavDrawer.this.startActivity(myIntent);
                finish();
                return true;*/

            case R.id.action_contactUs:
                builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(" MED365 ");
                builder.setMessage(" Under construction ");
                // builder.setIcon(R.drawable.icon_me);
                builder.show();
                return true;

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
                                dataBaseAdapter dataBaseAdapter = new dataBaseAdapter(getApplicationContext(),"Log Out");
                                Toast.makeText(NavDrawer.this,"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NavDrawer.this,Splash.class);
                                startActivity(intent);
                                finish();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Logout cancelled.",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();

                return true;

            case R.id.action_shareApp:
                //Share the App
                String shareBody="Take Health Care with you wherever you go.\n";
                shareBody+="http://bit.ly/tXhJ3j";

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

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_aboutUs).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Intent intent;
        switch (position) {
            case 0:
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            case 1:
                intent = new Intent(NavDrawer.this,HospitalData.class);
                intent.putExtra("Title","Collections");
                startActivity(intent);
                finish();
                break;
            case 2:
                intent = new Intent(NavDrawer.this,Tracking.class);
                intent.putExtra("Title","Track MR");
                startActivity(intent);
                finish();
                break;
            case 3:
                intent = new Intent(NavDrawer.this,Dashboard.class);
                intent.putExtra("Title","Dashboard");
                startActivity(intent);
                finish();
                break;
            case 4:

                break;
            case 5:
                intent = new Intent(NavDrawer.this,SearchDoctors.class);
                intent.putExtra("Title","Search Doctors");
                startActivity(intent);
                finish();
                break;
            case 6:

                break;
            case 7:

                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment ");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



}
