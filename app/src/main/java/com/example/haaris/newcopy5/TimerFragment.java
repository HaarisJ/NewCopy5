package com.example.haaris.newcopy5;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.gnehzr.tnoodle.scrambles.PuzzleStateAndGenerator;

import java.util.ArrayList;
import java.util.Random;

import puzzle.ClockPuzzle;
import puzzle.CubePuzzle;
import puzzle.FourByFourCubePuzzle;
import puzzle.MegaminxPuzzle;
import puzzle.PyraminxPuzzle;
import puzzle.SkewbPuzzle;
import puzzle.SquareOnePuzzle;
import puzzle.ThreeByThreeCubePuzzle;
import puzzle.TwoByTwoCubePuzzle;

import static android.content.ContentValues.TAG;

public class TimerFragment extends Fragment {
    Button startBtn,stopBtn, ao5Btn,ao12Btn;

    private static final String TAG = "MainActivity";

    TextView result,ao5,ao12;
    Handler customHandler = new Handler();
    ListView timeList;
    ArrayList<String> strArr;
    ArrayAdapter<String> adapter;
    ConstraintLayout mLayout;
    String col;
    TextView ScrambleTextView;
    DatabaseReference databaseTimes;



    long[] recent5= new long[] {0,0,0,0,0};
    long[] recent12= new long[] {0,0,0,0,0,0,0,0,0,0,0,0};
    int indexNum5=0;
    int indexNum12=0;
    long avg5;
    long avg12;
    long max;
    long min;
    long sum;
    int solveNum=0;
    long startTime = 0L,timeMs = 0L, timeSwapBuff = 0L, updateTime = 0L;
    String scrambleType = "777";


    public void AddData(String newEntry) {
        String id = databaseTimes.push().getKey();
        databaseTimes.child(id).setValue(newEntry);

    }

    Runnable updateTimerThread = new Runnable(){
        @Override
        public void run(){
            mLayout.setBackgroundColor(Color.WHITE);
            col="white";
            timeMs = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff+timeMs;
            int secs = (int)(updateTime/1000);
            int mins = secs/60;
            secs%= 60;
            int ms = (int)((updateTime/10)%100);
            if(mins==0) {
                result.setText(String.format("%2d",secs)+"."+
                        String.format("%02d",ms));
            }
            else{
                result.setText("" + mins + ":" + String.format("%02d", secs) + "." +
                        String.format("%02d", ms));
            }
            customHandler.postDelayed(this,0);
        }
    };

    private Runnable holdTask = new Runnable() {
        @Override
        public void run() {
            mLayout.setBackgroundColor(Color.GREEN);
            col="grn";

            timeMs = SystemClock.uptimeMillis() - startTime;
            int secs = (int)(timeMs/1000);
            if(secs<16){
                result.setText(""+String.format("%02d",15-secs));
            }
            else{
                result.setText("DNF");
            }
            customHandler.postDelayed(this,0);
    }//end run

    };// end Runnable

    private Runnable updateAo5 = new Runnable() {
        @Override
        public void run() {
            recent5[(indexNum5%5)] = updateTime;
            indexNum5++;
            sum =0;

            if(recent5[4]!=0){
                max= recent5[0];
                min = recent5[0];
                for(int i = 1;i<=4;i++){
                    if(recent5[i]>max){
                        max = recent5[i];
                    }
                    else if(recent5[i]<min){
                        min =recent5[i];
                    }//they equal???? do nothing
                }
                for(int i =0;i<=4;i++){
                    sum = sum +recent5[i];
                }
                sum = sum-max-min;
                avg5 = sum/3;
                ao5.setText("Ao5: "+avg5/1000+"."+(avg5/10)%100);
            }
        }
    };

    private Runnable updateAo12 = new Runnable() {
        @Override
        public void run() {
            recent12[(indexNum12%12)] = updateTime;
            indexNum12++;
            sum=0;

            if(recent12[11]!=0){
                max= recent12[0];
                min = recent12[0];
                for(int i = 1;i<=11;i++){
                    if(recent12[i]>max){
                        max = recent12[i];
                    }
                    else if(recent12[i]<min){
                        min =recent12[i];
                    }//they equal???? do nothing
                }
                for(int i =0;i<=11;i++){
                    sum = sum +recent12[i];
                }
                sum = sum-max-min;
                avg12 = sum/10;
                ao12.setText("Ao12: "+avg12/1000+"."+(avg12/10)%100);
            }
        }
    };

