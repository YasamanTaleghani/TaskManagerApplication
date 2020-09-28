package com.example.taskmanagerapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.Repository.ToDoRepository;
import com.example.taskmanagerapplication.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class ToDoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ToDoTaskAdapter mToDoTaskAdapter;
    private ToDoRepository mToDoRepository;
    private ImageView mImageView;
    private TextView mTextView;
    private FloatingActionButton mFloatingActionButton;

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
        mToDoRepository = ToDoRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        findViews(view);
        setListeners();
        initViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.ToDoRecyclerView);
        mImageView = view.findViewById(R.id.imageView);
        mTextView = view.findViewById(R.id.textview);
        mFloatingActionButton = view.findViewById(R.id.floatingActionButton_todo);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        List<Task> tasks = mToDoRepository.getTasks();

        if (tasks.size() == 0) {
            mImageView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);

            if (mToDoTaskAdapter == null) {
                mToDoTaskAdapter = new ToDoTaskAdapter(tasks);
                mRecyclerView.setAdapter(mToDoTaskAdapter);
            } else {
                mToDoTaskAdapter.setTasks(tasks);
                mToDoTaskAdapter.notifyDataSetChanged();
            }
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private ImageView mImageViewSolved;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.row_item_task_title);
            mTextViewDate = itemView.findViewById(R.id.row_item_task_date);
            mImageViewSolved = itemView.findViewById(R.id.action_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mTextViewTitle.setText(task.getTitle());
            mTextViewDate.setText(task.getDate().toString());
            mImageViewSolved.setVisibility(View.VISIBLE);
        }
    }

    private class ToDoTaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public ToDoTaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.task_row_list, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

    }

    private void setListeners(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        AddTaskDialogFragment taskDialogFragment = new AddTaskDialogFragment(getActivity());
        taskDialogFragment.show();
    }

    public class AddTaskDialogFragment extends Dialog implements android.view.View.OnClickListener{

        private Activity mActivity;
        private Task mTask;
        private Dialog mDialog;
        private EditText mTitle, mDescription;
        private Button save, cancel;
        private CheckBox mCheckBox;
        private Button mButtonDatePicker, mButtonTimePicker;

        public AddTaskDialogFragment(Activity activity){
            super(activity);
            mActivity = activity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.starting_dialog_layout);

            save = findViewById(R.id.btn_save);
            cancel = findViewById(R.id.btn_cancle);
            mCheckBox = findViewById(R.id.checkbox_task);
            mTitle = findViewById(R.id.title_edittext);
            mDescription = findViewById(R.id.description_edittext);
            mButtonDatePicker = findViewById(R.id.btn_date);
            mButtonTimePicker = findViewById(R.id.btn_time);

            save.setOnClickListener(this);
            cancel.setOnClickListener(this);
            mButtonDatePicker.setOnClickListener(this);
            mButtonTimePicker.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.btn_save:
                    mTask = new Task();
                    mTask.setTitle(mTitle.getText().toString());
                    mTask.setDescription(mDescription.getText().toString());
                    mTask.setSolved(mCheckBox.isChecked());
                    mToDoRepository.insertTask(mTask);
                    updateUI();
                    break;

                case R.id.btn_cancle:
                    dismiss();
                    break;

                case R.id.btn_date:
                    DatePickerFragment datePickerFragment =
                            DatePickerFragment.newInstance(mTask.getDate());

                    //create parent-child relations between CDF and DPF
                    /*datePickerFragment.setTargetFragment(
                            CrimeDetailFragment.this,
                            REQUEST_CODE_DATE_PICKER);*/

                    datePickerFragment.show(
                            getActivity().getSupportFragmentManager(),
                            "Fragment_tag_date_picker");
                    break;

                case R.id.btn_time:
                    //todo
                    break;

                default:
                    break;
            }
            dismiss();
        }

    }
}