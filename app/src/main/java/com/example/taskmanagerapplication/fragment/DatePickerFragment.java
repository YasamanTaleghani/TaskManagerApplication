package com.example.taskmanagerapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.taskmanagerapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    public static final String ARG_TASK_DATE = "Arg_Task_Date";
    public static final String EXTRA_USER_SELECTED_DATE = "Extra_User_selected_date";
    private Date mDateTask;
    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date dateTask) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_DATE, dateTask);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDateTask = (Date) getArguments().getSerializable(ARG_TASK_DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);

        findViews(view);
        initViews();

        return view;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker, null);

        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Date")
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date userSelectedDate = extractDateFromDatePicker();
                        sendResult(userSelectedDate);

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker_task);
    }

    private void initViews() {
        initDatePicker();
    }

    private void initDatePicker() {
        // i have a date and i want to set it in date picker.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDateTask);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }

    private Date extractDateFromDatePicker() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();

        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        return calendar.getTime();
    }

    private void sendResult(Date userSelectedDate) {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}