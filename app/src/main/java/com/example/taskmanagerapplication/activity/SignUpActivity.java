package com.example.taskmanagerapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.fragment.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity{

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        return signUpFragment;
    }

}