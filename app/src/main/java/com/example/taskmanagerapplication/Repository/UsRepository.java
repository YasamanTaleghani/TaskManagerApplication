package com.example.taskmanagerapplication.Repository;

import com.example.taskmanagerapplication.model.User;

import java.util.List;
import java.util.UUID;

public interface UsRepository {

    List<User> getUsers();
    User getUser(UUID userId);
    void insertUser(User user);

}
