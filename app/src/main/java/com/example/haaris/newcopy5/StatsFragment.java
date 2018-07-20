package com.example.haaris.newcopy5;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsFragment extends Fragment {
    String ao5;
    ArrayAdapter<String> adapter;
    ArrayList<String> timesArray= new ArrayList<String>();
    ListView timesList;
    String time;
    int count;
    private static final String TAG = "ListDataActivity";
    ArrayList<String> revTimesArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, null);
        timesList = (ListView) view.findViewById(R.id.timesList);

        if(timesArray.size()==0) {
            populateListView(); //called on app start
        }
       //this just puts the updated array into a listview on stats page
        revTimesArray = new ArrayList<String>(timesArray);
        Collections.reverse(revTimesArray);

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),//also this??
                    android.R.layout.simple_list_item_1, revTimesArray);

        timesList.setAdapter(adapter);//needs to be in this function?
        adapter.notifyDataSetChanged();

        return view;
    }

    private void populateListView() {//called on app start but never again.
        //onDataChange is called whenever timer is stopped(time is added) and it updates the timesArray
                //by destroying the array and adding the whole new database into it

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("times");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                timesArray= new ArrayList<String>();

                for (DataSnapshot timesSnapshot: dataSnapshot.getChildren()) {//called every time frag is opened
                    Toast.makeText(getContext(), "inforloop", Toast.LENGTH_SHORT).show();
                    count++;

                    time = ""+timesSnapshot.getValue();
                    timesArray.add( "" + count + ". " + time);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


}
