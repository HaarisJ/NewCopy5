package com.example.haaris.newcopy5;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Room {

    public String name;
    public String puzzle;
    public boolean passNeeded;
    public String password;
    public int members = 1;
    public String roomID;

    public Room(){

    }

    public Room(String name, String puzzle, boolean passNeeded, String password, String roomID){
        this.name = name;
        this.puzzle = puzzle;
        this.passNeeded = passNeeded;
        this.password = password;
        this.roomID = roomID;
    }
}
