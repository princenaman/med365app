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
 * Created by Shubham on 8/5/2015.
 */
public class FragmentIPD extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private EditText ipdDate,ipdToDate;
    private DatePickerDialog ipdDateDialog,ipdToDateDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private Spinner spinner;
    private Button button;
    private TextView ipdResult;

    public FragmentIPD() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentOPD newInstance(int sectionNumber) {
        FragmentOPD fragment = new FragmentOPD();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public FragmentIPD(int sectionNumber,Context context) {
        this.sectionNumber = sectionNumber;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_ipd, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        ipdDate = (EditText) rootView.findViewById(R.id.ipdDate);
        ipdDate.setInputType(InputType.TYPE_NULL);
        ipdDate.requestFocus();
        ipdToDate = (EditText) rootView.findViewById(R.id.ipdToDate);
        ipdToDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        spinner = (Spinner) rootView.findViewById(R.id.ipdtypeSelect);
        spinner.setOnItemSelectedListener(this);

        button = (Button) rootView.findViewById(R.id.ipdButton);
        button.setOnClickListener(this);

        ipdResult = (TextView) rootView.findViewById(R.id.ipdText);

        return rootView;
    }

    private void setDateTimeField() {
        ipdDate.setOnClickListener(this);
        ipdToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        ipdDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ipdDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        ipdToDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ipdToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == ipdDate) {
            ipdDateDialog.show();
            ipdToDate.requestFocus();
        }
        else if(view == ipdToDate) {
            ipdToDateDialog.show();
        }
        else if (view == button){
            String arg1 = "ipd";
            String arg2 = ipdToDate.getText().toString();
            String arg3 = String.valueOf(spinner.getSelectedItem());
            String arg4 = ipdDate.getText().toString();
            new phpFetchAdapter(context,ipdResult).execute(arg1,arg2,arg3,arg4);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
