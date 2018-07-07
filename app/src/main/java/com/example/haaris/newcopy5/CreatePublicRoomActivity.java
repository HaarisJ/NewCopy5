package com.example.haaris.newcopy5;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class CreatePublicRoomActivity extends AppCompatActivity {

    private EditText RoomNameEditText;
    private Button ScrambleTypeButton;
    private Switch PasswordProtectionSwitch;
    private Button PasswordInput;
    private Button CreateRoomButton;
    String[] puzzleTypes;

    String RoomName;
    String PuzzleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_public_room);

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
        PasswordInput = (Button) findViewById(R.id.passwordButton);
        CreateRoomButton = (Button) findViewById(R.id.createRoomButton);

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
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog PuzzleDialog = PuzzleDialogBuilder.create();
                PuzzleDialog.show();
            }
        });


        PasswordProtectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    PasswordInput.setEnabled(false);
                }
                else {
                    PasswordInput.setEnabled(true);
                }
            }
        });

        PasswordInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomName = RoomNameEditText.getText().toString();
            }
        });

    }

}
