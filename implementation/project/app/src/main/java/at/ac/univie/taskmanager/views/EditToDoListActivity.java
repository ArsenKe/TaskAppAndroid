package at.ac.univie.taskmanager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.taskmanager.MainActivity;
import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.StringAdapter;
import at.ac.univie.taskmanager.models.tasks.CheckList;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class EditToDoListActivity extends ReturnOptionActivity {

    private EditText editText;
    private Button addBtn;
    private Button resetBtn;
    private Button createToDoListBtn;
    private Button editBtn;
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private StringAdapter adapter;
    private CheckList checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        actionBar.setTitle("Edit TODO List");

        checkList = (CheckList) extras.get("list");
        dataList = checkList.getTodo();

        editText = findViewById(R.id.edit_text);
        addBtn = findViewById(R.id.bt_add);
        createToDoListBtn = findViewById(R.id.bt_create);
        recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new StringAdapter(this, true);
        adapter.setTasks(checkList.getTodo());

        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = editText.getText().toString().trim();
                if (!sText.equals("")) {
                    editText.setText("");
                    dataList.add(sText);
                    adapter.setTask(sText);

                    Toast.makeText(EditToDoListActivity.this, "Successfully Edit!", Toast.LENGTH_LONG).show();
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
                CheckList task = (CheckList) checkList;
                TaskViewModel taskViewModel = new ViewModelProvider(EditToDoListActivity.this).get(TaskViewModel.class);
                taskViewModel.deleteById(task.getId());
                dataList = adapter.getTasks();
                task.setTodo(dataList);
                taskViewModel.insert(task);
                switchToMainActivity();
            }
        });
    }

    private void switchToMainActivity() {
        Intent switchToMainActivityItem = new Intent(this, MainActivity.class);
        startActivity(switchToMainActivityItem);
    }

}