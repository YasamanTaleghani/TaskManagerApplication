package com.example.taskmanagerapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "taskTable")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    private int mPrimaryId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "taskType")
    private String mTaskType;


    //Constructor
    public Task() {
        mId = UUID.randomUUID();
        Calendar calendar = Calendar.getInstance();
        mDate = calendar.getTime();
    }

    public Task(
            UUID id, String title, String description, Date date , String taskType){
        mId = id;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTaskType = taskType;
    }


    //Getter & Setters
    public int getPrimaryId() {
        return mPrimaryId;
    }

    public void setPrimaryId(int primaryId) {
        mPrimaryId = primaryId;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTaskType() {
        return mTaskType;
    }

    public void setTaskType(String taskType) {
        mTaskType = taskType;
    }

}
