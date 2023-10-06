package at.ac.univie.taskmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.tasks.Appointment;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.models.tasks.CompositeTask;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.models.tasks.TaskDBObj;
import at.ac.univie.taskmanager.utilities.DateHelper;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;
import at.ac.univie.taskmanager.views.CheckListDetailsActivity;
import at.ac.univie.taskmanager.views.CompositeTaskDetailsActivity;
import at.ac.univie.taskmanager.views.EditAppointmentActivity;
import at.ac.univie.taskmanager.views.EditCheckListActivity;
import at.ac.univie.taskmanager.views.EditCompositeTaskActivity;
import at.ac.univie.taskmanager.views.TaskDetailsActivity;


/**
 * TaskAdapterTemplate helps laying out the tasks in the activity where it is used.<br><br>
 *
 * It adapts the task to its visualization based on title, description and its date.<br>
 * Additionally by default adds three buttons to every task (edit / hide / delete).<br><br>
 *
 * Supports tasks as well as subtasks. To support subtasks - pass additional boolean argument
 * to {@link TaskAdapterTemplate#setTasks(List, boolean)} function.<br><br>
 *
 * Has two subclasses:<br>
 * See {@link TaskAdapter} TaskAdapter - adapter with full functionality.<br>
 * See {@link TaskAdapterNoButtons} TaskAdapterNoButtons - as the name suggests, the buttons are hidden (used for detailed view of Task).
 */
public abstract class TaskAdapterTemplate extends RecyclerView.Adapter<at.ac.univie.taskmanager.adapters.TaskAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<>();
    private Context context;
    private TaskViewModel viewModel;
    private boolean subtasks;
    private Task mainTask;
    private Task oldTask;

    public TaskAdapterTemplate(Context context, TaskViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterTemplate.TaskHolder holder, int position) {

        // for every task it sets its title, description, date
        Task task = tasks.get(position);
        holder.textviewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());
        holder.textViewDate.setText(DateHelper.dateToString(task.getDateTime()));

        // if the task is clicked - show its details
        holder.itemView.setOnClickListener(view -> {
            Log.i(TaskAdapter.class.getSimpleName(), "Show task details");
            showTaskDetails(task);
        });

        // if the delete button is clicked - remove the task based on the knowledge
        // whether it is a subtask or a normal task
        holder.deleteBtn.setOnClickListener(view -> {
            Task taskToDelete = getTaskAt(holder.getAdapterPosition());
            Log.i("Task to delete", taskToDelete.toString());

            removeAt(holder.getAdapterPosition());
            if(subtasks) {
                mainTask.remove(taskToDelete);
            } else {
                viewModel.deleteById(taskToDelete.getId());
            }
        });

        // if the edit button is clicked - continue with edit activity specific
        // to the task type. Additional info must be passed for correct handling of the
        // subtasks.
        holder.editBtn.setOnClickListener(view -> {
            Task taskToEdit = getTaskAt(holder.getAdapterPosition());
            TaskDBObj taskDB = new TaskDBObj(taskToEdit);
            taskDB.id = (int) taskToEdit.getId();

            Class<?> nextActivity = determineEditActivity(taskToEdit);
            Intent intent = new Intent(context, nextActivity);


            // here we pass extra info for handling of subtasks.
            // if old task is null set it to the task that is going to be edited
            if(oldTask == null) {
                oldTask = taskToEdit;
            }
            // pass the boolean flag whether it is a subtask
            intent.putExtra("subtask", subtasks);
            // pass the old task for future checks for changes
            intent.putExtra("oldTask", oldTask);
            // pass the task that is going to be edited
            intent.putExtra("editTask", taskDB);
            // pass the main task (the owner of subtasks)
            intent.putExtra("mainTask", mainTask);

            // switch to the edit activity
            context.startActivity(intent);
            // at the end update the database with edited task
            viewModel.update(taskDB);
        });
    }

    private Class<?> determineEditActivity(Task taskToEdit) {
        if (taskToEdit instanceof Appointment){
            Log.i("Appointment task to edit: ", taskToEdit.toString());
            return EditAppointmentActivity.class;
        } else if (taskToEdit instanceof CheckList ) {
            Log.i("CheckList task to edit: ", taskToEdit.toString());

            return EditCheckListActivity.class;
        } else if (taskToEdit instanceof CompositeTask) {
            Log.i("CompositeTask task to edit: ", taskToEdit.toString());
            return EditCompositeTaskActivity.class;
        } else {
            throw new IllegalArgumentException("Unsupported task type");
        }
    }

    private void showTaskDetails(Task task) {
        Intent intent;
        if ( task instanceof Appointment) {
            Log.i("Appointment View details", task.toString());

            intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("TASK_TYPE", "Appointment");
            initIntent(intent, task);
            intent.putExtra("priority", ((Appointment) task).getPriority().toString());
            intent.putExtra("location", ((Appointment) task).getLocation());
        } else if ( task instanceof CheckList) {
            Log.i("CheckList View details", task.toString());

            intent = new Intent(context, CheckListDetailsActivity.class);
            intent.putExtra("TASK_TYPE", "Check List");
            initIntent(intent, task);
            intent.putStringArrayListExtra("toDoList", new ArrayList<>(((CheckList) task).getTodo()));
        } else {
            Log.i("CompositeTask View details", task.toString());

            intent = new Intent(context, CompositeTaskDetailsActivity.class);
            intent.putExtra("TASK_TYPE", "CompositeTask");
            intent.putExtra("task", task);
        }

        context.startActivity(intent);
    }

    private void initIntent(Intent intent, Task task) {
        intent.putExtra("title", task.getTitle());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("dateTime", task.getDateTime());
        intent.putExtra("status", task.getStatus().toString());
        intent.putExtra("notification", task.getNotification().toString());
        intent.putExtra("notify", task.getNotify().toString());
        intent.putExtra("color", task.getColor().toString());
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks, boolean subtasks) {
        this.subtasks = subtasks;
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks) {
        this.subtasks = false;
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    public void removeAt(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    public void setMainTask(CompositeTask mainTask) {
        this.mainTask = mainTask;
        this.subtasks = true;
    }

    public void setOldTask(Task oldTask) {
        this.oldTask = oldTask;
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView textviewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        protected Button deleteBtn;
        protected Button editBtn;
        protected Button hideBtn;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textviewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            editBtn = itemView.findViewById(R.id.edit_btn);
            hideBtn = itemView.findViewById(R.id.hide_btn);
        }
    }
}
