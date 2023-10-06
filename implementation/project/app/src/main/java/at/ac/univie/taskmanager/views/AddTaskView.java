package at.ac.univie.taskmanager.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.time.LocalDateTime;
import java.util.Objects;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.utilities.CustomDatePicker;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;

/**
 * AddTaskView. Activity that gives user an option to create a task
 * based on the user input. Activity contains fragment, which extends
 * the GUI based on the provided task type.
 */
public abstract class AddTaskView extends ReturnOptionActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerStatus;
    private Spinner spinnerNotification;
    private Spinner spinnerNotify;
    private Spinner spinnerColor;
    protected TaskFragment fragment;
    protected CustomDatePicker datePicker;
    protected Task mainTask;
    private String taskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // action bar to give the user an opportunity to return to the previous view.
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        // parse the data passed from the previous activity
        Bundle extras = getIntent().getExtras();
        this.taskType = extras.getString("TASK_TYPE");
        this.mainTask = (Task) extras.getSerializable("mainTask");
        actionBar.setTitle(this.taskType);

        // On create button click the task is added to the db
        // and the app routes back to the main activity
        Button createBtn = findViewById(R.id.create_taskBtn_id);
        createBtn.setOnClickListener(view -> {
                saveTask();
                switchToNextActivity();
        });

        // Extends the GUI based on the passed task type.
        setUpTaskFragment();

        // Initialization of the page fields.
        editTextTitle = findViewById(R.id.edit_title_id);
        editTextDescription = findViewById(R.id.edit_description_id);

        // DatePicker wrapper for easier interaction and reusability
        datePicker = new CustomDatePicker(AddTaskView.this, R.id.editBtn_date_id, R.id.editBtn_time_id, LocalDateTime.now());

        // Here we set up different adapters for the spinners
        spinnerStatus = findViewById(R.id.choose_statusSpinner_id);
        ArrayAdapter<CharSequence> statusAdapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.statusTypes);
        spinnerStatus.setAdapter(statusAdapter);

        spinnerNotification = findViewById(R.id.choose_notificationSpinner_id);
        ArrayAdapter<CharSequence> notificationAdapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.notificationType);
        spinnerNotification.setAdapter(notificationAdapter);

        spinnerNotify = findViewById(R.id.choose_notifySpinner_id);
        ArrayAdapter<CharSequence> notifyAdapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.notifyType);
        spinnerNotify.setAdapter(notifyAdapter);

        spinnerColor = findViewById(R.id.choose_colorSpinner_id);
        ArrayAdapter<CharSequence> colorAdapter = SpinnerUtils.setUpAdapter(this.getApplicationContext(), R.array.colorType);
        spinnerColor.setAdapter(colorAdapter);
    }

    protected abstract void switchToNextActivity();

    /**
     * Extends the GUI based on the task type passed to this activity.
     * Adds a fragment for a specific task type, which contains the
     * needed graphical elements and implements the factory method pattern
     */
    private void setUpTaskFragment() {
        switch (taskType) {
            case "Appointment":
                fragment = new AppointmentFragment(this, this.getApplicationContext());
                break;
            case "CheckList":
                fragment = new CheckListFragment(this, this.getApplicationContext());
                break;
            case "CompositeTask":
                fragment = new CompositeTaskFragment(this, this.getApplicationContext());
                break;
            default:
                throw new IllegalArgumentException("Unsupported fragment type");
        }
    }


    /**
     * Method is used to parse the data from the view, validate it and to create a task based on it.
     */
    public void saveTask() {
        // parse data from the text views and validate it
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Must not be empty ....", Toast.LENGTH_SHORT).show();
            return;
        }

        // parse data from the spinners (already validated based on the resources specification)
        ETaskStatus status = ETaskStatus.valueOf((String) spinnerStatus.getSelectedItem());
        ETaskNotification notification = ETaskNotification.valueOf((String)spinnerNotification.getSelectedItem());
        ETaskNotify notify = ETaskNotify.valueOf((String)spinnerNotify.getSelectedItem().toString());
        ETaskColor color = ETaskColor.valueOf((String) spinnerColor.getSelectedItem().toString());

        processTask(title, description, status, notification, notify, color);
    }

    /**
     * Based on the user input creates a map that is further passed to the factory method of
     * the fragment initialized in this activity. Stores the task into the database after
     * its creation.
     *
     * @param title Validated title provided by the user
     * @param description Validated description provided by the user
     * @param status Validated status provided by the user
     * @param notification Validated notification provided by the user
     * @param notify Validated notify provided by the user
     * @param color Validated color provided by the user
     */
    protected abstract void processTask(String title, String description, ETaskStatus status, ETaskNotification notification, ETaskNotify notify, ETaskColor color);
}
