package com.example.taskmanagerapplication.database;

import android.annotation.TargetApi;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerapplication.model.Task;
import com.example.taskmanagerapplication.model.User;

@Database(entities = {User.class, Task.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDAO getTaskDAO();
    public abstract UserDAO getUserDAO();

}
