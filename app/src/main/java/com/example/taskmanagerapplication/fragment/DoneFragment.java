package com.example.taskmanagerapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.Repository.TaskRepository;
import com.example.taskmanagerapplication.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoneFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DoneTaskAdapter mDoneTaskAdapter;
    private TaskRepository mTaskRepository;
    private Task mTaskDone;
    private ImageView mImageView;
    private TextView mTextView;
    private FloatingActionButton mFloatingActionButton;
    private String mStringTaskType = "Done";

    public DoneFragment() {
        // Required empty public constructor
    }

    public static DoneFragment newInstance() {
        DoneFragment fragment = new DoneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        findViews(view);
        initViews();
        setListeners();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.menu_item_clearAll){
            while (mTaskRepository.getTasks().size()!=0){
                List<Task> list = mTaskRepository.getTasks();
                Task task = list.get(0);
                mTaskRepository.deleteTask(task);
            }
            initViews();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.DoneRecyclerView);
        mImageView = view.findViewById(R.id.imageView);
        mTextView = view.findViewById(R.id.textview);
        mFloatingActionButton = view.findViewById(R.id.floatingActionButton_done);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        List<Task> tasks = getDoneTasks();

        if (tasks.size() == 0) {
            mImageView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);
        }

        if (mDoneTaskAdapter == null) {
            mDoneTaskAdapter = new DoneTaskAdapter(tasks);
            mRecyclerView.setAdapter(mDoneTaskAdapter);
        } else {
            mDoneTaskAdapter.setTasks(tasks);
            mDoneTaskAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private TextView mImageViewSolved;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.row_item_task_title);
            mTextViewDate = itemView.findViewById(R.id.row_item_task_date);
            mImageViewSolved = itemView.findViewById(R.id.action_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //todo
                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mTaskDone = task;
            mTextViewTitle.setText(task.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = task.getDate();
            mTextViewDate.setText(simpleDateFormat.format(date));
            mImageViewSolved.setVisibility(View.VISIBLE);
            mImageViewSolved.setText(Character.toString(mTask.getTitle().charAt(0)));
        }
    }

    private class DoneTaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public DoneTaskAdapter(List<Task> tasks) {
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
                //openDialog();
            }
        });
    }

    /*public void openDialog() {
        AddTaskDialogFragment taskDialogFragment =
                new AddTaskDialogFragment(getActivity(),mStringTaskType);
        taskDialogFragment.show();
    }*/

    public List<Task> getDoneTasks(){
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