package com.example.taskmanagerapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.activity.ManagerActivity;

public class LoginFragment extends Fragment {

    public static final String EXTRA_LOGIN_USERNAME =
            "com.example.taskmanagerapplication.Extra_Login_Username";
    public static final String EXTRA_LOGIN_PASSWORD =
            "com.example.taskmanagerapplication.Extra_Login_password";

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListeners();

        return view;
    }

    private void findViews(View view){
        mEditTextUserName = view.findViewById(R.id.login_username);
        mEditTextPassword = view.findViewById(R.id.login_password);
        mButtonLogin = view.findViewById(R.id.btn_login);
    }

    private void setListeners(){
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEditTextUserName.length()==0 || mEditTextPassword.length()==0){
                    Toast.makeText(getActivity(), R.string.userpass,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity() , ManagerActivity.class);
                    intent.putExtra(EXTRA_LOGIN_USERNAME,
                            mEditTextUserName.getText().toString().trim());
                    intent.putExtra(EXTRA_LOGIN_PASSWORD,
                            mEditTextPassword.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }
}