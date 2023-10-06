package at.ac.univie.taskmanager.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.Objects;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.TaskAdapter;
import at.ac.univie.taskmanager.adapters.TaskAdapterNoButtons;
import at.ac.univie.taskmanager.adapters.TaskAdapterTemplate;
import at.ac.univie.taskmanager.models.tasks.CompositeTask;
import at.ac.univie.taskmanager.utilities.DateFormatter;
import at.ac.univie.taskmanager.utilities.DateHelper;
import at.ac.univie.taskmanager.viewmodel.TaskViewModel;

public class CompositeTaskDetailsActivity extends ReturnOptionActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compositetask_details);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        actionBar.setTitle(extras.getString("TASK_TYPE"));

        CompositeTask task = (CompositeTask) extras.getSerializable("task");

        TextView titleTxtView = findViewById(R.id.txt_title_id);
        TextView descriptionTxtView = findViewById(R.id.txt_description_id);
        TextView dateTxtView = findViewById(R.id.txt_date_id);
        TextView timeTxtView = findViewById(R.id.txt_time_id);
        TextView statusTxtView = findViewById(R.id.txt_status_id);
        TextView notificationTxtView = findViewById(R.id.txt_notification_id);
        TextView notifyTxtView = findViewById(R.id.txt_notify_id);
        TextView colorTxtView = findViewById(R.id.txt_color_id);

        titleTxtView.setText(task.getTitle());
        descriptionTxtView.setText(task.getDescription());
        LocalDateTime dateTime = task.getDateTime();
        DateFormatter formatter = new DateHelper();
        dateTxtView.setText(formatter.formatDate(dateTime));
        timeTxtView.setText(formatter.formatTime(dateTime));
        statusTxtView.setText(task.getStatus().toString());
        notificationTxtView.setText(task.getNotification().toString());
        notifyTxtView.setText(task.getNotify().toString());
        colorTxtView.setText(task.getColor().toString());

        // initialize the recycler view holding the tasks
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // adapt the tasks to initial listview
        TaskAdapterTemplate adapter = new TaskAdapterNoButtons(this, new ViewModelProvider(this).get(TaskViewModel.class));
        recyclerView.setAdapter(adapter);

        // populate adapter with tasks from database
        adapter.setTasks(task.getSubtasks());
    }
}
