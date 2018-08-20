package com.example.haaris.newcopy5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RVAdapter";

    private ArrayList<String> mData;
    private LayoutInflater mInflater;
//    private OnClickListener mClickListener = new MyOnClickListener();
    private Context context;
//    private OnItemClicked onClick;

//    public interface OnItemClicked{
//        void onItemClick(int position);
//    }

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
//        ViewHolder vHolder = new ViewHolder(view);
//        vHolder.tvUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//Log.d(TAG,"we were clicked");
//            }
//        });
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String userAndAvgs = mData.get(position);
//        Log.d(TAG, "inside bindviewholder"+ position);
        if(position%5 ==0){
            holder.tvUsers.setText(userAndAvgs);
        }
//        else if(position%5 ==1) {
//            holder.tvAvgs.setText("BestAo12: " + userAndAvgs);
//        }
//        else if(position%5 ==2) {
//            holder.tvAvgs.setText("BestAo5: " + userAndAvgs);
//        }
        else if(position%5 ==3){
            holder.tvAvgs.setText("Ao5: "+userAndAvgs);
        }
        else if(position%5 ==4){
            holder.tvAvgs.setText("Ao12: "+userAndAvgs);
        }
//        holder.tvUsers.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                onClick.onItemClick(position);
//            }
//        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsers;
        TextView tvAvgs;


        ViewHolder(View itemView) {
            super(itemView);
            tvUsers = itemView.findViewById(R.id.tvUsers);
            tvAvgs = itemView.findViewById(R.id.tvAvgs);
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }
//    public void setOnClick(OnItemClicked onClick){
//        this.onClick=onClick;
//    }
    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
////        this.mClickListener = itemClickListener;
//    }

    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        //void onItemClick(View view, int position);
//    }
}