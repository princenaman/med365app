package com.configaware.med365doctors;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        dataBaseAdapter mydb=new dataBaseAdapter(this);
        if(!mydb.isEmpty())
        {
            Log.e("isEmpty", "Not Empty");
            Cursor result = mydb.fetchData();
            if (result!=null){
                result.moveToFirst();

                TextView resultView = (TextView) findViewById(R.id.splashText);

                do{
                    new phpFetchAdapter(Splash.this,resultView).execute("Splash", result.getString(1), result.getString(2));
                    Log.e("Local Data", result.getString(1)+" "+result.getString(2));
                }while (result.moveToNext());
            }
        }
        else {
             new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Splash.this,SearchDoctors.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}