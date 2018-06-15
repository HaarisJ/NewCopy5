package com.example.haaris.newcopy5;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RoomsFragment extends Fragment {
    Button pub3x3;
    Button my3x3;
    String PUBROOMS = "NONE";

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, null);

        pub3x3 = (Button) v.findViewById(R.id.pub3x3);
        pub3x3.setVisibility(View.VISIBLE);
        pub3x3.setBackgroundColor(Color.GRAY);
        my3x3 = (Button) v.findViewById(R.id.my3x3);
        my3x3.setVisibility(View.VISIBLE);
        my3x3.setBackgroundColor(Color.GRAY);

        pub3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).publicRoomJoined();
                if(PUBROOMS.equals("WE GOT ONE") ){
                    my3x3.setBackgroundColor(Color.GRAY);// set all pubroom buttons to gray
                }

            } //end onTouch
        });
        my3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//private session

                ((MainActivity)getActivity()).privateRoomJoined();
                my3x3.setBackgroundColor(Color.GREEN);
                PUBROOMS = "WE GOT ONE";

            }
        });

        return v;
    }
}
