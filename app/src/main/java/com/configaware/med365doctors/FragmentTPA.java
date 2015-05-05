package com.configaware.med365doctors;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by naman on 5/5/2015.
 */
public class FragmentTPA extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;

    public FragmentTPA() {
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
    public FragmentTPA(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_opd, container, false);

        /*
        TabHost tabHost = (TabHost) getActivity().findViewById(android.R.id.tabhost);

        View opd = tabHost.getTabWidget().getChildAt(0);
        ImageView tabTitleIcon = (ImageView) opd.findViewById(android.R.id.icon);
        tabTitleIcon.setImageResource(R.mipmap.ic_launcher);
        */
        TextView textView = (TextView) rootView.findViewById(R.id.opdText);
        textView.setText("TPA "+(sectionNumber+1));

        return rootView;
    }
}
