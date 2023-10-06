package at.ac.univie.taskmanager.views;

import android.content.Context;
import android.os.Bundle;

import java.util.Map;

import at.ac.univie.taskmanager.models.tasks.Task;

public abstract class TaskFragment {

    public TaskFragment(AddTaskView view, Context ctx) {
        super();
    }

    public abstract Task createTask(Map<String, Object> taskMap);
    public Task newTask(Map<String, Object> taskMap) {
        Task task = createTask(taskMap);
        return task;
    }
}
