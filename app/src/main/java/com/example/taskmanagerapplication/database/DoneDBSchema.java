package com.example.taskmanagerapplication.database;

public class DoneDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class doneTable {
        public static final String NAME = "doneTable";

        public static final class Cols {
            //this column in only for database
            public static final String DONEID = "done_id";
            public static final String DONEUUID = "done_uuid";
            public static final String DONETITLE = "done_title";
            public static final String DONEDESCRIPTION = "done_description";
            public static final String DONEDATE = "done_date";
            public static final String DONESOLVED = "done_solved";
        }
    }
}
