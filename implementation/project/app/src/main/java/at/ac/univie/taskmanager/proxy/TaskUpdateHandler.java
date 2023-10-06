package at.ac.univie.taskmanager.proxy;

import java.util.ArrayList;

import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;

public abstract class TaskUpdateHandler {
    public abstract void updateStatus(ArrayList<Task> tasks, ETaskStatus status);
    public abstract void updateStatus(Task task, ETaskStatus status);
}
