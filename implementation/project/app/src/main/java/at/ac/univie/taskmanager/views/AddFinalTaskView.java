package at.ac.univie.taskmanager.views;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

/**
 * AddFinalTaskView. The view is responsible for the collection and processing of
 * user input for task creation. It is responsible only for "final" tasks, hence
 * not subtasks.
 *
 * For subtasks: {@see AddSubTaskView}
 */
public class AddFinalTaskView extends AddTaskView {

    /**
     * Method is responsible for the appropriate task creation.
     * ViewModel stores the final task that can optionally
     * be composed of multiple subtasks into the database.
     *
     * @param title Validated title provided by the user
     * @param description Validated description provided by the user
     * @param status Validated status provided by the user
     * @param notification Validated notification provided by the user
     * @param notify Validated notify provided by the user
     * @param color Validated color provided by the user
     */
    @Override
    protected void processTask(String title, String description, ETaskStatus status, ETaskNotification notification, ETaskNotify notify, ETaskColor color) {
        // create a map from task for factory method pattern
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("description", description);
        taskMap.put("date", datePicker.getDate());
        taskMap.put("status", status);
        taskMap.put("notification", notification);
        taskMap.put("notify", notify);
        taskMap.put("color", color);

        // get the viewmodel and store the task into the database
        TaskViewModel taskViewModel =  new ViewModelProvider(this).get(TaskViewModel.class);
        Task task = fragment.newTask(taskMap);
        long id = taskViewModel.insert(task);
        task.setId(id);
    }

    @Override
    protected void switchToNextActivity() {
        switchToMainActivity();
    }

    /**
     * Starts MainActivity. Simply navigates to the next activity.
     */
    private void switchToMainActivity(){
        Intent switchToMainActivityItem = new Intent(this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}
