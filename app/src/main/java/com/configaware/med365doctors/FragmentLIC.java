package com.configaware.med365doctors;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by naman on 5/5/2015.
 */
public class FragmentLIC extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private EditText licDate,licToDate;
    private DatePickerDialog licDateDialog,licToDateDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private Spinner spinner;
    private Button button;
    private TextView licResult;

    public FragmentLIC() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentLIC newInstance(int sectionNumber) {
        FragmentLIC fragment = new FragmentLIC();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public FragmentLIC(int sectionNumber,Context context) {
        this.sectionNumber = sectionNumber;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_lic, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        licDate = (EditText) rootView.findViewById(R.id.licDate);
        licDate.setInputType(InputType.TYPE_NULL);
        licDate.requestFocus();
        licToDate = (EditText) rootView.findViewById(R.id.licToDate);
        licToDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        spinner = (Spinner) rootView.findViewById(R.id.lictypeSelect);
        spinner.setOnItemSelectedListener(this);

        button = (Button) rootView.findViewById(R.id.licButton);
        button.setOnClickListener(this);

        licResult = (TextView) rootView.findViewById(R.id.licText);

        return rootView;
    }

    private void setDateTimeField() {
        licDate.setOnClickListener(this);
        licToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        licDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                licDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        licToDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                licToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == licDate) {
            licDateDialog.show();
            licToDate.requestFocus();
        }
        else if(view == licToDate) {
            licToDateDialog.show();
        }
        else if (view == button){
            String arg1 = "lic";
            String arg2 = licToDate.getText().toString();
            String arg3 = String.valueOf(spinner.getSelectedItem());
            String arg4 = licDate.getText().toString();
            new phpFetchAdapter(context,licResult).execute(arg1,arg2,arg3,arg4);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
