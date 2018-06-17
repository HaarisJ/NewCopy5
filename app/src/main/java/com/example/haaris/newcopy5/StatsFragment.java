package com.example.haaris.newcopy5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {
    TextView currAO5;
    String ao5;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, null);

        currAO5 = (TextView) v.findViewById(R.id.currAO5);

        ao5 = getArguments().getString("AO5");
        currAO5.setText(""+ao5);

        return v;
    }
}
