package at.ac.univie.taskmanager.validators;

public class StringValidator {
    public static boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
