package com.example.taskmanagerapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.Repository.TaskRepository;
import com.example.taskmanagerapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddTaskDialogFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final int REQUEST_CODE_ADD_TASK = 2;

    private Activity mActivity;
    private Callbacks mCallbacks;
    private Dialog mDialog;
    private EditText mTitle, mDescription;
    private Button save, cancel;
    private Button mButtonDatePicker, mButtonTimePicker;
    private Date date;
    private Task mTask = new Task();
    private String taskType;
    private TaskRepository mTaskRepository = TaskRepository.getInstance(getContext());

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    public static AddTaskDialogFragment newInstance(Fragment f, String string) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString("arg_Task_type", string);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        taskType = getArguments().getString("arg_Task_type");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ToDoFragment.Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + " must implement Callbacks");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.starting_dialog_layout, null);

        @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Task")
                .setView(view);

        AlertDialog dialog = builder.create();

        findView(view);
        initView();
        setListeners();

        return dialog;
    }

    private void findView(View view) {
        save = view.findViewById(R.id.btn_save);
        cancel = view.findViewById(R.id.btn_cancle);
        mTitle = view.findViewById(R.id.title_edittext);
        mDescription = view.findViewById(R.id.description_edittext);
        mButtonDatePicker = view.findViewById(R.id.btn_date);
        mButtonTimePicker = view.findViewById(R.id.btn_time);
    }

    private void initView() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        date = new Date();
        mButtonDatePicker.setText(simpleDateFormat.format(date));
        mButtonTimePicker.setText(simpleDateFormat1.format(date));
    }

    private void setListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.setId(UUID.randomUUID());
                mTask.setTitle(mTitle.getText().toString());
                mTask.setDescription(mDescription.getText().toString());
                mTask.setDate(date);
                mTask.setTaskType(taskType);
                mTaskRepository.insertTask(mTask);
                sendResult();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onTaskDatePicker(AddTaskDialogFragment.this, mTask);
            }
        });

        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onTaskTimePicker(AddTaskDialogFragment.this, mTask);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PiCKER) {
            Date userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            updateTaskDate(userSelectedDate);

        }

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Long userSelectedTime =
                    data.getLongExtra(TimePickerFragment.USER_SELECTED_TIME, 0);

            updateTaskTime(userSelectedTime);
        }

    }

    public void updateTaskDate(Date userSelectedDate) {
        mTask.setDate(userSelectedDate);
        mButtonDatePicker.setText(
                new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
    }

    public void updateTaskTime(Long userSelectedTime) {
        mTask.getDate().setTime(userSelectedTime);
        mButtonTimePicker.setText(
                new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();

        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    public interface Callbacks{
        void onTaskDatePicker(Fragment fragment, Task task);

        void onTaskTimePicker(Fragment fragment, Task task);
    }
}
