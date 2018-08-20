package com.example.haaris.newcopy5;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.gnehzr.tnoodle.svglite.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StatsFragment extends Fragment {
    String ao5;
    ArrayAdapter<String> timesAdapter;
    ArrayList<String> timesArray= new ArrayList<String>();
    ArrayList<String> uAndArray;

    ArrayList<String> membersArray;
    TextView txtAo5;
    ListView timesList;
    RecyclerView usersAndAvgs;
    String time, UID,roomID, member;
    String currUID = getCurrUID();
    int count;
    private static final String TAG = "ListDataActivity";
    ArrayList<String> revTimesArray;
    private LinearLayoutManager linearLayoutManager;
    MyRecyclerViewAdapter uAndAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, null);
        timesList = (ListView) view.findViewById(R.id.timesList);
        usersAndAvgs= (RecyclerView) view.findViewById(R.id.userAndAvgs);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        usersAndAvgs.setLayoutManager(linearLayoutManager);

        if(timesArray.size()==0) {
            populateListView(); //called on app start
        }
       //this just puts the updated array into a listview on stats page
        revTimesArray = new ArrayList<String>(timesArray);
        Collections.reverse(revTimesArray);

        membersArray= new ArrayList<String>();

        uAndArray= new ArrayList<String>();
        timesAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),//also this??
                    android.R.layout.simple_list_item_1, revTimesArray);

        timesList.setAdapter(timesAdapter);//needs to be in this function?
//        timesAdapter.notifyDataSetChanged();

        membersArray.add(currUID);//user will always be first in the list

        getRoomId();//what room is the user in
        if(roomID!=null){
            getRoomMembersArray(roomID);
        }
        usersAndAvgs.addItemDecoration(new VerticalSpaceItemDecoration(-90));
        return view;
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(getContext(), "You clicked " + uAndAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }

    public void getRoomMembersArray(String roomID) {
        DatabaseReference RoomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomID).child("membersList");
        RoomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange: "+ dataSnapshot.toString());
                for(DataSnapshot timesSnapshot: dataSnapshot.getChildren()) {
                    member = timesSnapshot.getValue(String.class);
//                    Log.d(TAG, "onDataChange: "+member);
                    if(!(member.equals(currUID))) {
                        membersArray.add(member);//so that we skip the already add currUser
                    }

                }
                for(int i=0; i<membersArray.size();i++) {  //for every user in the room
                    UID = membersArray.get(i);
                    //find their username from users{name}(add to array)
                    findUsername(UID);//adds username to array
                    //populate avgs and times (add to array)
                    addAvgs(UID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getRoomId(){
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("users").child(currUID);
        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG,dataSnapshot.child("name").getValue().toString());
                roomID = ""+dataSnapshot.child("currentRoom").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getContext(),roomID, Toast.LENGTH_SHORT).show();
    }
    public String getCurrUID(){
        FirebaseUser currentUID = FirebaseAuth.getInstance().getCurrentUser();
        return currentUID.getUid();
    }
    public void addAvgs(String UID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users avgs").child(UID);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "inside addavgs"+dataSnapshot.toString());

                for(DataSnapshot timesSnapshot: dataSnapshot.getChildren()) {
                    String oneOfTheAvgs = timesSnapshot.getValue(String.class);
                    uAndArray.add(oneOfTheAvgs);//adds every avg to the array////UPDATE THIS SO IT GOES TO SUBTEXT
                }
                Log.d(TAG,"uandarray"+uAndArray.toString());
                uAndAdapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), uAndArray);
//                uAndAdapter.setOnClick(this);
                usersAndAvgs.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                usersAndAvgs.setAdapter(uAndAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void findUsername(String UID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
//        Toast.makeText(getContext(),"findusername", Toast.LENGTH_SHORT).show();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,dataSnapshot.child("name").getValue().toString());
//                Toast.makeText(getContext(),"inondatachange", Toast.LENGTH_SHORT).show();

                String userName = ""+dataSnapshot.child("name").getValue();
                uAndArray.add(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateListView() {//called on app start but never again.
        //onDataChange is called whenever timer is stopped(time is added) and it updates the timesArray
                //by destroying the array and adding the whole new database into it
        //getUID
        String UID = "SQQQL9bh1UZrVf4RgjJfAK5jeUG2";
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("times").child(UID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                timesArray= new ArrayList<String>();

                for (DataSnapshot timesSnapshot: dataSnapshot.getChildren()) {//called every time frag is opened
                    //Toast.makeText(getContext(), "inforloop", Toast.LENGTH_SHORT).show();
                    count++;

                    time = timesSnapshot.getValue(String.class);
                    timesArray.add( "" + count + ". " + time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
//            outRect.top = verticalSpaceHeight;

        }
    }


}
