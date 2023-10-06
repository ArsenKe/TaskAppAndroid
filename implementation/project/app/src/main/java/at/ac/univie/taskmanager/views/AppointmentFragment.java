package at.ac.univie.taskmanager.views;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Map;

import at.ac.univie.taskmanager.R;
import at.ac.univie.taskmanager.models.enums.ETaskColor;
import at.ac.univie.taskmanager.models.enums.ETaskNotification;
import at.ac.univie.taskmanager.models.enums.ETaskNotify;
import at.ac.univie.taskmanager.models.enums.ETaskPriority;
import at.ac.univie.taskmanager.models.enums.ETaskStatus;
import at.ac.univie.taskmanager.models.tasks.Appointment;
import at.ac.univie.taskmanager.models.tasks.Task;
import at.ac.univie.taskmanager.utilities.SpinnerUtils;
import at.ac.univie.taskmanager.utilities.Styler;
import at.ac.univie.taskmanager.utilities.DefaultTextViewStyler;

public class AppointmentFragment extends TaskFragment {

    private Spinner spinnerPriority;
    private EditText editTextLocation;
    private TextView priorityLabel;
    private TextView locationLabel;

    public AppointmentFragment(AddTaskView view, Context ctx) {
        super(view, ctx);

        Styler styler = new DefaultTextViewStyler();

        LinearLayout priorityLayout = view.findViewById(R.id.priority_layout_id);
        LinearLayout locationLayout = view.findViewById(R.id.location_layout_id);

        ArrayAdapter<CharSequence> priorityAdapter = SpinnerUtils.setUpAdapter(ctx, R.array.priorityType);
        spinnerPriority = new Spinner(view);
        spinnerPriority.setAdapter(priorityAdapter);
        spinnerPriority.setGravity(Gravity.CENTER);
        spinnerPriority.setMinimumWidth(styler.getWidth());

        priorityLabel = new TextView(view);
        styler.styleTextView(priorityLabel, R.string.priority_label);

        locationLabel = new TextView(view);
        styler.styleTextView(locationLabel, R.string.location_label);

        editTextLocation = new EditText(view);
        editTextLocation.setId(R.id.edittext_location);
        editTextLocation.setMinimumWidth(styler.getWidth());
        editTextLocation.setHint(R.string.location_hint);
        editTextLocation.setMaxLines(1);

        priorityLayout.addView(priorityLabel);
        priorityLayout.addView(spinnerPriority);

        locationLayout.addView(locationLabel);
        locationLayout.addView(editTextLocation);
    }


    /**
     * Implementation of the factory method pattern, which creates a task based on the
     * input.
     *
     * @param taskMap map object from which the task is going to be created
     * @return appointment created based on the users input
     */
    @Override
    public Task createTask(Map<String, Object> taskMap) {
        ETaskPriority priority = ETaskPriority.valueOf((String)spinnerPriority.getSelectedItem());
        String location = editTextLocation.getText().toString();

        return new Appointment(
                (String) taskMap.get("title"), (String) taskMap.get("description"), (LocalDateTime) taskMap.get("date"),
                (ETaskStatus) taskMap.get("status"), (ETaskNotification) taskMap.get("notification"), (ETaskNotify) taskMap.get("notify"),
                (ETaskColor) taskMap.get("color"), "note", location, priority
        );
    }
}
