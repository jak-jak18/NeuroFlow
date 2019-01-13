package com.example.justin.neuroflow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.justin.neuroflow.SecondScreen.OnButtonTappedListener;

public class SecondStory extends AppCompatActivity implements OnButtonTappedListener{

    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.first_screen:
                    FirstScreen FSfrag = FirstScreen.newInstance(-1);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, FSfrag)
                            .commit();
                    return true;
                case R.id.second_screen:
                    SecondScreen SSfrag = SecondScreen.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, SSfrag)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FirstScreen FSfrag = FirstScreen.newInstance(-1);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSfrag)
                .commit();
    }

    public void onButtonTapped(int num){
        navigation.setSelectedItemId(R.id.first_screen);
        FirstScreen FSfrag = FirstScreen.newInstance(num);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSfrag)
                .commit();
    }
}
