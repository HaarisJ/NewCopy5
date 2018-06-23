package com.example.haaris.newcopy5;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class CreatePublicRoomActivity extends AppCompatActivity {

    private EditText RoomName;
    private Button ScrambleType;
    private Switch PasswordProtection;
    private Button PasswordInput;
    String[] puzzleTypes;

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

        RoomName = (EditText) findViewById(R.id.NameEditText);
        ScrambleType = (Button) findViewById(R.id.puzzle_type_button);
        PasswordProtection = (Switch) findViewById(R.id.passwordSwitch);
        PasswordInput = (Button) findViewById(R.id.passwordButton);

        ScrambleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder PuzzleDialogBuilder = new AlertDialog.Builder(CreatePublicRoomActivity.this);
                PuzzleDialogBuilder.setTitle("Select a puzzle type");
                PuzzleDialogBuilder.setSingleChoiceItems(puzzleTypes, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ScrambleType.setText(puzzleTypes[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog PuzzleDialog = PuzzleDialogBuilder.create();
                PuzzleDialog.show();
            }
        });
    }

}
