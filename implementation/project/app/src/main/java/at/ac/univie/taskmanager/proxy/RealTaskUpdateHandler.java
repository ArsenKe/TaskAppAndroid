package at.ac.univie.taskmanager.proxy;

import java.util.ArrayList;

import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;

public class RealTaskUpdateHandler extends TaskUpdateHandler {
    @Override
    public void updateStatus(ArrayList<Task> tasks, ETaskStatus status) {
        for(var task : tasks) {
            updateStatus(task, status);
        }
    }

    @Override
    public void updateStatus(Task task, ETaskStatus status) {
        task.setStatus(status);
    }
}
