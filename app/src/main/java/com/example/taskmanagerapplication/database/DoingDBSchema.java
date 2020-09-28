package com.example.taskmanagerapplication.database;

public class DoingDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class doingTable {
        public static final String NAME = "doingTable";

        public static final class Cols {
            //this column in only for database
            public static final String DOINGID = "doing_id";
            public static final String DOINGUUID = "doing_uuid";
            public static final String DOINGTITLE = "doing_title";
            public static final String DOINGDESCRIPTION = "doing_description";
            public static final String DOINGDATE = "doing_date";
            public static final String DOINGSOLVED = "doing_solved";
        }
    }

}
