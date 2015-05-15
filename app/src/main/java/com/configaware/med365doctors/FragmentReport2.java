package com.configaware.med365doctors;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shubham on 5/5/2015.
 */
public class FragmentReport2 extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private EditText opdDate,opdToDate;
    private DatePickerDialog opdDateDialog,opdToDateDialog;
    private SimpleDateFormat dateFormatter;
    private ListView listView;
    private Context context;
    private Spinner spinner;
    private Button button;
    private TextView opdResult;

    public FragmentReport2() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentReport2 newInstance(int sectionNumber) {
        FragmentReport2 fragment = new FragmentReport2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public FragmentReport2(int sectionNumber,Context context) {
        this.sectionNumber = sectionNumber;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_report2, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        opdDate = (EditText) rootView.findViewById(R.id.opdDate2);
        opdDate.setInputType(InputType.TYPE_NULL);
        opdDate.requestFocus();
        opdToDate = (EditText) rootView.findViewById(R.id.opdToDate2);
        opdToDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        spinner = (Spinner) rootView.findViewById(R.id.typeSelect2);
        spinner.setOnItemSelectedListener(this);

        button = (Button) rootView.findViewById(R.id.opdButton2);
        button.setOnClickListener(this);

        opdResult = (TextView) rootView.findViewById(R.id.opdText2);
        opdResult.setMovementMethod(new ScrollingMovementMethod());

        listView = (ListView) rootView.findViewById(R.id.collectionList2);
        return rootView;
    }

    private void setDateTimeField() {
        opdDate.setOnClickListener(this);
        opdToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        opdDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                opdDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        opdToDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                opdToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == opdDate) {
            opdDateDialog.show();
            opdToDate.requestFocus();
        }
        else if(view == opdToDate) {
            opdToDateDialog.show();
        }
        else if (view == button){
            String arg1 = "collection";
            String arg2 = opdDate.getText().toString();
            String arg3 = opdToDate.getText().toString();
            String arg4 = "";
            new reportDayAdapter(context,opdResult,listView).execute(arg1,arg2,arg3,arg4);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
