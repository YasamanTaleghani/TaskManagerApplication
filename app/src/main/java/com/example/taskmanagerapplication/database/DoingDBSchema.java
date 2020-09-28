package com.example.taskmanagerapplication.database;

public class DoingDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class doingTable {
        public static final String NAME = "doingTable";

        public static final class DoingCols {
            //this column in only for database
            public static final String ID = "doing_id";
            public static final String UUID = "doing_uuid";
            public static final String TITLE = "doing_title";
            public static final String DESCRIPTION = "doing_description";
            public static final String DATE = "doing_date";
            public static final String SOLVED = "doing_solved";
        }
    }

}
