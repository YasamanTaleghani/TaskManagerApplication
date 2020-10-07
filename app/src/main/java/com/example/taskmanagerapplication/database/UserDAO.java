package com.example.taskmanagerapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmanagerapplication.Repository.UsRepository;
import com.example.taskmanagerapplication.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDAO extends UsRepository {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE uuid=:userId")
    User getUser(UUID userId);

}
