package at.ac.univie.taskmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;

import java.util.Objects;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;

public class SubtasksActivity extends ReturnOptionActivity {

    private Spinner taskTypeSelector;
    private Task mainTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task_type);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Subtask");

        mainTask = (Task) getIntent().getSerializableExtra("mainTask");

        // Initializes the spinner with options to select different task types
        taskTypeSelector = findViewById(R.id.chooseTaskSpinner);
        ArrayAdapter<CharSequence> adapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.taskTypesNoComposite);
        taskTypeSelector.setAdapter(adapter);


        // Show the button to finish the creation of the task
        Button doneBtn = findViewById(R.id.doneButton);
        doneBtn.setVisibility(View.VISIBLE);
        doneBtn.setOnClickListener(view -> {
            switchToMainActivity();
        });

        Button addBtn = findViewById(R.id.chooseTaskAddBtn);
        addBtn.setOnClickListener(view -> {
            switchToCreateTaskActivity();
        });
    }

    /**
     * Starts AddTaskActivity. Simply navigates to the next activity.
     *
     * Additionally the task type is passed so the GUI of the
     * activity can be adapted to the specific task type.
     */
    private void switchToCreateTaskActivity() {
        String selectTaskType = taskTypeSelector.getSelectedItem().toString();
        Intent switchToAddTaskActivity = new Intent(this, AddSubTaskView.class);
        switchToAddTaskActivity.putExtra("TASK_TYPE", selectTaskType);
        switchToAddTaskActivity.putExtra("mainTask", mainTask);
        startActivity(switchToAddTaskActivity);
    }

    private void switchToMainActivity(){
        Intent switchToMainActivityItem = new Intent(this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}
