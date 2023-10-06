package at.ac.univie.taskmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.TaskAdapter;
import at.ac.univie.taskmanager.adapters.TaskAdapterTemplate;
import at.ac.univie.taskmanager.models.tasks.CompositeTask;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class EditSubtasksActivity extends ReturnOptionActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subtasks);


        // ViewModel for MVVM pattern. It is used to manipulate tasks
        TaskViewModel taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // initialize the recycler view holding the tasks
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // adapt the tasks to initial listview
        TaskAdapterTemplate adapter = new TaskAdapter(this, taskViewModel);
        recyclerView.setAdapter(adapter);

        Task editTask = (Task) getIntent().getSerializableExtra("editTask");
        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");
        CompositeTask mainTask = (CompositeTask) getIntent().getSerializableExtra("mainTask");
        if(!mainTask.equals(editTask)) {
            mainTask.remove(oldTask);
            mainTask.add(editTask);
        }

        // populate adapter with tasks from database
        adapter.setTasks(mainTask.getSubtasks(), true);
        adapter.setMainTask(mainTask);
        adapter.setOldTask(oldTask);


        Button done = findViewById(R.id.doneEdit_taskBtn_id);
        done.setOnClickListener(view -> {
            TaskDBObj taskDB = new TaskDBObj(mainTask);
            taskDB.id = (int) mainTask.getId();
            taskViewModel.update(taskDB);
            switchToMainActivity();
        });
    }

    private void switchToMainActivity(){
        Intent switchToMainActivityItem = new Intent(EditSubtasksActivity.this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}
