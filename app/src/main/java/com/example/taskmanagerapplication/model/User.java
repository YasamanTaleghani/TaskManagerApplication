package com.example.taskmanagerapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "userTable")
public class User {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @ColumnInfo(name = "password")
    private String mPassword;

    //Constructor
    public User(UUID id,String userName, String password) {
        mId = id;
        mUserName = userName;
        mPassword = password;
    }

    public User(String userName, String password) {
        mId = UUID.randomUUID();
        mUserName = userName;
        mPassword = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public User(UUID id) {
        mId = id;
    }

    public User() {
        this(UUID.randomUUID());
    }

    //Getter & Setters
    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
