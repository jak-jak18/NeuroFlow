package com.example.justin.neuroflow.db;

import android.provider.BaseColumns;

public class Columns {

    public Columns() {}

    public static abstract class Titles implements BaseColumns {
        public static final String TABLE_NAME = "PLAYERS";

        public static final String NAME = "NAME";
        public static final String PERCENT = "PERCENT";
        public static final String DATE = "DATE";
        public static final String SEX = "SEX";

        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
