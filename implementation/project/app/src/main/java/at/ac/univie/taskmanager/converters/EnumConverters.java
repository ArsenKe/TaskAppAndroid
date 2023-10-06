package at.ac.univie.taskmanager.converters;

import at.ac.univie.taskmanager.models.enums.ETaskStatus;

public class EnumConverters {

    public static ETaskStatus convertStringToEStatus(String string){
        switch (string){
            case "PLANNED":
                return ETaskStatus.PLANNED;
            case "IN_PROGRESS":
                return ETaskStatus.IN_PROGRESS;
            case "TODO":
                return ETaskStatus.TODO;
            case "CANCELLED":
                return ETaskStatus.CANCELLED;
            case "DONE":
                return ETaskStatus.DONE;
            default:
                throw new IllegalArgumentException("Unsupported EStatus");
        }
    }
}
