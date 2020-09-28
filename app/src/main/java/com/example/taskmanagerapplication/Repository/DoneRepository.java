package com.example.taskmanagerapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerapplication.database.TaskDBHelper;
import com.example.taskmanagerapplication.database.UserDBSchema;
import com.example.taskmanagerapplication.model.Task;

import static com.example.taskmanagerapplication.database.UserDBSchema.doneTable.DoneCols;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DoneRepository implements TaskRepositoryInterface{

    private static DoneRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private List<Task> mTasks;

    public static DoneRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DoneRepository(context);

        return sInstance;
    }

    private DoneRepository(Context context) {
        mContext = context;
        TaskDBHelper taskDBHelper = new TaskDBHelper(context);
        mDatabase = taskDBHelper.getWritableDatabase();
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                UserDBSchema.doneTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return tasks;

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Task task = getUserFromCursor(cursor);

                tasks.add(task);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return tasks;
    }

    @Override
    public Task getTask(UUID TaskId) {
        String where = DoneCols.UUID + " = ?";
        String[] whereArgs =  new String[]{TaskId.toString()};

        Cursor cursor = mDatabase.query(
                UserDBSchema.doneTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new Task();

        try {
            cursor.moveToFirst();
            Task task = getUserFromCursor(cursor);
            return task;

        } finally {
            cursor.close();
        }
    }

    @Override
    public void insertTask(Task task) {
        ContentValues contentValues = getContentValues(task);
        mDatabase.insert(UserDBSchema.doneTable.NAME,null,contentValues);
    }

    @Override
    public boolean searchTask(Task task) {
        List<Task> users = getTasks();
        UUID id = task.getId();
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getId().equals(id)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateTask(Task task) {
        ContentValues contentValue = getContentValues(task);
        mDatabase.replace(UserDBSchema.doneTable.NAME,null,contentValue);
    }

    @Override
    public void deleteTask(Task task) {
        String where = DoneCols.UUID + " = ?";
        String[] whereArgs =  new String[]{task.getId().toString()};
        mDatabase.delete(UserDBSchema.doneTable.NAME,where,whereArgs);
    }

    @Override
    public int getPosition(UUID taskId) {
        List<Task> users = getTasks();
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getId().equals(taskId))
                return i;
        }

        return -1;
    }

    private Task getUserFromCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(
                cursor.getString(cursor.getColumnIndex(DoneCols.UUID)));
        String title = cursor.getString(
                cursor.getColumnIndex(DoneCols.TITLE));
        String description = cursor.getString(
                cursor.getColumnIndex(DoneCols.DESCRIPTION));
        Date date = new Date(cursor.getLong(
                cursor.getColumnIndex(DoneCols.DATE)));
        boolean solve = cursor.getInt(cursor.getColumnIndex(DoneCols.SOLVED)) == 0 ? false : true;

        return new Task(uuid,title,description,date,solve);
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(DoneCols.UUID, task.getId().toString());
        values.put(DoneCols.TITLE , task.getTitle());
        values.put(DoneCols.DESCRIPTION , task.getDescription());
        values.put(DoneCols.DATE , task.getDate().toString());
        values.put(DoneCols.SOLVED , task.isSolved());
        return values;
    }
}
