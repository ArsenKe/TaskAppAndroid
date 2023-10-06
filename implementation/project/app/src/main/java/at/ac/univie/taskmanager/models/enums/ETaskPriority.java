package at.ac.univie.taskmanager.models.enums;

public enum ETaskPriority {
    NONE("none"),
    HIGH("high"),
    LOW("low");

    private String priority;
    private ETaskPriority(String priority){
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority;
    }
}
