package at.ac.univie.taskmanager.models.enums;

import androidx.annotation.NonNull;

public enum ETaskColor {
    WHITE("WHITE"),
    BLUE("BLUE"),
    RED("RED"),
    GREEN("GREEN");

    private String color;
    private ETaskColor(String color){
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
