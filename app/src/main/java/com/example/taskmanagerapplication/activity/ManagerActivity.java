package com.example.taskmanagerapplication.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.fragment.AddTaskDialogFragment;
import com.example.taskmanagerapplication.fragment.DatePickerFragment;
import com.example.taskmanagerapplication.fragment.DoingFragment;
import com.example.taskmanagerapplication.fragment.DoneFragment;
import com.example.taskmanagerapplication.fragment.EditTaskDialogFragment;
import com.example.taskmanagerapplication.fragment.TimePickerFragment;
import com.example.taskmanagerapplication.fragment.ToDoFragment;
import com.example.taskmanagerapplication.model.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class ManagerActivity extends AppCompatActivity implements ToDoFragment.Callbacks,
        EditTaskDialogFragment.Callbacks, AddTaskDialogFragment.Callbacks{

    public static final String FRAGMENT_TAG_DATE_PICKER = "Fragment_tag_date_picker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "Fragment_tag_time_picker";
    public static final String FRAGMENT_TAG_ADD_TASK_DIALOG = "fragment_add_task_dialog";
    public static final String FRAGMENT_TAG_EDIT_TASK_DIALOG = "fragment_edit_task_dialog";
    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final int REQUEST_CODE_ADD_TASK = 2;
    public static final int REQUEST_CODE_EDIT_TASK = 3;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    PageAdapter mPageAdapter;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        findViews();
        initView();
        //setListeners();
    }

    private void findViews() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
    }

    private void initView() {
        mPageAdapter = new PageAdapter(this);
        mViewPager.setAdapter(mPageAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText("TODO");
                                break;
                            }
                            case 1: {
                                tab.setText("DOING");
                                break;
                            }
                            case 2: {
                                tab.setText("DONE");
                                break;
                            }
                        }
                    }
                });

        tabLayoutMediator.attach();

    }

    private class PageAdapter extends FragmentStateAdapter {


        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    currentFragment = ToDoFragment.newInstance();
                    return currentFragment;
                case 1:
                    currentFragment = DoingFragment.newInstance();
                    return currentFragment;
                case 2:
                    currentFragment = DoneFragment.newInstance();
                    return currentFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }

    @Override
    public void addTaskFragment(Fragment fragment, String string) {
        AddTaskDialogFragment addTaskDialogFragment =
                AddTaskDialogFragment.newInstance(currentFragment,string);
        addTaskDialogFragment.setTargetFragment(fragment, REQUEST_CODE_ADD_TASK);
        addTaskDialogFragment.show(getSupportFragmentManager(), FRAGMENT_TAG_ADD_TASK_DIALOG);
    }

    @Override
    public void editTaskFragment(Fragment fragment, UUID id) {
        EditTaskDialogFragment editTaskDialogFragment =
                EditTaskDialogFragment.newInstance(id);
        editTaskDialogFragment.setTargetFragment(fragment, REQUEST_CODE_EDIT_TASK);
        editTaskDialogFragment.show(getSupportFragmentManager(), FRAGMENT_TAG_EDIT_TASK_DIALOG);
    }

    @Override
    public void onTaskDatePicker(Fragment fragment, Task task) {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(task.getDate());
        datePickerFragment.setTargetFragment( fragment, REQUEST_CODE_DATE_PiCKER );
        datePickerFragment.show(getSupportFragmentManager(), FRAGMENT_TAG_DATE_PICKER);
    }

    @Override
    public void onTaskTimePicker(Fragment fragment, Task task) {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(task.getDate());
        timePickerFragment.setTargetFragment( currentFragment, REQUEST_CODE_TIME_PICKER );
        timePickerFragment.show(getSupportFragmentManager() , FRAGMENT_TAG_TIME_PICKER);
    }

}