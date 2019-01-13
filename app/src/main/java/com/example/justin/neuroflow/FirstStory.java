package com.example.justin.neuroflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstStory extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        mTextMessage = findViewById(R.id.textView);
        StringBuilder sb = new StringBuilder();
        sb.append("Ryan");
        sb.append("\n");
        sb.append("100%");
        sb.append("\n");
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        sb.append(ft.format(dNow));
        mTextMessage.setText(sb.toString());
    }
}
