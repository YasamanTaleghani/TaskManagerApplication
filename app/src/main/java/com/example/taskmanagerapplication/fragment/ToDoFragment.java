package com.example.taskmanagerapplication.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import com.example.taskmanagerapplication.Repository.TaskRepository;
import com.example.taskmanagerapplication.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ToDoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ToDoTaskAdapter mToDoTaskAdapter;
    private TaskRepository mTaskRepository;
    private ImageView mImageView;
    private TextView mTextView;
    private Task mTaskToDo;
    private FloatingActionButton mFloatingActionButton;
    private String mStringTaskType = "ToDo";

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
        mTaskRepository = TaskRepository.getInstance(getActivity());
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
        List<Task> tasks = getToDoTasks();

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

                    EditTaskDialogFragment editTaskDialogFragment =
                            new EditTaskDialogFragment(getActivity());
                    editTaskDialogFragment.show();
                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mTaskToDo = task;
            mTextViewTitle.setText(task.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = task.getDate();
            mTextViewDate.setText(simpleDateFormat.format(date));
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

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            mButtonDatePicker.setText(simpleDateFormat.format(date));
            mButtonTimePicker.setText(simpleDateFormat1.format(date));

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

                    UUID id = UUID.randomUUID();
                    String title = mTitle.getText().toString();
                    String description = mDescription.getText().toString();
                    Date date = new Date();
                    String TaskType = mStringTaskType;
                    Boolean solved = mCheckBox.isChecked();
                    mTask = new Task(id,title,description,date,TaskType,solved);
                    mTaskRepository.insertTask(mTask);
                    updateUI();
                    break;

                case R.id.btn_cancle:
                    dismiss();
                    break;

                case R.id.btn_date:
                    DatePickerFragment datePickerFragment =
                            DatePickerFragment.newInstance(mTask.getDate());

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

    public class EditTaskDialogFragment extends Dialog implements android.view.View.OnClickListener{

        private Activity mActivity;
        private Task mTask=mTaskToDo;
        private Dialog mDialog;
        private EditText mTitle, mDescription;
        private Button save, edit, delet;
        private CheckBox mCheckBox;
        private Button mButtonDatePicker, mButtonTimePicker;

        public EditTaskDialogFragment(Activity activity){
            super(activity);
            mActivity = activity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.edit_dialog_layout);

            save = findViewById(R.id.btn_save);
            edit = findViewById(R.id.btn_edit);
            delet = findViewById(R.id.btn_delete);
            mCheckBox = findViewById(R.id.checkbox_task);
            mTitle = findViewById(R.id.title_edittext);
            mDescription = findViewById(R.id.description_edittext);
            mButtonDatePicker = findViewById(R.id.btn_date);
            mButtonTimePicker = findViewById(R.id.btn_time);
            mTitle.setEnabled(false);
            mDescription.setEnabled(false);
            mButtonDatePicker.setEnabled(false);
            mButtonTimePicker.setEnabled(false);
            mTitle.setText(mTask.getTitle());
            mDescription.setText(mTask.getDescription());
            mCheckBox.setChecked(mTask.isSolved());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
            Date date = mTask.getDate();
            mButtonDatePicker.setText(simpleDateFormat.format(date));
            mButtonTimePicker.setText(simpleDateFormat1.format(date));

            save.setOnClickListener(this);
            edit.setOnClickListener(this);
            delet.setOnClickListener(this);
            mButtonDatePicker.setOnClickListener(this);
            mButtonTimePicker.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.btn_save:

                    UUID id = mTask.getId();
                    String title = mTitle.getText().toString();
                    String description = mDescription.getText().toString();
                    Date date = new Date();
                    String TaskType = mStringTaskType;
                    Boolean solved = mCheckBox.isChecked();
                    Task task = new Task(id,title,description,date,TaskType,solved);
                    mTaskRepository.updateTask(task);
                    updateUI();
                    break;

                case R.id.btn_edit:
                    mTitle.setEnabled(true);
                    mDescription.setEnabled(true);
                    mButtonDatePicker.setEnabled(true);
                    mButtonTimePicker.setEnabled(true);
                    break;

                case R.id.btn_delete:
                    mTaskRepository.deleteTask(mTask);
                    updateUI();
                    break;

                case R.id.btn_date:
                    DatePickerFragment datePickerFragment =
                            DatePickerFragment.newInstance(mTask.getDate());

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

    public List<Task> getToDoTasks(){
        List<Task> resultTasks = new ArrayList<>();
        List<Task> tasks = mTaskRepository.getTasks();

        for (int i = 0; i < tasks.size() ; i++) {
            Task task = tasks.get(i);
            if (task.getTaskType().equals(mStringTaskType)){
                resultTasks.add(task);
            }
        }

        return resultTasks;
    }
}