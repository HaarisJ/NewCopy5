package com.example.haaris.newcopy5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class RoomsFragment extends Fragment {
    Button my3x3;
    String PUBROOMS = "NONE";
    GridView publicGridView;

    String sessions[] = {"Create Room", "2", "3", "4", "5"};

    int sessionIcons[] = {R.drawable.ic_add_box_black_200dp, R.drawable.temp_cube, R.drawable.temp_cube, R.drawable.temp_cube, R.drawable.temp_cube};

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, null);

        my3x3 = (Button) v.findViewById(R.id.my3x3);
        my3x3.setVisibility(View.VISIBLE);
        my3x3.setBackgroundColor(Color.GRAY);

        publicGridView = v.findViewById(R.id.pubRoomsGrid);
        PublicGridAdapter adapter = new PublicGridAdapter(getContext(), sessionIcons, sessions);
        publicGridView.setAdapter(adapter);
        publicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                   startActivity(new Intent(getContext(), CreatePublicRoomActivity.class));
                }

                else {
                    ((MainActivity)getActivity()).publicRoomJoined();
                }
            }
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
