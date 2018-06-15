package com.example.haaris.newcopy5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gnehzr.tnoodle.scrambles.PuzzleStateAndGenerator;

import java.util.Random;

import puzzle.ClockPuzzle;

public class MoreFragment extends Fragment {

    TextView ScrambleTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, null);
        Random k = new Random();
        ClockPuzzle cubeState = new ClockPuzzle();
        PuzzleStateAndGenerator solver = cubeState.generateRandomMoves(k);
        String scramble = solver.generator;
        ScrambleTextView = (TextView) v.findViewById(R.id.scrambleTextView);
        ScrambleTextView.setVisibility(View.VISIBLE);
        ScrambleTextView.setText(""+scramble+"");
        return v;
    }

}

