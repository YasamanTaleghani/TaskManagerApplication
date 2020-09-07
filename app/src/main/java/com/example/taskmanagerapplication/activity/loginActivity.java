package com.example.taskmanagerapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.fragment.LoginFragment;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.Login_Container);

        //create an add fragment transaction for CrimeDetailFragment
        if (fragment == null) {
            LoginFragment loginFragment = LoginFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.Login_Container,loginFragment)
                    .commit();
        }
    }
}