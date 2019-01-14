package com.example.justin.neuroflow;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstScreenThirdStory extends Fragment {

    private static final String LOG_TAG = "FirstScreenThirdStory";
    static String NAME = "NAME", SCORE = "SCORE", DATE = "DATE";

    public FirstScreenThirdStory() {}

    public static FirstScreenThirdStory newInstance(String name, int score, long date) {
        FirstScreenThirdStory FSTSfrag = new FirstScreenThirdStory();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putInt(SCORE, score);
        args.putLong(DATE, date);
        FSTSfrag.setArguments(args);
        return FSTSfrag;
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
            sb.append("\n");
            Date dNow = new Date(getArguments().getLong(DATE));
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            sb.append(ft.format(dNow));
            textView.setText(sb.toString());
        }
    }
}
