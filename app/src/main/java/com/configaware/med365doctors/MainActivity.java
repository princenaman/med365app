package com.configaware.med365doctors;
/*
Edits by shubham
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText usernameField,passwordField;
    private TextView resultView;
    private Button login;
    private ImageButton mSearchDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlogin);
        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);
        resultView = (TextView) findViewById(R.id.result);
        login = (Button) findViewById(R.id.bLogin);
        login = (Button) findViewById(R.id.bLogin);
        login.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        if(v==login){
            hideKeyboard();
            String arg1 = "login";
            String arg2 = usernameField.getText().toString();
            String arg3 = CFEMD5EncryptionAPI.getEncryptedValue(arg2,passwordField.getText().toString());
            if(usernameField.getText().toString() == null || passwordField.getText().toString() == null)
            {
                resultView.setText("Please Enter Details");
            }
            //Log.e("Password",arg3);
            new phpFetchAdapter(MainActivity.this,resultView).execute(arg1, arg2, arg3);
            //resultView.setText(result);
            //getData();
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            Intent intent = new Intent(MainActivity.this, SearchDoctors.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}