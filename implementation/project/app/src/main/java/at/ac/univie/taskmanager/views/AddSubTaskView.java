package at.ac.univie.taskmanager.views;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class AddSubTaskView extends AddTaskView {

    @Override
    protected void processTask(String title, String description, ETaskStatus status, ETaskNotification notification, ETaskNotify notify, ETaskColor color) {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("description", description);
        taskMap.put("date", datePicker.getDate());
        taskMap.put("status", status);
        taskMap.put("notification", notification);
        taskMap.put("notify", notify);
        taskMap.put("color", color);

        TaskViewModel taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);
        Task task = fragment.newTask(taskMap);
        mainTask.add(task);
        TaskDBObj taskDb = new TaskDBObj(mainTask);
        taskDb.id = (int) mainTask.getId();
        taskViewModel.update(taskDb);
    }

    protected void switchToNextActivity() {
        Intent switchToNextActivity = new Intent(this, SubtasksActivity.class);
        switchToNextActivity.putExtra("mainTask", mainTask);
        startActivity(switchToNextActivity);
    }
}
