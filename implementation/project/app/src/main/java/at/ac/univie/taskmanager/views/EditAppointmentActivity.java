package at.ac.univie.taskmanager.views;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskPriority;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Appointment;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.utilities.CustomDatePicker;
import at.ac.univie.taskmanager.utilities.DefaultTextViewStyler;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;
import at.ac.univie.taskmanager.utilities.Styler;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;


/**
 * EditAppointmentActivity. The activity enables the option to
 * edit the appointment. It creates the EditText and Spinner elements
 * and initializes them with values from the provided appointment task.
 *
 * The user is able to change them and to apply his changes.
 */
public class EditAppointmentActivity extends ReturnOptionActivity {

    private EditText title;
    private EditText description;
    private Spinner spinnerStatus;
    private Spinner spinnerNotification;
    private Spinner spinnerNotify;
    private Spinner spinnerColor;
    private Spinner spinnerPriority;
    private EditText editTextLocation;
    private TaskViewModel taskViewModel;
    private CustomDatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // helper object to be consistent in styling of the textviews
        Styler styler = new DefaultTextViewStyler();

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Appointment");

        taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);

        // parse the graphical elements by ids
        title = findViewById(R.id.edit_title_id);
        description = findViewById(R.id.edit_description_id);
        spinnerStatus = findViewById(R.id.choose_statusSpinner_id);
        spinnerNotification = findViewById(R.id.choose_notificationSpinner_id);
        spinnerNotify = findViewById(R.id.choose_notifySpinner_id);
        spinnerColor = findViewById(R.id.choose_colorSpinner_id);
        Button editBtn = findViewById(R.id.create_taskBtn_id);

        // parse the task from extras
        TaskDBObj taskDBObjTask = (TaskDBObj) getIntent().getSerializableExtra("editTask");
        Appointment task = (Appointment) taskDBObjTask.getTask();

        // for proper subtasks handling we also pass the subtask flag
        // if True the mainTask (owner of subtasks) and oldTask (task before editing)
        // are also sent
        boolean subtask = getIntent().getBooleanExtra("subtask", false);
        Task mainTask = (Task) getIntent().getSerializableExtra("mainTask");
        Task oldTask = (Task) getIntent().getSerializableExtra("oldTask");


        // initialize the default values

        // use helper class for date picker creation
        datePicker = new CustomDatePicker(EditAppointmentActivity.this, R.id.editBtn_date_id, R.id.editBtn_time_id, task.getDateTime());

        // set initial string values
        title.setText(task.getTitle());
        description.setText(task.getDescription());

        // initialize spinners with the values from task
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerStatus, R.array.statusTypes, task.getStatus().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotification, R.array.notificationType, task.getNotification().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotify, R.array.notifyType, task.getNotify().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerColor, R.array.colorType, task.getColor().ordinal());

        // define layout specific to appointment
        LinearLayout priorityLayout = EditAppointmentActivity.this.findViewById(R.id.priority_layout_id);
        TextView priorityLabel = new TextView(EditAppointmentActivity.this);
        styler.styleTextView(priorityLabel, R.string.priority_label);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(EditAppointmentActivity.this, R.array.priorityType, android.R.layout.simple_spinner_dropdown_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPriority = new Spinner(EditAppointmentActivity.this);
        int indexPriority = task.getPriority().ordinal();
        spinnerPriority.setAdapter(priorityAdapter);
        spinnerPriority.setGravity(Gravity.CENTER);
        spinnerPriority.setMinimumWidth(styler.getWidth());
        spinnerPriority.setSelection(indexPriority);

        priorityLayout.addView(priorityLabel);
        priorityLayout.addView(spinnerPriority);

        LinearLayout locationLayout = EditAppointmentActivity.this.findViewById(R.id.location_layout_id);
        TextView locationLabel = new TextView(EditAppointmentActivity.this);
        styler.styleTextView(locationLabel, R.string.location_label);

        editTextLocation = new EditText(EditAppointmentActivity.this);
        editTextLocation.setMinimumWidth(styler.getWidth());
        editTextLocation.setMaxLines(1);
        editTextLocation.setText(task.getLocation());

        locationLayout.addView(locationLabel);
        locationLayout.addView(editTextLocation);


        // update the task on edit button click
        editBtn.setText("Edit");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTitle(title.getText().toString());
                task.setDescription(description.getText().toString());
                task.setDateTime(datePicker.getDate());
                task.setStatus(ETaskStatus.valueOf((String) spinnerStatus.getSelectedItem()));
                task.setNotification(ETaskNotification.valueOf((String) spinnerNotification.getSelectedItem()));
                task.setNotify(ETaskNotify.valueOf((String) spinnerNotify.getSelectedItem()));
                task.setColor(ETaskColor.valueOf((String) spinnerColor.getSelectedItem()));
                task.setPriority(ETaskPriority.valueOf((String) spinnerPriority.getSelectedItem()));
                task.setLocation(editTextLocation.getText().toString());

                if(subtask) { // if it is a subtask switch to the edit subtasks activity
                    switchToEditSubtasksActivity(task, mainTask, oldTask);
                } else { // not a subtask - simply update the task in the database
                    taskViewModel.update(taskDBObjTask);
                    switchToMainActivity();
                }
            }
        });
    }

    private void switchToEditSubtasksActivity(Task task, Task mainTask, Task oldTask) {
        Intent switchToEditSubtasksItem = new Intent(EditAppointmentActivity.this, EditSubtasksActivity.class);
        switchToEditSubtasksItem.putExtra("editTask", task);
        switchToEditSubtasksItem.putExtra("oldTask", oldTask);
        switchToEditSubtasksItem.putExtra("mainTask", mainTask);
        startActivity(switchToEditSubtasksItem);
    }

    private void switchToMainActivity() {
        Intent switchToMainActivityItem = new Intent(EditAppointmentActivity.this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}