package at.ac.univie.taskmanager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.time.LocalDateTime;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.utilities.DateFormatter;
import at.ac.univie.taskmanager.utilities.DateHelper;

/**
 * TaskDetailsActivity. Visualization of the task. Contains all the needed data
 * to show about the appointment object.
 */
public class TaskDetailsActivity extends ReturnOptionActivity {

    private TextView titleTxtView;
    private TextView descriptionTxtView;
    private TextView dateTxtView;
    private TextView timeTxtView;
    private TextView statusTxtView;
    private TextView notificationTxtView;
    private TextView notifyTxtView;
    private TextView colorTxtView;
    private TextView priorityTxtView;
    private TextView locationTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        actionBar.setTitle(extras.getString("TASK_TYPE"));

        // get the text views by id.
        titleTxtView = findViewById(R.id.txt_title_id);
        descriptionTxtView = findViewById(R.id.txt_description_id);
        dateTxtView = findViewById(R.id.txt_date_id);
        timeTxtView = findViewById(R.id.txt_time_id);
        statusTxtView = findViewById(R.id.txt_status_id);
        notificationTxtView = findViewById(R.id.txt_notification_id);
        notifyTxtView = findViewById(R.id.txt_notify_id);
        colorTxtView = findViewById(R.id.txt_color_id);
        priorityTxtView = findViewById(R.id.txt_priority_id);
        locationTxtView = findViewById(R.id.txt_location_id);

        // process the task data and set it to be visible.
        titleTxtView.setText(extras.getString("title"));
        descriptionTxtView.setText(extras.getString("description"));
        statusTxtView.setText(extras.getString("status"));
        notificationTxtView.setText(extras.getString("notification"));
        notifyTxtView.setText(extras.getString("notify"));
        colorTxtView.setText(extras.getString("color"));
        priorityTxtView.setText(extras.getString("priority"));
        locationTxtView.setText(extras.getString("location"));

        // we format the date to be consistent in our visualizations.
        LocalDateTime dateTime = (LocalDateTime)extras.get("dateTime");
        DateFormatter formatter = new DateHelper();
        dateTxtView.setText(formatter.formatDate(dateTime));
        timeTxtView.setText(formatter.formatTime(dateTime));
    }
}