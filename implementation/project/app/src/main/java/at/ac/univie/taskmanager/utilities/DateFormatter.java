package at.ac.univie.taskmanager.utilities;

import java.time.LocalDateTime;

public interface DateFormatter {

    public String formatDate(LocalDateTime localDateTime);
    public String formatTime(LocalDateTime localDateTime);
}
