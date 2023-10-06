package at.ac.univie.taskmanager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskPriority;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Appointment;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.models.tasks.Task;

public class TaskUtils {

    public static ArrayList<Task> getTasks() {
        ArrayList<Task> res = new ArrayList<>();
        res.add(getAppointment());
        res.add(getChecklist());
        res.add(new Appointment(
                "Appointment 2", "test desc", LocalDateTime.now(),
                ETaskStatus.PLANNED, ETaskNotification.POPUP, ETaskNotify.ONE_HOUR,
                ETaskColor.BLUE, "Note", "Vienna", ETaskPriority.HIGH
        ));
        res.add(new Appointment(
                "Appointment 3", "test desc", LocalDateTime.now(),
                ETaskStatus.CANCELLED, ETaskNotification.POPUP, ETaskNotify.ONE_HOUR,
                ETaskColor.BLUE, "Note", "Vienna", ETaskPriority.HIGH
        ));
        return res;
    }

    public static Task getAppointment() {
        return new Appointment(
                "Test appointment", "test desc", LocalDateTime.now(),
                ETaskStatus.DONE, ETaskNotification.POPUP, ETaskNotify.ONE_HOUR,
                ETaskColor.BLUE, "Note", "Vienna", ETaskPriority.HIGH
        );
    }

    public static Task getChecklist() {
        return new CheckList(
                "Test appointment", "test desc", LocalDateTime.now(),
                ETaskStatus.DONE, ETaskNotification.POPUP, ETaskNotify.ONE_HOUR,
                ETaskColor.BLUE, "Note", List.of("todo 1", "todo 2")
        );
    }
}
