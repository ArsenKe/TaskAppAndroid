package at.ac.univie.taskmanager.utilities;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

import at.ac.univie.taskmanager.exceptions.InvalidDateException;

public class DateHelper implements DateFormatter {

    private static final Map<Integer, String> months = Map.ofEntries(Map.entry(1,"Jan"),
            Map.entry(2,"Feb"),
            Map.entry(3,"Mar"),
            Map.entry(4,"Apr"),
            Map.entry(5,"May"),
            Map.entry(6,"Jun"),
            Map.entry(7,"Jul"),
            Map.entry(8,"Aug"),
            Map.entry(9,"Sep"),
            Map.entry(10,"Oct"),
            Map.entry(11,"Nov"),
            Map.entry(12,"Dec"));

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        System.out.println("========> Mont is: " + month);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return createDateString(day, month + 1 , year);
    }

    public static String dateToString(LocalDateTime localDateTime){
        int year = localDateTime.getYear();
        int month = localDateTime.getMonth().getValue();
        int day = localDateTime.getDayOfMonth();
        return createDateString(day, month, year);
    }

    public static String createDateString(int day, int month, int year){
        return day + "-" + getMonthFormat(month) + "-" + year;
    }

    private static String getMonthFormat(int month) {
        if(month > 12 || month < 1){
            throw new InvalidDateException("Month must be between 1-12");
        }
        return months.get(month);
    }

    @Override
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth() + "/" + dateTime.getMonth().getValue() + "/" +  dateTime.getYear();
    }

    @Override
    public String formatTime(LocalDateTime dateTime) {
        return String.format("%02d:%02d",dateTime.getHour(), dateTime.getMinute());
    }
}
