package at.ac.univie.taskmanager.models.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import at.ac.univie.taskmanager.exceptions.UnsupportedCompositeException;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;

public class CheckList extends Task{

    private List<String> todo;

    public CheckList(String title, String description, LocalDateTime dateTime, ETaskStatus status,
                     ETaskNotification notification, ETaskNotify notify, ETaskColor color,
                     String note, List<String> todo) {
        super(title, description, dateTime, status, notification, notify, color, note);
        this.todo = todo;
    }

    public List<String> getTodo() {
        return todo;
    }

    public void setTodo(List<String> todo) {
        this.todo = todo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
//        CheckList checkList = (CheckList) o;
//        return Objects.equals(todo, checkList.todo);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), todo);
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "todo=" + todo +
                '}';
    }

    @Override
    public void add(Task subTask) {
        throw new UnsupportedCompositeException("Method add is not implemented for the CheckList");
    }

    @Override
    public void remove(Task subTask) {
        throw new UnsupportedCompositeException("Method remove is not implemented for the CheckList");
    }

    @Override
    public void removeAll() {
        throw new UnsupportedCompositeException("Method removeAll is not implemented for the CheckList");
    }


}
