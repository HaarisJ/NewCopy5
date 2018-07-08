package com.example.haaris.newcopy5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatFragment extends Fragment {
    Button leaveRoomBtn;
    TextView usernameTxt;
    String username;
    String roomName;
    private Button sendBtn;
    private TextView chatTxt;
    private EditText inputMsgTxt;
    private DatabaseReference root;
    private String tempKey, chatMsg, chatUsername;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, null);

        leaveRoomBtn = v.findViewById(R.id.leaveRoomBtn);
        sendBtn = v.findViewById(R.id.sendBtn);
        chatTxt = v.findViewById(R.id.chatTxt);
        inputMsgTxt = v.findViewById(R.id.inputMsgTxt);

        leaveRoomBtn.setVisibility(View.VISIBLE);
        leaveRoomBtn.setBackgroundColor(Color.GRAY);

        leaveRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).publicRoomJoined();
            }
        });


//        username = getArguments().getString("username");
//        root = FirebaseDatabase.getInstance().getReference().child(roomName);

//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String,Object> map = new HashMap<String,Object>();
//                tempKey = root.push().getKey();
//                root.updateChildren(map);
//
//                DatabaseReference msgRoot = root.child(tempKey);
//                Map<String,Object> map2 = new HashMap<String,Object>();
//                map2.put("name", username);
//                map2.put("msg", inputMsgTxt.getText().toString());
//
//                msgRoot.updateChildren(map2);
//
//            }
//        });

//        root.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                appendChatConv(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                appendChatConv(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return v;
    }

//    private void appendChatConv(DataSnapshot dataSnapshot) {
//        Iterator i = dataSnapshot.getChildren().iterator();
//        while(i.hasNext()){
//            chatMsg = (String) ((DataSnapshot)i.next()).getValue();
//            chatUsername = (String) ((DataSnapshot)i.next()).getValue();
//
//            chatTxt.append(chatUsername + ": " + chatMsg + " \n");
//        }
//    }
//
//
}
