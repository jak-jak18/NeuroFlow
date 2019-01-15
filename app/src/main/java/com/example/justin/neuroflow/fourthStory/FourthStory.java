package com.example.justin.neuroflow.fourthStory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.justin.neuroflow.fourthStory.SecondScreen.OnItemSelectedListener;
import com.example.justin.neuroflow.R;
import com.example.justin.neuroflow.db.Columns;
import com.example.justin.neuroflow.db.DbHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FourthStory extends AppCompatActivity implements OnItemSelectedListener {

    private static final String LOG_TAG = "FourthStory";
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.first_screen:
                    FirstScreen FSfrag = FirstScreen
                            .newInstance(null,-1,0);
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

        if(dataCheck() == 0){
            Toast.makeText(this, "Inserting data", Toast.LENGTH_SHORT).show();
            insertData();
        }

        FirstScreen FSfrag = FirstScreen.newInstance(null,-1,0);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSfrag)
                .commit();
    }

    public void onItemSelected(String name, int score, long date){
        navigation.setSelectedItemId(R.id.first_screen);
        FirstScreen FSfrag = FirstScreen.newInstance(name, score, date);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FSfrag)
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
        final String url = "https://gist.githubusercontent.com/ryanneuroflow/370d19311602c091928300edd7a40f66/raw/1865ae6004142553d8a6c6ba79ccb511028a2cba/names.json";
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        readJsonStream((InputStream) new URL(url).getContent());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FourthStory.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (MalformedURLException e) {
                        Log.e(LOG_TAG, e.getMessage());
                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                }
            }).start();
        }else {
            Toast.makeText(this, "Please connect to Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public void readMessagesArray(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readMessage(reader);
        }
        reader.endArray();
    }

    public void readMessage(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()){
            String sex = reader.nextName();
            if(sex.equals("males")){
                readPlayers(reader, "M");
            }else if(sex.equals("females")){
                readPlayers(reader, "F");
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    public void readPlayers(JsonReader reader, String sex) throws IOException{
        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        reader.beginArray();
        while (reader.hasNext()){
            reader.beginObject();
            List<String> player = new ArrayList<>();
            while (reader.hasNext()){
                String key = reader.nextName();
                if(key.equals("name")){
                    player.add(reader.nextString());
                }else if(key.equals("score")){
                    player.add(reader.nextString());
                }else if(key.equals("date_created")){
                    player.add(reader.nextString());
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            player.add(sex);
            ContentValues values = new ContentValues();
            values.put(Columns.Titles.NAME, player.get(0));
            values.put(Columns.Titles.PERCENT, Integer.valueOf(player.get(1)));
            values.put(Columns.Titles.DATE, player.get(2));
            values.put(Columns.Titles.SEX, player.get(3));
            db.insert(
                    Columns.Titles.TABLE_NAME,
                    Columns.Titles.COLUMN_NAME_NULLABLE,
                    values);
        }
        reader.endArray();
        db.close();
    }
}
