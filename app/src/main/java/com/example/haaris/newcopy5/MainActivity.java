package com.example.haaris.newcopy5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View;

//import com.example.haaris.newcopy5.R;


public class MainActivity extends AppCompatActivity {



    private FrameLayout fragmentContainer;
    Fragment StatsFragment = new StatsFragment();
    Fragment TimerFragment = new TimerFragment();
    Fragment MoreFragment = new MoreFragment();
    Fragment ChatFragment = new ChatFragment();
    Fragment RoomsFragment = new RoomsFragment();

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        ChatFragment.setArguments(bundle);
        MoreFragment.setArguments(bundle);

        hideChatNav();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, RoomsFragment).commit();//start page!!!!!!!!!!!!!!!!!

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, StatsFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, TimerFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ChatFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, MoreFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();

        bottomNav.setSelectedItemId(R.id.navigation_rooms);
    }

    public void privateRoomJoined(){//called in the rooms frag.
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
        getSupportFragmentManager().beginTransaction().show(TimerFragment).commit();
        bottomNav.setSelectedItemId(R.id.navigation_timer);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    public void publicRoomJoined(){//called in the rooms frag. NEEDS TO CONNECT WITH OTHER PEOPLE
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        if(bottomNav.findViewById(R.id.navigation_rooms).getVisibility()==View.GONE){
            bottomNav.findViewById(R.id.navigation_rooms).setVisibility(View.VISIBLE);
            bottomNav.findViewById(R.id.navigation_chat).setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().show(RoomsFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();
            bottomNav.setSelectedItemId(R.id.navigation_rooms);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
        }
        else{
            bottomNav.findViewById(R.id.navigation_rooms).setVisibility(View.GONE);
            bottomNav.findViewById(R.id.navigation_chat).setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().show(ChatFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
            bottomNav.setSelectedItemId(R.id.navigation_chat);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
        }

    }

    public void hideNav() {
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setVisibility(View.GONE);
    }

    public void showNav() {
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setVisibility(View.VISIBLE);
    }

    public void hideRoomsNav(){
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.findViewById(R.id.navigation_rooms).setVisibility(View.GONE);
    }

    public void showRoomsNav(){
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.findViewById(R.id.navigation_rooms).setVisibility(View.VISIBLE);
    }

    public void hideChatNav(){
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.findViewById(R.id.navigation_chat).setVisibility(View.GONE);
    }

    public void showChatNav(){
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.findViewById(R.id.navigation_chat).setVisibility(View.VISIBLE);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    int i = item.getItemId();
                    if (i == R.id.navigation_timer) {
                        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();

                        selectedFragment = TimerFragment;

                    } else if (i == R.id.navigation_stats) {
                        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();

                        selectedFragment = StatsFragment;

                    } else if (i == R.id.navigation_more) {
                        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
                        selectedFragment = MoreFragment;

                    } else if (i == R.id.navigation_chat) {
                        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();

                        selectedFragment = ChatFragment;

                    } else if (i == R.id.navigation_rooms) {
                        getSupportFragmentManager().beginTransaction().hide(TimerFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(StatsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(MoreFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(RoomsFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(ChatFragment).commit();

                        selectedFragment = RoomsFragment;

                    }

                    getSupportFragmentManager().beginTransaction().show(selectedFragment).commit();

                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

}
