package at.ac.univie.taskmanager.viewmodel;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.tasks.Task;

public abstract class NotificationScheduler implements Observer {
    private Object state;

    public NotificationScheduler(Object state) {
        this.state = state;
    }

    public void addNotification(Task task) {
        LocalDateTime dateTime = determineNotification(task);
        String msg = createNotificationMessage(task);
        scheduleTaskNotification(msg, dateTime);
    }


    //Check the task's date and time to see if a notification should be triggered.
    //Set task notification to certain time.
    LocalDateTime determineNotification(@NonNull Task task) {
        LocalDateTime notificationTime = null;
        if(task.getDateTime() != null) {
            notificationTime = task.getDateTime();
        }
        if (task.getNotification() == ETaskNotification.EMAIL || task.getNotification() == ETaskNotification.POPUP) {
            switch (task.getNotify()) {
                case ONE_MINUTES:
                    notificationTime = notificationTime.minusMinutes(1);
                    break;
                case ONE_HOUR:
                    notificationTime = notificationTime.minusMinutes(60);
                    break;
                case TEN_MINUTES:
                    notificationTime = notificationTime.minusMinutes(10);
                    break;
                case THIRTY_MINUTES:
                    notificationTime = notificationTime.minusMinutes(30);
                    break;
                case FIVE_MINUTES:
                    notificationTime = notificationTime.minusMinutes(5);
                    break;
                case NONE:
                    notificationTime = notificationTime.minusMinutes(0);
                    break;
            }
        }
        return notificationTime;
    }

    //message that will be displayed in the notification
    String createNotificationMessage(Task task) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Task: " + task.getTitle() + "\n");
        messageBuilder.append("Description: " + task.getDescription() + "\n");
        messageBuilder.append("Status: " + task.getStatus().getLabel() + "\n");
        messageBuilder.append("Color: " + task.getColor().getLabel() + "\n");
        messageBuilder.append("Note: " + task.getNote() + "\n");
        messageBuilder.append("Sketch: " + task.getSketchFileName() + "\n");

        return messageBuilder.toString();
    }


    abstract void scheduleTaskNotification(String msg, LocalDateTime dateTime);

    @Override
    public void update(Object state) {
        this.state = state;
    }
}




