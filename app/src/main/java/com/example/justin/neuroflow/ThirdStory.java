package com.example.justin.neuroflow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.justin.neuroflow.SecondScreenThirdStory.OnItemSelectedListener;
import com.example.justin.neuroflow.db.Columns;
import com.example.justin.neuroflow.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThirdStory extends AppCompatActivity implements OnItemSelectedListener {

    private static final String LOG_TAG = "ThirdStory";
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.first_screen:
                    FirstScreenThirdStory FSTSfrag = FirstScreenThirdStory
                            .newInstance(null,-1,0);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, FSTSfrag)
                            .commit();
                    return true;
                case R.id.second_screen:
                    SecondScreenThirdStory SSTSfrag = SecondScreenThirdStory.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, SSTSfrag)
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

        if(dataCheck() == 0){
            Log.d(LOG_TAG, "Data inserted");
            insertData();
        }

        FirstScreenThirdStory FSTSfrag = FirstScreenThirdStory.newInstance(null,-1,0);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSTSfrag)
                .commit();
    }

    public void onItemSelected(String name, int score, long date){
        navigation.setSelectedItemId(R.id.first_screen);
        FirstScreenThirdStory FSTSfrag = FirstScreenThirdStory.newInstance(name, score, date);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSTSfrag)
                .commit();
    }

    int dataCheck(){
        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                Columns.Titles.NAME,
        };
        Cursor c = db.query(
                true,
                Columns.Titles.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );
        int count = c.getCount();
        db.close();
        return count;
    }

    void insertData(){
        List<String[]> players = new ArrayList<>();
        players.add(getResources().getStringArray(R.array.player1_3rdStory));
        players.add(getResources().getStringArray(R.array.player2_3rdStory));
        players.add(getResources().getStringArray(R.array.player3_3rdStory));
        players.add(getResources().getStringArray(R.array.player4_3rdStory));
        players.add(getResources().getStringArray(R.array.player5_3rdStory));
        players.add(getResources().getStringArray(R.array.player6_3rdStory));

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        for(String[] player: players){
            ContentValues values = new ContentValues();
            values.put(Columns.Titles.NAME, player[0]);
            values.put(Columns.Titles.PERCENT, Integer.valueOf(player[1]));
//            Log.d(LOG_TAG, Long.parseLong(player[2]));
            values.put(Columns.Titles.DATE, player[2]);
            values.put(Columns.Titles.SEX, player[3]);
            db.insert(
                    Columns.Titles.TABLE_NAME,
                    Columns.Titles.COLUMN_NAME_NULLABLE,
                    values);
        }
        db.close();
    }
}
