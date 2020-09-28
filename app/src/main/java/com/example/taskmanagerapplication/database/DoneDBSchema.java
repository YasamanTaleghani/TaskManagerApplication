package com.example.taskmanagerapplication.database;

public class DoneDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class doneTable {
        public static final String NAME = "doneTable";

        public static final class DoneCols {
            //this column in only for database
            public static final String ID = "done_id";
            public static final String UUID = "done_uuid";
            public static final String TITLE = "done_title";
            public static final String DESCRIPTION = "done_description";
            public static final String DATE = "done_date";
            public static final String SOLVED = "done_solved";
        }
    }
}

