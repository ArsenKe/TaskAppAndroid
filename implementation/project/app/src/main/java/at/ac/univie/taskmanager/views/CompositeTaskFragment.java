package at.ac.univie.taskmanager.views;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.CompositeTask;
import at.ac.univie.taskmanager.models.tasks.Task;

public class CompositeTaskFragment extends TaskFragment {

    private Task task;

    public CompositeTaskFragment(AddTaskView view, Context ctx) {
        super(view, ctx);

        Button createBtn = view.findViewById(R.id.create_taskBtn_id);
        createBtn.setOnClickListener(v -> {
            view.saveTask();
            switchToNext(view);
        });
    }

    @Override
    public Task createTask(Map<String, Object> taskMap) {
        this.task = new CompositeTask(
                (String) taskMap.get("title"), (String) taskMap.get("description"), (LocalDateTime) taskMap.get("date"),
                (ETaskStatus) taskMap.get("status"), (ETaskNotification) taskMap.get("notification"), (ETaskNotify) taskMap.get("notify"),
                (ETaskColor) taskMap.get("color"), "note", new ArrayList<>()
        );
        return this.task;
    }

    private void switchToNext(AddTaskView view) {
        Intent switchToNextActivityItem = new Intent(view, SubtasksActivity.class);
        switchToNextActivityItem.putExtra("mainTask", task);
        view.startActivity(switchToNextActivityItem);
    }
}
