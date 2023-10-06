package at.ac.univie.taskmanager.repository;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.database.TaskDao;
import at.ac.univie.taskmanager.database.TaskDatabase;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.models.tasks.Task;

public class TaskRepository {

    private TaskDao taskDao;
    private List<Task> allTasks;

    public TaskRepository(Application app) {
        this.allTasks = new ArrayList<>();
        TaskDatabase db = TaskDatabase.getInstance(app);
        taskDao = db.tasksDao();
        List<TaskDBObj> data = taskDao.getAllTasks();
        for(TaskDBObj s : data) {
            Task task = s.getTask();
            task.setId(s.id);
            allTasks.add(task);
        }
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public long insert(Task task) {
        TaskDBObj taskDBObj = new TaskDBObj(task);
        return taskDao.insert(taskDBObj);
    }

    public void update(TaskDBObj task){
        taskDao.update(task);
    }

    public void deleteById(long id) { taskDao.deleteTaskById(id); }

////////////////////////////////////////////////////////////////

    public boolean getCreateNotificationSetting() {
        // Retrieve the "create notification" setting from the TaskRepository
        return taskDao.getCreateNotificationSetting();
    }

    public void setCreateNotificationSetting(boolean createNotification) {
        // Save the "create notification" setting to the TaskRepository
        taskDao.setCreateNotificationSetting(createNotification);
    }


    public boolean getUpdateNotificationSetting() {
        // Retrieve the "update notification" setting from the TaskRepository
        return taskDao.getUpdateNotificationSetting();
    }

    public void setUpdateNotificationSetting(boolean updateNotification) {
        // Save the "update notification" setting to the TaskRepository
        taskDao.setUpdateNotificationSetting(updateNotification);
    }

    public boolean getDeleteNotificationSetting() {
        return taskDao.getDeleteNotificationSetting();
    }

    public void setDeleteNotificationSetting(boolean deleteNotification) {
        taskDao.setDeleteNotificationSetting(deleteNotification);
    }
}


