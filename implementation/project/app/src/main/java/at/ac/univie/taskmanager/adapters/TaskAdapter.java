package at.ac.univie.taskmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class TaskAdapter extends TaskAdapterTemplate {

    public TaskAdapter(Context context, TaskViewModel taskViewModel){
        super(context, taskViewModel);
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
