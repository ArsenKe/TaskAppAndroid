package at.ac.univie.taskmanager.viewmodel;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.time.LocalDateTime;
import java.time.ZoneId;

import at.ac.univie.taskmanager.models.tasks.Task;

public class EmailNotificationScheduler extends NotificationScheduler implements Observer {
    private Context context;
    private Class emailNotificationReceiver;

    public EmailNotificationScheduler(Object state, Context context, Class emailNotificationReceiver) {
        super(state);
        this.context = context;
        this.emailNotificationReceiver = emailNotificationReceiver;
    }
    @Override
    void scheduleTaskNotification(String msg, LocalDateTime dateTime) {

        // Convert dateTime object to a timestamp in milliseconds
        long notificationTimeStamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Intent object to trigger broadcast receiver
        Intent notificationIntent = new Intent(context,emailNotificationReceiver);
        notificationIntent.putExtra("message",msg);
        notificationIntent.putExtra("notificationTimeStamp",notificationTimeStamp);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // AlarmManager class will schedule the notification time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTimeStamp, pendingIntent);
        }
    }

    //Display the notification message and schedule it
    void scheduleTaskNotification (Task task) {
        LocalDateTime notificationTime = determineNotification(task);
        String notificationMessage = createNotificationMessage(task);
        scheduleTaskNotification(notificationMessage, notificationTime);
    }
}
