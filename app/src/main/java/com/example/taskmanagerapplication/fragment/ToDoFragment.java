package com.example.taskmanagerapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.UUID;


public class ToDoFragment extends Fragment {

    public static final String FRAGMENT_TAG_ADD_TASK_DIALOG = "fragment_add_task_dialog";
    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final int REQUEST_CODE_ADD_TASK = 2;
    public static final int REQUEST_CODE_EDIT_TASK = 3;

    private RecyclerView mRecyclerView;
    private ToDoTaskAdapter mToDoTaskAdapter;
    private TaskRepository mTaskRepository;
    private ImageView mImageView;
    private TextView mTextView;
    private Task mTaskToDo;
    private FloatingActionButton mFloatingActionButton;
    private String mStringTaskType = "ToDo";
    private Callbacks mCallbacks;

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
        setHasOptionsMenu(true);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + " must implement Callbacks");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_item_clearAll) {
            while (mTaskRepository.getTasks().size() != 0) {
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
        }

        if (mToDoTaskAdapter == null) {
            mToDoTaskAdapter = new ToDoTaskAdapter(tasks);
            mRecyclerView.setAdapter(mToDoTaskAdapter);
        } else {
            mToDoTaskAdapter.setTasks(tasks);
            mToDoTaskAdapter.notifyDataSetChanged();
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
                    mCallbacks.editTaskFragment(ToDoFragment.this, mTask.getId());
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
            mImageViewSolved.setText(Character.toString(mTask.getTitle().charAt(0)));
        }
    }

    private class ToDoTaskAdapter extends RecyclerView.Adapter<TaskHolder> implements Filterable {

        private List<Task> mTasks;
        private List<Task> contactListFiltered;
        private List<Task> contactList;

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

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();

                    if (charString.isEmpty()) {
                        contactListFiltered = contactList;
                    } else {
                        List<Task> filteredList = new ArrayList<>();
                        for (Task row : contactList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getDescription().toLowerCase().contains(charSequence) ||
                                    row.getDate().toString().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        contactListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = contactListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    contactListFiltered = (ArrayList<Task>) filterResults.values;

                    // refresh the list with filtered data
                    notifyDataSetChanged();
                }
            };
        }
    }

    private void setListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.addTaskFragment(ToDoFragment.this ,mStringTaskType);
            }
        });
    }

    public List<Task> getToDoTasks() {
        List<Task> resultTasks = new ArrayList<>();
        List<Task> tasks = mTaskRepository.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTaskType().equals(mStringTaskType)) {
                resultTasks.add(task);
            }
        }

        return resultTasks;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode==REQUEST_CODE_ADD_TASK){
            updateUI();
        } else if (requestCode==REQUEST_CODE_EDIT_TASK){
            updateUI();
        }

    }

    public interface Callbacks {
        void addTaskFragment(Fragment fragment, String string);

        void editTaskFragment(Fragment fragment, UUID id);


    }

}