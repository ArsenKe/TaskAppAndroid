package at.ac.univie.taskmanager.proxy;

import java.util.ArrayList;

import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;

public class ProxyTaskUpdateHandler extends TaskUpdateHandler {
    private TaskUpdateHandler realUpdateHandler;

    public ProxyTaskUpdateHandler() {
        realUpdateHandler = new RealTaskUpdateHandler();
    }

    @Override
    public void updateStatus(ArrayList<Task> tasks, ETaskStatus status) {
        for(var task : tasks) {
            if(task.getStatus().equals(status)) {
                continue;
            } else {
                updateStatus(task, status);
            }
        }
    }

    @Override
    public void updateStatus(Task task, ETaskStatus status) {
        realUpdateHandler.updateStatus(task, status);
    }


}
