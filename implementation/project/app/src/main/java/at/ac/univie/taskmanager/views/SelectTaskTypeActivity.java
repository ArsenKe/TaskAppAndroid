package at.ac.univie.taskmanager.views;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Objects;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;


/**
 * SelectTaskTypeActivity. Before adding a new task the user needs to select a type of
 * task he is going to add. The types include:
 *
 * Appointment
 * CheckList
 * CompositeTask
 *
 * User simply selects an option from the list and proceeds with task creation.
 */
public class SelectTaskTypeActivity extends ReturnOptionActivity {

    private Spinner spinnerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task_type);

        // Initializes the action bar and sets the title
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.select_task_title);

        // Initializes the spinner with options to select different task types
        spinnerTask = findViewById(R.id.chooseTaskSpinner);
        ArrayAdapter<CharSequence> adapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.taskTypes);
        spinnerTask.setAdapter(adapter);

        // Select button. On select the app routes to the create task activity
        Button selectTaskBtn = findViewById(R.id.chooseTaskAddBtn);
        selectTaskBtn.setOnClickListener(view -> {
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
        String selectTaskType = spinnerTask.getSelectedItem().toString();
        Intent switchToAddTaskActivity = new Intent(this, AddFinalTaskView.class);
        switchToAddTaskActivity.putExtra("TASK_TYPE", selectTaskType);
        startActivity(switchToAddTaskActivity);
    }
}