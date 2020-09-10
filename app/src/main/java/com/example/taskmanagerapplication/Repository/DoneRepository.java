package com.example.taskmanagerapplication.Repository;

import com.example.taskmanagerapplication.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DoneRepository implements DoneInterface{

    private static DoneRepository sInstance;

    public static DoneRepository getInstance() {
        if (sInstance == null)
            sInstance = new DoneRepository();

        return sInstance;
    }

    private List<Task> mTasks;

    private DoneRepository() {
        mTasks = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            Task task = new Task();
            task.setTitle("Task#" + (i + 1));
            task.setSolved(i % 2 == 0);

            mTasks.add(task);
        }
    }


    @Override
    public List<Task> getTasks() {
        return mTasks;
    }

    @Override
    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }


    @Override
    public Task getTask(UUID TaskId) {
        for (int i = 0; i <mTasks.size() ; i++) {
            if (mTasks.get(i).getId().equals(TaskId))
                return mTasks.get(i);
        }

        return null;
    }

    @Override
    public void insertTask(Task task) {
        mTasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        Task findTask = getTask(task.getId());
        findTask.setTitle(task.getTitle());
        findTask.setDescription(task.getDescription());
        findTask.setSolved(task.isSolved());
        findTask.setDate(task.getDate());
    }

    @Override
    public void deleteTask(Task task) {
        for (int i = 0; i < mTasks.size() ; i++) {
            if (mTasks.get(i).getId().equals(task.getId())){
                mTasks.remove(i);
                return;
            }
        }
    }

    @Override
    public int getPosition(UUID taskId) {
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getId().equals(taskId))
                return i;
        }

        return 0;
    }
}
