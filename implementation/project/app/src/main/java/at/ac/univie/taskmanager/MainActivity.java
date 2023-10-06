package at.ac.univie.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import at.ac.univie.taskmanager.adapters.TaskAdapter;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;
import at.ac.univie.taskmanager.views.UpdateTaskActivity;
import at.ac.univie.taskmanager.views.SelectTaskTypeActivity;


/**
 * MainActivity. The first activity to appear when the user starts the program.
 * It contains menu to navigate through the program. A list with all tasks that
 * are not hidden and also the button to add new tasks.
 *
 * Every task from this page can be hidden, deleted or edited.
 */
public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private TaskViewModel taskViewModel;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ViewModel for MVVM pattern. It is used to manipulate tasks
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Menu located on the sidebar
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.menu_id);
        initNavigationView();

        // initialize the recycler view holding the tasks
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // adapt the tasks to initial listview
        TaskAdapter adapter = new TaskAdapter(this, taskViewModel);
        recyclerView.setAdapter(adapter);

        // populate adapter with tasks from database
        adapter.setTasks(taskViewModel.getAllTasks());

        // the button to proceed with task creation
        FloatingActionButton addTaskBtn = findViewById(R.id.button_add_task);
        addTaskBtn.setOnClickListener(view -> switchToSelectTaskTypeActivity());
    }

    /**
     * Initializes the navigation menu in the side bar.
     */
    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.update_tasks_menu:
                    switchToUpdateTaskActivity();
                    break;
                //TODO write cases for all new menu items.
                default:
                    return false;
            }
            return false;
        });
    }

    /**
     * Enables the sidebar button.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts SelectTaskTypeActivity. Simply navigates to the next activity.
     */
    private void switchToSelectTaskTypeActivity() {
        Intent switchToSelectTaskItem = new Intent(MainActivity.this, SelectTaskTypeActivity.class);
        startActivity(switchToSelectTaskItem);
    }

    /**
     * Starts UpdateTaskActivity. Simply navigates to the next activity.
     */
    private void switchToUpdateTaskActivity() {
        Intent switchToSelectAllTaskItem = new Intent(MainActivity.this, UpdateTaskActivity.class);
        startActivity(switchToSelectAllTaskItem);
    }
}