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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by naman on 5/5/2015.
 */
public class FragmentOPD extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private EditText opdDate;
    private DatePickerDialog opdDateDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;

    public FragmentOPD() {
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
    public FragmentOPD(int sectionNumber,Context context) {
        this.sectionNumber = sectionNumber;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_opd, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.opdText);
        textView.setText("OPD "+(sectionNumber+1));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        opdDate = (EditText) rootView.findViewById(R.id.opdDate);
        opdDate.setInputType(InputType.TYPE_NULL);
        opdDate.requestFocus();
        setDateTimeField();

        return rootView;
    }

    private void setDateTimeField() {
        opdDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        opdDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                opdDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == opdDate) {
            opdDateDialog.show();
        }
    }
}
