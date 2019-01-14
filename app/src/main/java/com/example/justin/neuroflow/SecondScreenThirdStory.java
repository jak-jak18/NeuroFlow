package com.example.justin.neuroflow;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justin.neuroflow.db.Columns;
import com.example.justin.neuroflow.db.DbHelper;

public class SecondScreenThirdStory extends Fragment {

    private OnItemSelectedListener mListener;
    public interface OnItemSelectedListener {
        void onItemSelected(String name, int score, long date);
    }

    public SecondScreenThirdStory() {}

    public static SecondScreenThirdStory newInstance() {
        return new SecondScreenThirdStory();
    }

    @Override
    public void onAttach(Activity activity) {
        mListener = (OnItemSelectedListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.second_screen_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        arrayAdapter.add("Males");
        arrayAdapter.add("(name - score percent - timestamp)");

        Cursor cursor = retrieveData("M");
        cursor.moveToNext();
        while (!cursor.isAfterLast()){
            StringBuilder sb = new StringBuilder();
            sb.append(cursor.getString(0));
            sb.append(" - ");
            sb.append(cursor.getInt(1));
            sb.append(" - ");
            sb.append(cursor.getString(2));
            arrayAdapter.add(sb.toString());
            cursor.moveToNext();
        }
        cursor.close();
        arrayAdapter.add("");

        arrayAdapter.add("Females");
        arrayAdapter.add("(name - score percent - timestamp)");
        cursor = retrieveData("F");
        cursor.moveToNext();
        while (!cursor.isAfterLast()){
            StringBuilder sb = new StringBuilder();
            sb.append(cursor.getString(0));
            sb.append(" - ");
            sb.append(cursor.getInt(1));
            sb.append(" - ");
            sb.append(cursor.getString(2));
            arrayAdapter.add(sb.toString());
            cursor.moveToNext();
        }
        cursor.close();

        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String text = String.valueOf(textView.getText());
                String[] data = text.split(" - ");
                mListener.onItemSelected(data[0],
                        Integer.valueOf(data[1]),
                        Long.parseLong(data[2]));
            }
        });
    }

    Cursor retrieveData(String sex){
        DbHelper mDbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                Columns.Titles.NAME,
                Columns.Titles.PERCENT,
                Columns.Titles.DATE
        };

        String selection = Columns.Titles.SEX + " = ?";
        String[] selectionArgs = {sex};
        String sortOrder = Columns.Titles.DATE + " ASC";

        Cursor c = db.query(
                Columns.Titles.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return c;
    }
}
