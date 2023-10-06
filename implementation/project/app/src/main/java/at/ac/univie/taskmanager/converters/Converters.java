package at.ac.univie.taskmanager.converters;

import androidx.room.TypeConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import at.ac.univie.taskmanager.models.tasks.Task;

public class Converters {
    //Reference https://stackoverflow.com/questions/54927913/room-localdatetime-typeconverter
    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }

    @TypeConverter
    public static byte[] toBytes(Task task) {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try(ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(task);
            }
            System.out.println("Was serialized");
            return b.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TypeConverter
    public static Task toTask(byte[] bytes) {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try(ObjectInputStream o = new ObjectInputStream(b)) {
                return (Task) o.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @TypeConverter
    public static byte[] tasksToBytes(ArrayList<Task> tasks) {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try(ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(tasks);
            }
            return b.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TypeConverter
    @SuppressWarnings("unchecked")
    public static ArrayList<Task> bytesToTasks(byte[] bytes) {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try(ObjectInputStream o = new ObjectInputStream(b)) {
                return (ArrayList<Task>) o.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
