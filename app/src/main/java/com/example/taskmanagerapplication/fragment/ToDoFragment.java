package com.example.taskmanagerapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapplication.R;
import com.google.android.material.tabs.TabLayoutMediator;


public class ToDoFragment extends Fragment {

    public ToDoFragment() {
        // Required empty public constructor
    }


    public static ToDoFragment newInstance() {
        ToDoFragment fragment = new ToDoFragment();
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
        return inflater.inflate(R.layout.fragment_to_do, container, false);

    }
}