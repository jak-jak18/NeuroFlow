package com.example.justin.neuroflow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class SecondScreen extends Fragment {

    private OnButtonTappedListener mListener;
    public interface OnButtonTappedListener {
        void onButtonTapped(int num);
    }

    public SecondScreen() {}

    public static SecondScreen newInstance() {
        return new SecondScreen();
    }

    @Override
    public void onAttach(Activity activity) {
        mListener = (OnButtonTappedListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.second_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random ran = new Random();
                mListener.onButtonTapped(ran.nextInt(4));
            }
        });
    }
}
