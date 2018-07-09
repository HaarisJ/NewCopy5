package com.example.haaris.newcopy5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RoomsFragment extends Fragment {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef;
    DatabaseReference mUserRef;

    Button my3x3;
    String PUBROOMS = "NONE";
    GridView publicGridView;

    ArrayList<Room> roomsList = new ArrayList<Room>();
    ArrayList<String> sessionsList = new ArrayList<String>();
    ArrayList<Integer> sessionIconsList = new ArrayList<Integer>();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, null);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        my3x3 = (Button) v.findViewById(R.id.my3x3);
        my3x3.setVisibility(View.VISIBLE);
        my3x3.setBackgroundColor(Color.GRAY);

        publicGridView = v.findViewById(R.id.pubRoomsGrid);

        my3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//private session

                ((MainActivity)getActivity()).privateRoomJoined();
                my3x3.setBackgroundColor(Color.GREEN);
                PUBROOMS = "WE GOT ONE";

            }
        });

        mUserRef = mDatabase.getReference();
        mRef = mDatabase.getReference().child("rooms");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                sessionsList.clear();
                sessionIconsList.clear();

                sessionsList.add("Create Room");
                sessionIconsList.add(R.drawable.ic_add_box_black_200dp);

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    roomsList.add(ds.getValue(Room.class));
                    sessionsList.add(ds.getValue(Room.class).name);
                    sessionIconsList.add(getIcon(ds.getValue(Room.class).puzzle));
                }

                PublicGridAdapter adapter = new PublicGridAdapter(getContext(), sessionIconsList, sessionsList);
                publicGridView.setAdapter(adapter);
                publicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0){
                            startActivity(new Intent(getContext(), CreatePublicRoomActivity.class));
                        }

                        else {
                            final String roomID = roomsList.get(i-1).roomID;
                            if (roomsList.get(i-1).passNeeded){
                                final String correctPassword = roomsList.get(i-1).password;
                                AlertDialog.Builder passwordAlertBuilder = new AlertDialog.Builder(getContext());
                                final EditText edittext = new EditText(getContext());
                                //passwordAlertBuilder.setMessage("Enter Your Message");
                                passwordAlertBuilder.setTitle("Enter room password");

                                passwordAlertBuilder.setView(edittext);

                                passwordAlertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //What ever you want to do with the value
                                        String passwordGuess = edittext.getText().toString();
                                        if (passwordGuess.equals(correctPassword)){
                                            mUserRef.child("users").child(currentUser.getUid()).child("currentRoom").setValue(roomID);
                                            ((MainActivity) getActivity()).publicRoomJoined();
                                        }
                                        else {
                                            Toast.makeText(getActivity(),"Incorrect password", Toast.LENGTH_SHORT).show();
                                        }
                                        ((MainActivity) getActivity()).fullscreen();
                                    }
                                });

                                passwordAlertBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // what ever you want to do with No option.
                                        ((MainActivity) getActivity()).fullscreen();
                                    }
                                });
                                AlertDialog passwordAlert = passwordAlertBuilder.create();
//                                passwordAlert.getWindow().setFlags(
//                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                                passwordAlert.show();
                            }
                            else {
                                mUserRef.child("users").child(currentUser.getUid()).child("currentRoom").setValue(roomID);
                                ((MainActivity) getActivity()).publicRoomJoined();
                            }
                            if(PUBROOMS.equals("WE GOT ONE") ){
                                my3x3.setBackgroundColor(Color.GRAY);// set all pubroom buttons to gray
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    public int getIcon(String puzzleType){
        switch (puzzleType){
            case "2x2":
                return R.drawable.temp_cube;
            case "3x3":
                return R.drawable.temp_cube;
            case "4x4":
                return R.drawable.temp_cube;
            case "5x5":
                return R.drawable.temp_cube;
            case "6x6":
                return R.drawable.temp_cube;
            case "7x7":
                return R.drawable.temp_cube;
            case "Megaminx":
                return R.drawable.temp_cube;
            case "Pyraminx":
                return R.drawable.temp_cube;
            case "Skewb":
                return R.drawable.temp_cube;
            case "Clock":
                return R.drawable.temp_cube;
            case "Square One":
                return R.drawable.temp_cube;
            default:
                return 0;
        }
    }


}
