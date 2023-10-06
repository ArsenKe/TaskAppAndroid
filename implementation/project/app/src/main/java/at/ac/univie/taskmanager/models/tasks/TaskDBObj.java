package at.ac.univie.taskmanager.models.tasks;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "tasks")
public class TaskDBObj implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private Task task;
    public TaskDBObj() {}


    @Ignore
    public TaskDBObj(Task task) {
        this.task = task;
    }

    public Task getTask() {
        this.task.setId(id);
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDBObj taskDBObj = (TaskDBObj) o;
        return id == taskDBObj.id && Objects.equals(task, taskDBObj.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task);
    }
}
