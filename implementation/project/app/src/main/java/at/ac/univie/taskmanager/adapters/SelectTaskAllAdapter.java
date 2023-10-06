package at.ac.univie.taskmanager.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class SelectTaskAllAdapter  extends RecyclerView.Adapter<SelectTaskAllAdapter.TaskHolder>{

    private List<Task> tasks = new ArrayList<>();
    private ArrayList<Task> checkedList = new ArrayList<>();
    private TaskViewModel taskViewModel;
    private Context context;

    public SelectTaskAllAdapter(TaskViewModel taskViewModel, Context context){
        this.taskViewModel = taskViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectTaskAllAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_all_task_item, parent, false);
        return new SelectTaskAllAdapter.TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTaskAllAdapter.TaskHolder holder, int position) {
        Task currentNote =  tasks.get(position);
        holder.textviewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkedList.add(currentNote);
                } else {
                    checkedList.remove(currentNote);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = new ArrayList<>();
        for(var task: tasks){
            this.tasks.add(task);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Task> checkedTaskList(){
        return checkedList;
    }

    //this function is for delete. to find witch Item it is0
    public Task getAppointmentAt(int position){
        return tasks.get(position);
    }


    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView textviewTitle;
        private TextView textViewDescription;
        private CheckBox checkBox;
        private Button deleteTaskBtn;
        private Button updateTaskBtn;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textviewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            checkBox = itemView.findViewById(R.id.select_task_checkbox_id);
//            deleteTaskBtn = itemView.findViewById(R.id.select_task_deleteBtn_id);
//            updateTaskBtn = itemView.findViewById(R.id.select_task_updateBtn_id);
        }
    }
}
