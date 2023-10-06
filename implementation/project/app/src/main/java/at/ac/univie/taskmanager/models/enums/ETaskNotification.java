package at.ac.univie.taskmanager.models.enums;

public enum ETaskNotification {
    NONE("none"),
    EMAIL("email"),
    POPUP("popup");

    private String notification;
    private ETaskNotification(String notification){
        this.notification = notification;
    }

    @Override
    public String toString() {
        return notification;
    }
}
