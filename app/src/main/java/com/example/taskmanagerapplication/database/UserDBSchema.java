package com.example.taskmanagerapplication.database;

public class UserDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class userTable {
        public static final String NAME = "userTable";

        public static final class Cols {
            //this column in only for database
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public static final class toDoTable {
        public static final String NAME = "toDoTable";

        public static final class TodoCols {
            //this column in only for database
            public static final String ID = "todo_id";
            public static final String UUID = "todo_uuid";
            public static final String TITLE = "todo_title";
            public static final String DESCRIPTION = "todo_description";
            public static final String DATE = "todo_date";
            public static final String SOLVED = "todo_solved";
        }
    }

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
