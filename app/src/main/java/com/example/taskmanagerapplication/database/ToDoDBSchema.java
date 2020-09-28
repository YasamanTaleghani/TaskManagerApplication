package com.example.taskmanagerapplication.database;

public class ToDoDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

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
}
