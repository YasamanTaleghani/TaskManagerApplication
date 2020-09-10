package com.example.taskmanagerapplication.Repository;

import com.example.taskmanagerapplication.model.Task;

import java.util.List;
import java.util.UUID;

public interface ToDoInterface {

    List<Task> getTasks();
    Task getTask(UUID taskId);
    void insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(Task task);
    int getPosition(UUID taskId);
    public void setTasks(List<Task> tasks);
}
