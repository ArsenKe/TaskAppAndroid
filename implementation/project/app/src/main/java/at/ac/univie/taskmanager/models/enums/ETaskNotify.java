package at.ac.univie.taskmanager.models.enums;

public enum ETaskNotify {
    NONE("none"),
    ONE_MIN("1 min"),
    FIVE_MIN("5 min"),
    TEN_MIN("10 min"),
    THIRTY_MIN("30 min"),
    ONE_HOUR("1 hour");

    private String notify;
    private ETaskNotify(String notify){
        this.notify = notify;
    }

    @Override
    public String toString() {
        return notify;
    }
}
