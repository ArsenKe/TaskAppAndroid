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

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.utilities.CustomDatePicker;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class EditCheckListActivity extends ReturnOptionActivity {

    private EditText title;
    private EditText description;
    private Spinner spinnerStatus;
    private Spinner spinnerNotification;
    private Spinner spinnerNotify;
    private Spinner spinnerColor;
    private CustomDatePicker datePicker;
    private Button editBtn;
    private TaskViewModel taskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Check List");

        taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);
        title = findViewById(R.id.edit_title_id);
        description = findViewById(R.id.edit_description_id);
        spinnerStatus = findViewById(R.id.choose_statusSpinner_id);
        spinnerNotification = findViewById(R.id.choose_notificationSpinner_id);
        spinnerNotify = findViewById(R.id.choose_notifySpinner_id);
        spinnerColor = findViewById(R.id.choose_colorSpinner_id);
        editBtn = findViewById(R.id.create_taskBtn_id);
        Serializable serializableTask = getIntent().getSerializableExtra("editTask");
        TaskDBObj taskDBObjTask = (TaskDBObj) serializableTask;
        CheckList task = (CheckList) taskDBObjTask.getTask();

        title.setText(task.getTitle());
        description.setText(task.getDescription());

        datePicker = new CustomDatePicker(EditCheckListActivity.this, R.id.editBtn_date_id, R.id.editBtn_time_id, task.getDateTime());

        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerStatus, R.array.statusTypes, task.getStatus().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotification, R.array.notificationType, task.getNotification().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerNotify, R.array.notifyType, task.getNotify().ordinal());
        SpinnerUtils.initSpinner(this.getApplicationContext(), spinnerColor, R.array.colorType, task.getColor().ordinal());

        editBtn.setText("Edit");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTitle(title.getText().toString());
                task.setDescription(description.getText().toString());
                task.setStatus(ETaskStatus.valueOf((String) spinnerStatus.getSelectedItem()));
                task.setNotification(ETaskNotification.valueOf((String) spinnerNotification.getSelectedItem()));
                task.setNotify(ETaskNotify.valueOf((String) spinnerNotify.getSelectedItem()));
                task.setColor(ETaskColor.valueOf((String) spinnerColor.getSelectedItem()));
                task.setDateTime(datePicker.getDate());
                taskViewModel.update(taskDBObjTask);
                switchToEditToDoListActivity(task);
            }
        });
    }

    private void switchToEditToDoListActivity(CheckList task){
        Intent switchToMainActivityItem = new Intent(EditCheckListActivity.this, EditToDoListActivity.class);
        switchToMainActivityItem.putExtra("list", task);
        startActivity(switchToMainActivityItem);
    }
}