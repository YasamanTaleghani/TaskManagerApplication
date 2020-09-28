package com.example.taskmanagerapplication.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private boolean mSolved;

    //Constructor
    public Task() {
        mId = UUID.randomUUID();
        Calendar calendar = Calendar.getInstance();
        mDate = calendar.getTime();
    }

    public Task(UUID id, String title, String description, Date date, Boolean solved){
        mId = id;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mSolved = solved;
    }

    //Getter & Setters
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

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

}
