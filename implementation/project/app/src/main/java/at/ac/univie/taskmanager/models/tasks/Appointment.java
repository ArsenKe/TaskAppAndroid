package at.ac.univie.taskmanager.models.tasks;

import androidx.room.Ignore;

import java.time.LocalDateTime;

import at.ac.univie.taskmanager.exceptions.UnsupportedCompositeException;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskPriority;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;

public class Appointment extends Task {

    private String location;
    private ETaskPriority priority;

    public Appointment(){
        super();
    }

    @Ignore
    public Appointment(String title, String description, LocalDateTime dateTime,
                       ETaskStatus status, ETaskNotification notification, ETaskNotify notify,
                       ETaskColor color, String note, String location, ETaskPriority priority) {

        super(title, description, dateTime, status, notification, notify, color, note);

        if(priority == null) {
            throw new IllegalArgumentException("Priority must not be null");
        }
        this.priority = priority;
        if(location == null) {
            throw new IllegalArgumentException("Location must not be null");
        }
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ETaskPriority getPriority() {
        return priority;
    }

    public void setPriority(ETaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public void add(Task subTask) {
        throw new UnsupportedCompositeException("Method add is not implemented for the Appointment");
    }

    @Override
    public void remove(Task subTask) {
        throw new UnsupportedCompositeException("Method remove is not implemented for the Appointment");
    }

    @Override
    public void removeAll() {
        throw new UnsupportedCompositeException("Method removeAll is not implemented for the Appointment");
    }
}
