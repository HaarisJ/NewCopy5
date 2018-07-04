package com.example.haaris.newcopy5;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsFragment extends Fragment {
    TextView currAO5;
    String ao5;

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    private DatabaseReference databaseReference;


    private static final String TAG = "ListDataActivity";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, null);


        mListView = (ListView) v.findViewById(R.id.listView);
//        Log.i(TAG,"got just before populate list");

        populateListView();
        //Log.i(TAG,"got just after populate list");

        return v;
    }

    private void populateListView() {
        final ArrayList<String> listData = new ArrayList<>();
        databaseReference.child("times").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot timesSnapshot: dataSnapshot.getChildren()) {
                    listData.add(""+timesSnapshot.getKey());
                }

                Log.d(TAG, "this many times: "+listData.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Error trying to get classified ads for"
                         +databaseError);
                Toast.makeText(getActivity(),
                        "Error trying to get classified ads for ",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }

//
//    private void populateListView() {
//        Log.d(TAG, "populateListView: Displaying data in the ListView.");
//
//        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getActivity());
//
//        //get the data and append to a list
//        Cursor data = mDatabaseHelper.getData();
//        ArrayList<String> listData = new ArrayList<>();
//        int count= 1;
//        while(data.moveToNext()){
//            //get the value from the database in column 1
//            //then add it to the ArrayList
//            listData.add(""+count+". "+ data.getString(1));
//            count++;
//
//        }

//        ArrayList<String> revListData = new ArrayList<String>(listData);
//        Collections.reverse(revListData);
//
//        //create the list adapter and set the adapter
//        ListAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
//                android.R.layout.simple_list_item_1, listData);
//        mListView.setAdapter(adapter);
//
//

        //set an onItemClickListener to the ListView
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You Clicked on " + name);
//
//                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
//                int itemID = -1;
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
//                if(itemID > -1){
//                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
//                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
//                    editScreenIntent.putExtra("id",itemID);
//                    editScreenIntent.putExtra("name",name);
//                    startActivity(editScreenIntent);
//                }
//                else{
//                    System.out.println("it worked!!");
//                }
//            }
//        });


}