    private Runnable screenClear = new Runnable() {
        @Override
        public void run() {
            timeList.setVisibility(View.INVISIBLE);
            ao5.setVisibility(View.INVISIBLE);
            ao12.setVisibility(View.INVISIBLE);
            ao5Btn.setVisibility(View.GONE);
            ao12Btn.setVisibility(View.GONE);
            ScrambleTextView.setVisibility(View.GONE);
            ((MainActivity)getActivity()).hideNav();
        }
    };
    private Runnable screenShow = new Runnable() {
        @Override
        public void run() {
            timeList.setVisibility(View.VISIBLE);
            ao5.setVisibility(View.VISIBLE);
            ao12.setVisibility(View.VISIBLE);
            ao5Btn.setVisibility(View.VISIBLE);
            ao12Btn.setVisibility(View.VISIBLE);
            ScrambleTextView.setVisibility(View.VISIBLE);
            ((MainActivity)getActivity()).showNav();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseTimes = FirebaseDatabase.getInstance().getReference("times");


        if(savedInstanceState != null) {
            Log.i(TAG, "got notnull ");
            String savedResult = savedInstanceState.getString("resultkey");
            result.setText(savedResult);
        }
        else{
            Log.i(TAG, "GOT null:) ");
            //result.setText("0.00");

        }

        View v = inflater.inflate(R.layout.fragment_timer, null);

        ScrambleTextView = (TextView) v.findViewById(R.id.scrambleTextView);
        ScrambleTextView.setVisibility(View.VISIBLE);
        ScrambleTextView.setText("Generating scramble...");
        //ScrambleTextView.setText(""+scramble+"");
        cubeSolver scramble = new cubeSolver();
        scramble.execute(scrambleType);

        startBtn = (Button) v.findViewById(R.id.startBtn);
        startBtn.setVisibility(View.VISIBLE);
        startBtn.setBackgroundColor(Color.TRANSPARENT);

        stopBtn = (Button) v.findViewById(R.id.stopBtn);
        stopBtn.setVisibility(View.GONE);
        stopBtn.setBackgroundColor(Color.TRANSPARENT);

        ao5Btn = (Button) v.findViewById(R.id.ao5Btn);
        ao5Btn.setVisibility(View.VISIBLE);
        ao5Btn.setBackgroundColor(Color.TRANSPARENT);

        ao12Btn = (Button) v.findViewById(R.id.ao12Btn);
        ao12Btn.setVisibility(View.VISIBLE);
        ao12Btn.setBackgroundColor(Color.TRANSPARENT);


        result = (TextView) v.findViewById(R.id.result);
        ao5 = (TextView) v.findViewById(R.id.ao5);
        ao12 = (TextView)v.findViewById(R.id.ao12);

        timeList = (ListView) v.findViewById(R.id.timeList);
        strArr = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,strArr);
        timeList.setAdapter(adapter);

        mLayout = (ConstraintLayout) v.findViewById(R.id.mainLayout);

        startBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    customHandler.postDelayed(screenClear,0);
                    mLayout.setBackgroundColor(Color.RED);
                    col= "red";
                    startTime=SystemClock.uptimeMillis();
                    customHandler.postDelayed(holdTask,00);

                } else if (action == MotionEvent.ACTION_UP) {
                    if(col == "red") {
                        customHandler.removeCallbacks(holdTask); //THEY DIDNT HOLD LONG ENOUGH
                        mLayout.setBackgroundColor(Color.WHITE);
                        col="white";
                        customHandler.postDelayed(screenShow,0);

                        return false;
                    }
                    if(result.getText() == "DNF"){
                        customHandler.removeCallbacks(holdTask); //inspection too long
                        mLayout.setBackgroundColor(Color.WHITE);
                        customHandler.postDelayed(screenShow,0);
                        col="white";
                        return false;
                    }
                    else {
                        customHandler.removeCallbacks(holdTask);
                        startTime = SystemClock.uptimeMillis();

                        customHandler.postDelayed(updateTimerThread, 0);
                        startBtn.setVisibility(View.GONE);
                        stopBtn.setVisibility(View.VISIBLE);
                    }
                }//end else
                return false;
            } //end onTouch
        }); //end b my button

        stopBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    customHandler.removeCallbacks(updateTimerThread);
                    customHandler.removeCallbacks(holdTask);
                    customHandler.postDelayed(screenShow, 0);

                    AddData(""+result.getText());

                    solveNum++;
                    if (solveNum < 10) {
                        strArr.add(0, "  " + solveNum + ". " + result.getText().toString());
                    } else if (solveNum < 100) {
                        strArr.add(0, " " + solveNum + ". " + result.getText().toString());
                    } else if (solveNum < 1000) {
                        strArr.add(0, "" + solveNum + ". " + result.getText().toString());

                    }
                    adapter.notifyDataSetChanged();

                    customHandler.postDelayed(updateAo5, 0);
                    customHandler.postDelayed(updateAo12, 0);

                    stopBtn.setVisibility(View.GONE);//back to original state
                    startBtn.setVisibility(View.VISIBLE);
                    mLayout.setBackgroundColor(Color.WHITE);

                    ScrambleTextView.setText("Generating scramble...");
                    cubeSolver scramble = new cubeSolver();
                    scramble.execute(scrambleType);
                    //String scramble = cubeSolver(scrambleType);
                    //ScrambleTextView.setText(""+scramble+"");
                }
                return false;
            }
        });

        return v;

    }



    private class cubeSolver extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... scrambles) {
            String scrambleType = scrambles[0];
            Random k = new Random();
            PuzzleStateAndGenerator solver;
            publishProgress("cheese");
            switch (scrambleType) {
                case "333": ThreeByThreeCubePuzzle cubeState = new ThreeByThreeCubePuzzle();
                    solver = cubeState.generateRandomMoves(k);
                    break;
                case "444": FourByFourCubePuzzle cubeState4 = new FourByFourCubePuzzle();
                    solver = cubeState4.generateRandomMoves(k);
                    break;
                case "222": TwoByTwoCubePuzzle cubeState2 = new TwoByTwoCubePuzzle();
                    solver = cubeState2.generateRandomMoves(k);
                    break;
                case "pyra": PyraminxPuzzle cubeStateP = new PyraminxPuzzle();
                    solver = cubeStateP.generateRandomMoves(k);
                    break;
                case "squan": SquareOnePuzzle cubeStateS1 = new SquareOnePuzzle();
                    solver = cubeStateS1.generateRandomMoves(k);
                    break;
                case "skewb": SkewbPuzzle cubeStateSk = new SkewbPuzzle();
                    solver = cubeStateSk.generateRandomMoves(k);
                    break;
                case "mega": MegaminxPuzzle cubeStateM = new MegaminxPuzzle();
                    solver = cubeStateM.generateRandomMoves(k);
                    break;
                case "clock": ClockPuzzle cubeStateC = new ClockPuzzle();
                    solver = cubeStateC.generateRandomMoves(k);
                    break;
                case "555": CubePuzzle cubeState5 = new CubePuzzle(5);
                    solver = cubeState5.generateRandomMoves(k);
                    break;
                case "666": CubePuzzle cubeState6 = new CubePuzzle(6);
                    solver = cubeState6.generateRandomMoves(k);
                    break;
                case "777": CubePuzzle cubeState7 = new CubePuzzle(7);
                    solver = cubeState7.generateRandomMoves(k);
                    break;
                default:
                    solver = null;
                    break;
            }

            String finalResult = solver.generator;
            return finalResult;

        }

        @Override
        protected void onPostExecute(String finalResult) {
            super.onPostExecute(finalResult);
            ScrambleTextView.setText(""+finalResult+"");
        }
    }


   /* @Override
    public void onPause() {
        super.onPause();
        String persistentVariable = result.getText().toString();

        getArguments().putString(PERSISTENT_VAR_BUNDLE, persistentVariable);
    }

*/

}

