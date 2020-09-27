package com.example.taskmanagerapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.taskmanagerapplication.database.UserDBSchema.userTable.Cols;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
