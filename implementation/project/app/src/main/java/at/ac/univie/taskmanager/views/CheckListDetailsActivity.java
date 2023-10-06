package at.ac.univie.taskmanager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.adapters.StringAdapter;
import at.ac.univie.taskmanager.utilities.DateFormatter;
import at.ac.univie.taskmanager.utilities.DateHelper;

public class CheckListDetailsActivity extends ReturnOptionActivity {

    private TextView titleTxtView;
    private TextView descriptionTxtView;
    private TextView dateTxtView;
    private TextView timeTxtView;
    private TextView statusTxtView;
    private TextView notificationTxtView;
    private TextView notifyTxtView;
    private TextView colorTxtView;
    private StringAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        actionBar.setTitle(extras.getString("TASK_TYPE"));

        titleTxtView = findViewById(R.id.txt_title_id);
        descriptionTxtView = findViewById(R.id.txt_description_id);
        dateTxtView = findViewById(R.id.txt_date_id);
        timeTxtView = findViewById(R.id.txt_time_id);
        statusTxtView = findViewById(R.id.txt_status_id);
        notificationTxtView = findViewById(R.id.txt_notification_id);
        notifyTxtView = findViewById(R.id.txt_notify_id);
        colorTxtView = findViewById(R.id.txt_color_id);

        titleTxtView.setText(extras.getString("title"));
        descriptionTxtView.setText(extras.getString("description"));
        LocalDateTime dateTime = (LocalDateTime)extras.get("dateTime");
        DateFormatter formatter = new DateHelper();
        dateTxtView.setText(formatter.formatDate(dateTime));
        timeTxtView.setText(formatter.formatTime(dateTime));
        statusTxtView.setText(extras.getString("status"));
        notificationTxtView.setText(extras.getString("notification"));
        notifyTxtView.setText(extras.getString("notify"));
        colorTxtView.setText(extras.getString("color"));

        adapter = new StringAdapter(this, false);

        recyclerView=findViewById(R.id.check_list_recycler_view);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ArrayList<String> todoList = extras.getStringArrayList("toDoList");
        for(var todo : todoList) {
            adapter.setTask(todo);
        }

    }
}