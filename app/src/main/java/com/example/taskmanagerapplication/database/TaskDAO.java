package com.example.taskmanagerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerapplication.Repository.TaskRepositoryInterface;
import com.example.taskmanagerapplication.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDAO extends TaskRepositoryInterface {

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE uuid=:taskId")
    Task getTask(UUID taskId);

}
