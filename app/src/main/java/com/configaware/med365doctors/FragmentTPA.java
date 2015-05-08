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
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by naman on 8/5/2015.
 */
public class FragmentTPA extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private EditText tpaDate,tpaToDate;
    private DatePickerDialog tpaDateDialog,tpaToDateDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private Spinner spinner;
    private Button button;
    private TextView tpaResult;

    public FragmentTPA() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentTPA newInstance(int sectionNumber) {
        FragmentTPA fragment = new FragmentTPA();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public FragmentTPA(int sectionNumber,Context context) {
        this.sectionNumber = sectionNumber;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_tpa, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tpaDate = (EditText) rootView.findViewById(R.id.tpaDate);
        tpaDate.setInputType(InputType.TYPE_NULL);
        tpaDate.requestFocus();
        tpaToDate = (EditText) rootView.findViewById(R.id.tpaToDate);
        tpaToDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        spinner = (Spinner) rootView.findViewById(R.id.tpatypeSelect);
        spinner.setOnItemSelectedListener(this);

        button = (Button) rootView.findViewById(R.id.tpaButton);
        button.setOnClickListener(this);

        tpaResult = (TextView) rootView.findViewById(R.id.tpaText);

        return rootView;
    }

    private void setDateTimeField() {
        tpaDate.setOnClickListener(this);
        tpaToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        tpaDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tpaDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        tpaToDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tpaToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == tpaDate) {
            tpaDateDialog.show();
            tpaToDate.requestFocus();
        }
        else if(view == tpaToDate) {
            tpaToDateDialog.show();
        }
        else if (view == button){
            String arg1 = "tpa";
            String arg2 = tpaToDate.getText().toString();
            String arg3 = String.valueOf(spinner.getSelectedItem());
            String arg4 = tpaDate.getText().toString();
            new phpFetchAdapter(context,tpaResult).execute(arg1,arg2,arg3,arg4);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
