package at.ac.univie.taskmanager.views;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.StringAdapter;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class ToDoListActivity extends ReturnOptionActivity {

    private EditText editText;
    private Button addBtn;
    private Button resetBtn;
    private Button createToDoListBtn;
    private RecyclerView recyclerView;
    private List<String> dataList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private StringAdapter adapter;
    private Task extraTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add ToDo List");
        Bundle extras = getIntent().getExtras();
        this.extraTask = (Task) extras.getSerializable("task");

        editText=findViewById(R.id.edit_text);
        addBtn =findViewById(R.id.bt_add);
        createToDoListBtn = findViewById(R.id.bt_create);
        recyclerView=findViewById(R.id.recycler_view);

        linearLayoutManager =new LinearLayoutManager(this);

        adapter = new StringAdapter(this, true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText=editText.getText().toString().trim();
                if(!sText.equals("")){
                    editText.setText("");
                    dataList.add(sText);
                    adapter.setTask(sText);
                }
            }
        });

        resetBtn = findViewById(R.id.bt_Reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList = new ArrayList<>();
                adapter.resetTasks();
            }
        });

        createToDoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckList task = (CheckList) extraTask;
                TaskViewModel taskViewModel =  new ViewModelProvider(ToDoListActivity.this).get(TaskViewModel.class);
                taskViewModel.deleteById(task.getId());
                dataList = adapter.getTasks();
                task.setTodo(dataList);
                taskViewModel.insert(task);
                switchToMainActivity();
            }
        });
    }

    private void switchToMainActivity(){
        Intent switchToMainActivityItem = new Intent(this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }
}