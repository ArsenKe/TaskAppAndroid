package at.ac.univie.taskmanager;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.database.TaskDao;
import at.ac.univie.taskmanager.database.TaskDatabase;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.proxy.ProxyTaskUpdateHandler;
import at.ac.univie.taskmanager.proxy.TaskUpdateHandler;

public class UpdateTasksTest {
    private TaskDao taskDao;
    private TaskDatabase db;

    @Before
    public void setUpDatabase() {
        Context ctx = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(ctx, TaskDatabase.class).build();
        taskDao = db.tasksDao();
    }

    @After
    public void closeDatabase() {
        db.close();
    }

    @Test
    public void updateTasksAndReadFromDatabase() {
        ArrayList<Task> tasks = TaskUtils.getTasks();
        ArrayList<TaskDBObj> tasksToUpdate = new ArrayList<>();

        for(var eachTask: tasks) {
            tasksToUpdate.add(storeTask(eachTask));
        }

        List<TaskDBObj> storedTasks = taskDao.getAllTasks();
        assertEquals(tasksToUpdate.size(), storedTasks.size());

        tasks.clear();
        for(var eachTask: storedTasks) {
            tasks.add(eachTask.getTask());
        }

        TaskUpdateHandler updateHandler = new ProxyTaskUpdateHandler();
        updateHandler.updateStatus(tasks, ETaskStatus.IN_PROGRESS);

        for(var eachTask: tasks) {
            assertEquals(eachTask.getStatus(), ETaskStatus.IN_PROGRESS);
        }

        for(var eachTask: storedTasks) {
            taskDao.update(eachTask);
        }

        storedTasks = taskDao.getAllTasks();
        for(var eachTask: storedTasks) {
            assertEquals(eachTask.getTask().getStatus(), ETaskStatus.IN_PROGRESS);
        }
    }

    private TaskDBObj storeTask(Task task) {
        TaskDBObj dbTask = new TaskDBObj(task);
        long id = taskDao.insert(dbTask);
        dbTask.id = (int) id;
        return dbTask;
    }
}
