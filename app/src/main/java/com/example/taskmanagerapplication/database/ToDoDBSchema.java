package com.example.taskmanagerapplication.database;

public class ToDoDBSchema {

    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class toDoTable {
        public static final String NAME = "toDoTable";

        public static final class Cols {
            //this column in only for database
            public static final String TODOID = "todo_id";
            public static final String TODOUUID = "todo_uuid";
            public static final String TODOTITLE = "todo_title";
            public static final String TODODESCRIPTION = "todo_description";
            public static final String TODODATE = "todo_date";
            public static final String TODOSOLVED = "todo_solved";
        }
    }
}
