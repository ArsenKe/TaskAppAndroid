package at.ac.univie.taskmanager.models.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;

public class CompositeTask extends Task {
    private List<Task> listOfTasks;

    public CompositeTask(String title, String description, LocalDateTime dateTime, ETaskStatus status,
                         ETaskNotification notification, ETaskNotify notify, ETaskColor color, String note) {
        super(title, description, dateTime, status, notification, notify, color, note);
        this.listOfTasks = new ArrayList<>();
    }

    public CompositeTask(String title, String description, LocalDateTime dateTime, ETaskStatus status,
                         ETaskNotification notification, ETaskNotify notify, ETaskColor color, String note,
                         List<Task> listOfTasks) {
        super(title, description, dateTime, status, notification, notify, color, note);
        this.listOfTasks = listOfTasks;
    }

    @Override
    public void add(Task subTask) {
        listOfTasks.add(subTask);
    }

    @Override
    public void remove(Task subTask) {
        listOfTasks.remove(subTask);
    }

    @Override
    public void removeAll() {
        listOfTasks.clear();
    }

    @Override
    public String toString() {
        return "CompositeTask{" +
                "listOfTasks=" + listOfTasks +
                '}';
    }

    public List<Task> getSubtasks() {
        return listOfTasks;
    }
}
