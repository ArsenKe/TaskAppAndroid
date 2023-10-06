package at.ac.univie.taskmanager.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class TaskAdapterNoButtons extends TaskAdapterTemplate {

    public TaskAdapterNoButtons(Context context, TaskViewModel viewModel){
        super(context, viewModel);
    }

    @NonNull
    @Override
    public TaskAdapterTemplate.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskAdapterTemplate.TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterTemplate.TaskHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.deleteBtn.setVisibility(View.GONE);
        holder.editBtn.setVisibility(View.GONE);
        holder.hideBtn.setVisibility(View.GONE);
    }
}
