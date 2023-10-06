package at.ac.univie.taskmanager;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.ac.univie.taskmanager.database.TaskDao;
import at.ac.univie.taskmanager.database.TaskDatabase;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;

@RunWith(AndroidJUnit4.class)
public class DeleteTaskRoomDatabaseTest {
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
    public void deleteAppointmentAndReadFromDatabase() {
        Task appointment = TaskUtils.getAppointment();
        TaskDBObj dbTask = storeTask(appointment);
        assertTrue(taskDao.getAllTasks().contains(dbTask));
        taskDao.delete(dbTask);
        assertFalse(taskDao.getAllTasks().contains(dbTask));
    }

    @Test
    public void deleteChecklistAndReadFromDatabase() {
        Task checklist = TaskUtils.getChecklist();
        TaskDBObj dbTask = storeTask(checklist);
        assertTrue(taskDao.getAllTasks().contains(dbTask));
        taskDao.delete(dbTask);
        assertFalse(taskDao.getAllTasks().contains(dbTask));
    }

    private TaskDBObj storeTask(Task task) {
        TaskDBObj dbTask = new TaskDBObj(task);
        long id = taskDao.insert(dbTask);
        dbTask.id = (int) id;
        return dbTask;
    }
}
