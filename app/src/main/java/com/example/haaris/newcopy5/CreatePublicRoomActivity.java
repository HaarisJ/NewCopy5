package com.example.haaris.newcopy5;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePublicRoomActivity extends AppCompatActivity {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    private EditText RoomNameEditText;
    private Button ScrambleTypeButton;
    private Switch PasswordProtectionSwitch;
    private EditText PasswordInput;
    private Button CreateRoomButton;
    private Button CancelButton;
    String[] puzzleTypes;


    String RoomName;
    String PuzzleType;
    boolean passNeeded = false;
    String password = null;
    boolean scrambleSelected = false;
    String roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_public_room);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        puzzleTypes = getResources().getStringArray(R.array.puzzle_types);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        RoomNameEditText = (EditText) findViewById(R.id.NameEditText);
        ScrambleTypeButton = (Button) findViewById(R.id.puzzleTypeButton);
        PasswordProtectionSwitch = (Switch) findViewById(R.id.passwordSwitch);
        PasswordInput = (EditText) findViewById(R.id.passwordEntry);
        CreateRoomButton = (Button) findViewById(R.id.createRoomButton);
        CancelButton = (Button) findViewById(R.id.cancelButton);

        PasswordInput.setEnabled(false);

        ScrambleTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder PuzzleDialogBuilder = new AlertDialog.Builder(CreatePublicRoomActivity.this);
                PuzzleDialogBuilder.setTitle("Select a puzzle type");
                PuzzleDialogBuilder.setSingleChoiceItems(puzzleTypes, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PuzzleType = puzzleTypes[i];
                        ScrambleTypeButton.setText(puzzleTypes[i]);
                        scrambleSelected = true;
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog PuzzleDialog = PuzzleDialogBuilder.create();
                PuzzleDialog.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                PuzzleDialog.show();
            }
        });


        PasswordProtectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    PasswordInput.setEnabled(true);
                    passNeeded = true;
                }
                else {
                    PasswordInput.setEnabled(false);
                    passNeeded = false;
                }
            }
        });

        CreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!scrambleSelected){
                    Toast.makeText(CreatePublicRoomActivity.this, "You did not select a puzzle type", Toast.LENGTH_SHORT).show();
                    return;
                }

                RoomName = RoomNameEditText.getText().toString();
                if (RoomName.isEmpty()){
                    Toast.makeText(CreatePublicRoomActivity.this, "You did not give your room a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                password = PasswordInput.getText().toString();
                if (passNeeded && TextUtils.isEmpty(password)){
                    Toast.makeText(CreatePublicRoomActivity.this, "Your private room does not have password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                roomID = Long.toString(System.currentTimeMillis());
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Room room = new Room(RoomName, PuzzleType, passNeeded, password, roomID, currentUser.getUid());
                mRef.child("rooms").child(roomID).setValue(room);

                Intent returnIntent = new Intent();

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
