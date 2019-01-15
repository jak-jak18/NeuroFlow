package com.example.justin.neuroflow.fourthStory;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.justin.neuroflow.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstScreen extends Fragment {
    private static final String LOG_TAG = "FourthStoryFirstScreen";
    static String NAME = "NAME", SCORE = "SCORE", DATE = "DATE";

    public FirstScreen() {}

    public static FirstScreen newInstance(String name, int score, long date) {
        FirstScreen FSfrag = new FirstScreen();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putInt(SCORE, score);
        args.putLong(DATE, date);
        FSfrag.setArguments(args);
        return FSfrag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.first_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (getArguments().getInt(SCORE) != -1){
            TextView textView = view.findViewById(R.id.textView);
            StringBuilder sb = new StringBuilder();
            sb.append(getArguments().getString(NAME));
            sb.append("\n");
            sb.append(getArguments().getInt(SCORE));
            sb.append("%");
            sb.append("\n");
            Date dNow = new Date(getArguments().getLong(DATE));
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            sb.append(ft.format(dNow));
            textView.setText(sb.toString());
        }
    }
}
