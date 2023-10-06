package at.ac.univie.taskmanager.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import at.ac.univie.taskmanager.R;


public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringHolder>{

    private List<String> tasks = new ArrayList<>();
    private Context context;
    private boolean visibleDelete;

    public StringAdapter(Context context, boolean visibleDelete){
        this.context = context;
        this.visibleDelete = visibleDelete;
    }

    @NonNull
    @Override
    public StringAdapter.StringHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new StringAdapter.StringHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StringAdapter.StringHolder holder, int position) {
        String task = tasks.get(position);
        holder.textviewTitle.setText(task);
        if(visibleDelete) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskToDelete = getItemAt(holder.getAdapterPosition());
                Log.i("Task to delete", taskToDelete);

                removeAt(holder.getAdapterPosition());
            }
        });
    }

    public List<String> getTasks() {
        return tasks;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private String getItemAt(int position) {
        return tasks.get(position);
    }

    private void removeAt(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    public void setTasks(List<String> tasks) {
        for (var task: tasks) {
            this.tasks.add(task);
        }
        notifyDataSetChanged();
    }

    public void setTask(String task){
        this.tasks.add(task);
        notifyDataSetChanged();
    }

    public void resetTasks(){
        this.tasks = new ArrayList<>();
        notifyDataSetChanged();
    }

    class StringHolder extends RecyclerView.ViewHolder{
        private TextView textviewTitle;
        private Button deleteBtn;

        public StringHolder(@NonNull View itemView) {
            super(itemView);
            textviewTitle = itemView.findViewById(R.id.text_view_title_todo);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

    }
}
