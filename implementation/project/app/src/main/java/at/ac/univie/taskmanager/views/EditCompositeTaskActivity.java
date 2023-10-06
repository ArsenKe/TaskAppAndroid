package at.ac.univie.taskmanager.views;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.CompositeTask;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.utilities.CustomDatePicker;
import at.ac.univie.taskmanager.utilities.DefaultTextViewStyler;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;
import at.ac.univie.taskmanager.utilities.Styler;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class EditCompositeTaskActivity extends ReturnOptionActivity {

    private EditText title;
    private EditText description;
    private Spinner spinnerStatus;
    private Spinner spinnerNotification;
    private Spinner spinnerNotify;
    private Spinner spinnerColor;
    private TaskViewModel taskViewModel;
    private CustomDatePicker datePicker;
    private Task mainTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Styler styler = new DefaultTextViewStyler();

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit CompositeTask");

        taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);
        title = findViewById(R.id.edit_title_id);
        description = findViewById(R.id.edit_description_id);

        spinnerStatus = findViewById(R.id.choose_statusSpinner_id);
        spinnerNotification = findViewById(R.id.choose_notificationSpinner_id);
        spinnerNotify = findViewById(R.id.choose_notifySpinner_id);
        spinnerColor = findViewById(R.id.choose_colorSpinner_id);
        Button editBtn = findViewById(R.id.create_taskBtn_id);

        Serializable serializableTask = getIntent().getSerializableExtra("editTask");
        TaskDBObj taskDBObjTask = (TaskDBObj) serializableTask;
        CompositeTask task = (CompositeTask) taskDBObjTask.getTask();
        mainTask = task;

        //I put the previous values for edit
        title.setText(task.getTitle());
        description.setText(task.getDescription());

        datePicker = new CustomDatePicker(EditCompositeTaskActivity.this, R.id.editBtn_date_id, R.id.editBtn_time_id, task.getDateTime());

        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerStatus, R.array.statusTypes, task.getStatus().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotification, R.array.notificationType, task.getNotification().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotify, R.array.notifyType, task.getNotify().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerColor, R.array.colorType, task.getColor().ordinal());

        editBtn.setText("Edit subtasks");
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

                switchToEditSubtasksActivity(task);
            }
        });

        Button doneEdit = findViewById(R.id.doneEdit_taskBtn_id);
        doneEdit.setVisibility(View.VISIBLE);
        doneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskViewModel.update(taskDBObjTask);
                switchToMainActivity();
            }
        });
    }

    private void switchToEditSubtasksActivity(CompositeTask task) {
        Intent switchToEditSubtasks = new Intent(EditCompositeTaskActivity.this, EditSubtasksActivity.class);
        switchToEditSubtasks.putExtra("editTask", task);
        switchToEditSubtasks.putExtra("mainTask", task);
        startActivity(switchToEditSubtasks);
    }

    private void switchToMainActivity(){
        Intent switchToMainActivityItem = new Intent(EditCompositeTaskActivity.this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}