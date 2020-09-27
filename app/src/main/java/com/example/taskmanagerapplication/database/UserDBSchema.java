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

}
