package at.ac.univie.taskmanager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.converters.Converters;
import at.ac.univie.taskmanager.converters.EnumConverters;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.proxy.ProxyTaskUpdateHandler;
import at.ac.univie.taskmanager.proxy.TaskUpdateHandler;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class UpdateSelectedTasks extends ReturnOptionActivity {

    private TaskUpdateHandler updateHandler = new ProxyTaskUpdateHandler();
    private Spinner spinnerUpdateAllSelectedTaskStatus;
    private TaskViewModel viewModel;
    private Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_selected_tasks);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Task");

        spinnerUpdateAllSelectedTaskStatus = findViewById(R.id.choose_update_statusSpinner_id);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.statusTypes, android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateAllSelectedTaskStatus.setAdapter(statusAdapter);

        updateBtn = findViewById(R.id.update_taskBtn_id);

        Intent intent = getIntent();
        ArrayList<Task> tasksToUpdate = Converters.bytesToTasks(intent.getExtras().getByteArray("update"));

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETaskStatus statusToUpdate = EnumConverters.convertStringToEStatus(spinnerUpdateAllSelectedTaskStatus.getSelectedItem().toString());
                updateHandler.updateStatus(tasksToUpdate, statusToUpdate);
                for(var task : tasksToUpdate) {
                    Log.i("Tasks for update", task.toString());

                    TaskDBObj s = new TaskDBObj(task);
                    s.id = (int) task.getId();
                    viewModel.update(s);
                }
                switchToMainActivity();
            }
        });

    }

    private void switchToMainActivity() {
        Intent switchToMainActivityItem = new Intent(UpdateSelectedTasks.this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}