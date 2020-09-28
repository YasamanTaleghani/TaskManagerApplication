package com.example.taskmanagerapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.taskmanagerapplication.database.UserDBSchema.userTable.Cols;
import static com.example.taskmanagerapplication.database.UserDBSchema.toDoTable.TodoCols;
import static com.example.taskmanagerapplication.database.UserDBSchema.doingTable.DoingCols;
import static com.example.taskmanagerapplication.database.UserDBSchema.doneTable.DoneCols;

import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(@Nullable Context context) {
        super(context, UserDBSchema.NAME, null, UserDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("CREATE TABLE " + UserDBSchema.userTable.NAME + " (");
        sbQuery.append(Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery.append(Cols.UUID + " TEXT NOT NULL,");
        sbQuery.append(Cols.USERNAME + " TEXT NOT NULL,");
        sbQuery.append(Cols.PASSWORD + " TEXT NOT NULL");
        sbQuery.append(");");

        sqLiteDatabase.execSQL(sbQuery.toString());

        StringBuilder todoQuery = new StringBuilder();
        todoQuery.append("CREATE TABLE " + UserDBSchema.toDoTable.NAME + " (");
        todoQuery.append(TodoCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," );
        todoQuery.append(TodoCols.UUID + " TEXT NOT NULL,");
        todoQuery.append(TodoCols.TITLE + " TEXT,");
        todoQuery.append(TodoCols.DESCRIPTION + " TEXT,");
        todoQuery.append(TodoCols.DATE + " TEXT,");
        todoQuery.append(TodoCols.SOLVED + " INTEGER");
        todoQuery.append(");");

        sqLiteDatabase.execSQL(todoQuery.toString());

        StringBuilder doingQuery = new StringBuilder();
        doingQuery.append("CREATE TABLE " + UserDBSchema.doingTable.NAME + " (");
        doingQuery.append(DoingCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," );
        doingQuery.append(DoingCols.UUID + " TEXT NOT NULL,");
        doingQuery.append(DoingCols.TITLE + " TEXT,");
        doingQuery.append(DoingCols.DESCRIPTION + " TEXT,");
        doingQuery.append(DoingCols.DATE + " TEXT,");
        doingQuery.append(DoingCols.SOLVED + " INTEGER");
        doingQuery.append(");");

        sqLiteDatabase.execSQL(doingQuery.toString());

        StringBuilder doneQuery = new StringBuilder();
        doneQuery.append("CREATE TABLE " + UserDBSchema.doneTable.NAME + " (");
        doneQuery.append(DoneCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," );
        doneQuery.append(DoneCols.UUID + " TEXT NOT NULL,");
        doneQuery.append(DoneCols.TITLE + " TEXT,");
        doneQuery.append(DoneCols.DESCRIPTION + " TEXT,");
        doneQuery.append(DoneCols.DATE + " TEXT,");
        doneQuery.append(DoneCols.SOLVED + " INTEGER");
        doneQuery.append(");");

        sqLiteDatabase.execSQL(doneQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
