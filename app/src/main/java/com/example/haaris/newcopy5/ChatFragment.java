package com.example.haaris.newcopy5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChatFragment extends Fragment {
    Button leaveRoomBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, null);

        leaveRoomBtn = (Button) v.findViewById(R.id.leaveRoomBtn);

        leaveRoomBtn.setVisibility(View.VISIBLE);
        leaveRoomBtn.setBackgroundColor(Color.GRAY);

        leaveRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).publicRoomJoined();

            }
        });

        return v;

    }
}
