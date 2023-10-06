package at.ac.univie.taskmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.taskmanager.models.tasks.TaskDBObj;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TaskDBObj task);

    @Update
    void update(TaskDBObj task);

    @Delete
    void delete(TaskDBObj task);

    //TODO Order (DESC) of the Tasks
    @Query("SELECT * FROM tasks")
    List<TaskDBObj> getAllTasks();

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteTaskById(long id);

////////////////////////////////////////////////////////////////

    //@Query("SELECT notification FROM appointments ")
    boolean getCreateNotificationSetting();

    //@Query("UPDATE appointments SET notification = :setting WHERE id = :taskId")
    void setCreateNotificationSetting(boolean createNotification);

    boolean getUpdateNotificationSetting();

    void setUpdateNotificationSetting(boolean updateNotification);

    boolean getDeleteNotificationSetting();

    void setDeleteNotificationSetting(boolean deleteNotification);
}
