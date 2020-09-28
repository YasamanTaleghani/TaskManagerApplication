package com.example.taskmanagerapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.fragment.LoginFragment;

import java.util.UUID;

public class loginActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.example.criminalintent.crimeId";

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, ManagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        UUID userId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        LoginFragment loginFragment = LoginFragment.newInstance(userId);
        return loginFragment;
    }
}