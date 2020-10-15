package com.example.taskmanagerapplication.Repository;

import android.content.Context;
import androidx.room.Room;

import com.example.taskmanagerapplication.database.TaskDAO;
import com.example.taskmanagerapplication.database.TaskDataBase;
import com.example.taskmanagerapplication.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskRepository implements TaskDAO {

    private static TaskRepository sInstance;
    private TaskDAO mTaskDAO;
    private Context mContext;

    public static TaskRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskRepository(context);

        return sInstance;
    }

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();

        TaskDataBase taskDataBase =
                Room.databaseBuilder(mContext,TaskDataBase.class,"user.db")
                        .allowMainThreadQueries()
                        .build();

        mTaskDAO = taskDataBase.getTaskDAO();
    }

    @Override
    public List<Task> getTasks() {
        return mTaskDAO.getTasks();
    }

    @Override
    public Task getTask(UUID taskId) {
        return mTaskDAO.getTask(taskId);
    }

    @Override
    public void insertTask(Task task) {
        mTaskDAO.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mTaskDAO.updateTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mTaskDAO.deleteTask(task);
    }

    public boolean searchTask(Task task) {
        List<Task> tasks = getTasks();
        UUID id = task.getId();
        for (int i = 0; i < tasks.size() ; i++) {
            if (tasks.get(i).getId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public int getPosition(UUID taskId) {
        List<Task> tasks = getTasks();
        for (int i = 0; i < tasks.size() ; i++) {
            if (tasks.get(i).getId().equals(taskId))
                return i;
        }

        return -1;
    }


}
