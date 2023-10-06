package at.ac.univie.taskmanager.models.enums;

public enum ETaskStatus {
    NONE("none"),
    PLANNED("planned"),
    IN_PROGRESS("inProgress"),
    TODO("toDo"),
    CANCELLED("cancelled"),
    DONE("done");

    private String status;

    private ETaskStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
