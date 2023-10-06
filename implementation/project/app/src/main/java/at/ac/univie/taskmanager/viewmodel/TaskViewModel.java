package at.ac.univie.taskmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.repository.TaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private List<Task> allTasks;

    public TaskViewModel(@NonNull Application app) {
        super(app);
        taskRepository = new TaskRepository(app);
        allTasks = taskRepository.getAllTasks();
    }

    public long insert(Task task) { return taskRepository.insert(task); }

    public void update(TaskDBObj task){
        taskRepository.update(task);
    }

    public void deleteById(long id) { taskRepository.deleteById(id); }

    public List<Task> getAllTasks() {
        return allTasks;
    }

}
