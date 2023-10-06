package at.ac.univie.taskmanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import at.ac.univie.taskmanager.repository.TaskRepository;

public class NotificationSettingsViewModel extends ViewModel {
    private TaskRepository taskRepository;
    private MutableLiveData<Boolean> createNotification = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateNotification = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteNotification = new MutableLiveData<>();

    public NotificationSettingsViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        loadNotificationSettings();
    }

    public void loadNotificationSettings() {
        // Retrieve the notification settings from the TaskRepository and set them in the LiveData objects
        createNotification.setValue(taskRepository.getCreateNotificationSetting());
        updateNotification.setValue(taskRepository.getUpdateNotificationSetting());
        deleteNotification.setValue(taskRepository.getDeleteNotificationSetting());
    }

    public void saveNotificationSettings() {
        // Save the notification settings to the TaskRepository
        taskRepository.setCreateNotificationSetting(createNotification.getValue());
        taskRepository.setUpdateNotificationSetting(updateNotification.getValue());
        taskRepository.setDeleteNotificationSetting(deleteNotification.getValue());
    }

    public LiveData<Boolean> getCreateNotification() {
        return createNotification;
    }

    public void setCreateNotification(Boolean createNotification) {
        this.createNotification.setValue(createNotification);
    }

    public LiveData<Boolean> getUpdateNotification() {
        return updateNotification;
    }

    public void setUpdateNotification(Boolean updateNotification) {
        this.updateNotification.setValue(updateNotification);
    }

    public LiveData<Boolean> getDeleteNotification() {
        return deleteNotification;
    }

    public void setDeleteNotification(Boolean deleteNotification) {
        this.deleteNotification.setValue(deleteNotification);
    }
}

