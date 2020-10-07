package com.example.taskmanagerapplication.Repository;

import android.content.Context;
import androidx.room.Room;
import com.example.taskmanagerapplication.database.TaskDataBase;
import com.example.taskmanagerapplication.database.UserDAO;
import com.example.taskmanagerapplication.model.User;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements UserDAO {

    private static UserDBRepository sInstance;
    private UserDAO mUserDAO;
    private Context mContext;

    public static UserDBRepository getInstance(Context contex) {
        if (sInstance == null)
            sInstance = new UserDBRepository(contex);

        return sInstance;
    }

    public UserDBRepository(Context context) {
        mContext = context.getApplicationContext();

        TaskDataBase taskDataBase =
                Room.databaseBuilder(mContext,TaskDataBase.class,"user.db")
                .allowMainThreadQueries()
                .build();

        mUserDAO = taskDataBase.getUserDAO();
    }


    @Override
    public List<User> getUsers() {
        return mUserDAO.getUsers();
    }

    @Override
    public User getUser(UUID userId) {
       return mUserDAO.getUser(userId);
    }

    @Override
    public void insertUser(User user) {
        mUserDAO.insertUser(user);
    }

    public Boolean searchUser(User user) {
        List<User> users = getUsers();
        String username = user.getUserName();
        String password = user.getPassword();
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getUserName().equals(username) &&
                    users.get(i).getPassword().equals(password)){
                return true;
            }
        }

        return false;
    }

}
