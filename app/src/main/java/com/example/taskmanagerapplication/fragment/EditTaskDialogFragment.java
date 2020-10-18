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
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.Repository.TaskRepository;
import com.example.taskmanagerapplication.model.Task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EditTaskDialogFragment extends DialogFragment {

    public static final String TODO = "ToDo";
    public static final String DOING = "Doing";
    public static final String DONE = "Done";

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    private Activity mActivity;
    private Callbacks mCallbacks;
    private Task mTask;
    private Dialog mDialog;
    private EditText mTitle, mDescription;
    private Button save, edit, delet;
    private Button mButtonDatePicker, mButtonTimePicker;
    private CheckBox mCheckBoxTodo, mCheckBoxDoing, mCheckBoxDone;
    private TaskRepository mTaskRepository = TaskRepository.getInstance(getContext());

    public EditTaskDialogFragment() {

    }

    public static EditTaskDialogFragment newInstance(UUID id) {
        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("arg_task_edit", (Serializable) id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        UUID uuid = (UUID) getArguments().getSerializable("arg_task_edit");
        mTask = mTaskRepository.getTask(uuid);
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
        View view = inflater.inflate(R.layout.edit_dialog_layout, null);

        @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Edit Task")
                .setView(view);

        AlertDialog dialog = builder.create();

        findView(view);
        initView();
        setListeners();

        return dialog;
    }

    private void findView(View view){
        save = view.findViewById(R.id.btn_save);
        edit = view.findViewById(R.id.btn_edit);
        delet = view.findViewById(R.id.btn_delete);
        mTitle = view.findViewById(R.id.title_edittext);
        mDescription = view.findViewById(R.id.description_edittext);
        mButtonDatePicker = view.findViewById(R.id.btn_date);
        mButtonTimePicker = view.findViewById(R.id.btn_time);
        mCheckBoxTodo = view.findViewById(R.id.checkbox_task_todo);
        mCheckBoxDoing = view.findViewById(R.id.checkbox_task_doing);
        mCheckBoxDone = view.findViewById(R.id.checkbox_task_done);
    }

    private void initView(){
        mTitle.setEnabled(false);
        mDescription.setEnabled(false);
        mButtonDatePicker.setEnabled(false);
        mButtonTimePicker.setEnabled(false);
        mTitle.setText(mTask.getTitle());
        mDescription.setText(mTask.getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        Date date = mTask.getDate();
        mButtonDatePicker.setText(simpleDateFormat.format(date));
        mButtonTimePicker.setText(simpleDateFormat1.format(date));
    }

    private void setListeners(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID id = UUID.randomUUID();
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                Date date = mTask.getDate();
                String stringType;
                if (mCheckBoxDoing.isChecked()) {
                    stringType = DOING;
                } else if (mCheckBoxDone.isChecked()) {
                    stringType = DONE;
                } else {
                    stringType = TODO;
                }
                Task task = new Task(id, title, description, date, stringType);
                mTaskRepository.deleteTask(mTask);
                mTaskRepository.insertTask(task);
                sendResult();
                dismiss();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle.setEnabled(true);
                mDescription.setEnabled(true);
                mButtonDatePicker.setEnabled(true);
                mButtonTimePicker.setEnabled(true);
            }
        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskRepository.deleteTask(mTask);
                sendResult();
                dismiss();
            }
        });

        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onTaskDatePicker(EditTaskDialogFragment.this, mTask);
            }
        });

        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onTaskTimePicker(EditTaskDialogFragment.this, mTask);
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
