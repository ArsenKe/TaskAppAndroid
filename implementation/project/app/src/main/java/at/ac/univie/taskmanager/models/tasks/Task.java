package at.ac.univie.taskmanager.models.tasks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.validators.StringValidator;


public abstract class Task implements Serializable {

    private long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private ETaskStatus status;
    private ETaskNotification notification;
    private ETaskNotify notify;
    private ETaskColor color;
    private String note;
    private String sketchFileName;


    public Task(){}

    @Ignore
    public Task(String title, String description, LocalDateTime dateTime, ETaskStatus status,
                ETaskNotification notification, ETaskNotify notify, ETaskColor color, String note) {
        if(StringValidator.isNullOrBlank(title)) {
            throw new IllegalArgumentException("Title of task cannot be null or empty.");
        }
        this.title = title;
        if(description == null) {
            throw new IllegalArgumentException("Description of a task cannot be null.");
        }
        this.description = description;
        if(dateTime == null) {
            throw new IllegalArgumentException("Date time must not be null.");
        }
        this.dateTime = dateTime;
        if(status == null) {
            throw new IllegalArgumentException("Status of a task must not be null.");
        }
        this.status = status;
        if(notification == null) {
            throw new IllegalArgumentException("Notification of a task must not be null.");
        }
        this.notification = notification;
        if(notify == null) {
            throw new IllegalArgumentException("Notify of a task must not be null.");
        }
        this.notify = notify;
        if(color == null) {
            throw new IllegalArgumentException("Color of a task must not be null.");
        }
        this.color = color;
        if(note == null) {
            throw new IllegalArgumentException("Note of a task must not be null.");
        }
        this.note = note;
    }

    @Ignore
    public Task(String title, String description, LocalDateTime dateTime, ETaskStatus status,
                ETaskNotification notification, ETaskNotify notify, ETaskColor color, String note,
                String sketchFileName) {
        this(title, description, dateTime, status, notification, notify, color, note);
        if(StringValidator.isNullOrBlank(sketchFileName)) {
            throw new IllegalArgumentException("Sketch filename must not be null or blank");
        }
        this.sketchFileName = sketchFileName;
    }

    /**
     * Adds a subtask to the task if the class can be composed of multiple tasks.
     * Throws UnsupportedCompositeException if the method is not supported by
     * subclass.
     *
     * @param subTask a task to add as a subtask
     */
    public abstract void add(Task subTask);

    /**
     * Removes a subtask if the class can be composed of multiple tasks and includes the task to
     * be removed.
     * Throws UnsupportedCompositeException if the method is not supported by
     * subclass.
     *
     * @param subTask a task to remove
     */
    public abstract void remove(Task subTask);

    /**
     * Removes all subtasks if the class can be composed of multiple tasks.
     * Throws UnsupportedCompositeException if the method is not supported by
     * subclass.
     */
    public abstract void removeAll();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public ETaskNotification getNotification() {
        return notification;
    }

    public ETaskNotify getNotify() {
        return notify;
    }

    public ETaskColor getColor() {
        return color;
    }

    public String getNote() {
        return note;
    }

    public String getSketchFileName() {
        return sketchFileName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setNotification(ETaskNotification notification) {
        this.notification = notification;
    }

    public void setNotify(ETaskNotify notify) {
        this.notify = notify;
    }

    public void setColor(ETaskColor color) {
        this.color = color;
    }

    public void setSketchFileName(String sketchFileName) {
        this.sketchFileName = sketchFileName;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("equals call");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(dateTime, task.dateTime) && status == task.status && notification == task.notification && notify == task.notify && color == task.color && Objects.equals(note, task.note) && Objects.equals(sketchFileName, task.sketchFileName);
    }

    @Override
    public int hashCode() {
        System.out.println("hash code call");
        return Objects.hash(id, title, description, dateTime, status, notification, notify, color, note, sketchFileName);
    }
}
