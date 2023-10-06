package at.ac.univie.taskmanager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.SelectTaskAllAdapter;
import at.ac.univie.taskmanager.converters.Converters;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class UpdateTaskActivity extends ReturnOptionActivity {

    private TaskViewModel taskViewModel;
    private Button updateAllSelectedTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_all_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select All Tasks");

        RecyclerView recyclerView = findViewById(R.id.select_all_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);
        SelectTaskAllAdapter adapter = new SelectTaskAllAdapter(taskViewModel, this);
        recyclerView.setAdapter(adapter);
        adapter.setTasks(taskViewModel.getAllTasks());

        updateAllSelectedTask = findViewById(R.id.select_all_task_updateBtn_id);
        updateAllSelectedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Task> checkedTaskList = adapter.checkedTaskList();
                switchToSelectAllTaskActivity(checkedTaskList);
            }
        });
    }

    private void switchToSelectAllTaskActivity(ArrayList<Task> checkedTaskList){
        Intent switchToUpdateAllSelectTaskItem = new Intent(UpdateTaskActivity.this, UpdateSelectedTasks.class);
        switchToUpdateAllSelectTaskItem.putExtra("update", Converters.tasksToBytes(checkedTaskList));
        startActivity(switchToUpdateAllSelectTaskItem);
    }
}