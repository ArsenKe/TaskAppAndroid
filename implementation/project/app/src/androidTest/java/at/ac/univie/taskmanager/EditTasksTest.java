package at.ac.univie.taskmanager;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import at.ac.univie.taskmanager.database.TaskDao;
import at.ac.univie.taskmanager.database.TaskDatabase;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Appointment;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;

public class EditTasksTest {
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
    public void editAppointmentAndReadFromDatabase() {
        Appointment appointment = (Appointment) TaskUtils.getAppointment();
        TaskDBObj dbTask = storeTask(appointment);
        assertTrue(taskDao.getAllTasks().contains(dbTask));

        appointment.setStatus(ETaskStatus.TODO);
        appointment.setColor(ETaskColor.GREEN);
        appointment.setLocation("Graz");

        Appointment storedAppointment = (Appointment) taskDao.getAllTasks().get(0).getTask();
        assertNotEquals(appointment.getStatus(), storedAppointment.getStatus());
        assertNotEquals(appointment.getColor(), storedAppointment.getColor());
        assertNotEquals(appointment.getLocation(), storedAppointment.getLocation());

        taskDao.update(dbTask);
        storedAppointment = (Appointment) taskDao.getAllTasks().get(0).getTask();
        assertEquals(appointment.getStatus(), storedAppointment.getStatus());
        assertEquals(appointment.getColor(), storedAppointment.getColor());
        assertEquals(appointment.getLocation(), storedAppointment.getLocation());
    }

    @Test
    public void editChecklistAndReadFromDatabase() {
        CheckList checklist = (CheckList) TaskUtils.getChecklist();
        TaskDBObj dbTask = storeTask(checklist);
        assertTrue(taskDao.getAllTasks().contains(dbTask));

        checklist.setStatus(ETaskStatus.TODO);
        checklist.setColor(ETaskColor.GREEN);
        checklist.setTodo(List.of("edited 1", "edited 2", "edited 3"));

        CheckList storedChecklist = (CheckList) taskDao.getAllTasks().get(0).getTask();
        assertNotEquals(checklist.getStatus(), storedChecklist.getStatus());
        assertNotEquals(checklist.getColor(), storedChecklist.getColor());
        assertNotEquals(checklist.getTodo(), storedChecklist.getTodo());

        taskDao.update(dbTask);
        storedChecklist = (CheckList) taskDao.getAllTasks().get(0).getTask();
        assertEquals(checklist.getStatus(), storedChecklist.getStatus());
        assertEquals(checklist.getColor(), storedChecklist.getColor());
        assertEquals(checklist.getTodo(), storedChecklist.getTodo());
    }

    private TaskDBObj storeTask(Task task) {
        TaskDBObj dbTask = new TaskDBObj(task);
        long id = taskDao.insert(dbTask);
        dbTask.id = (int) id;
        return dbTask;
    }
}
