package at.ac.univie.taskmanager.utilities;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

public class SpinnerUtils {
    /**
     * Creates a simple array adapter needed for the spinners used in this activity
     *
     * @param type Type of the resource to create the adapter from
     * @return created adapter for the spinner
     */
    @NonNull
    public static ArrayAdapter<CharSequence> setUpAdapter(Context ctx, int type) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx, type, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Creates the adapter needed for spinner initialization and
     * sets up the spinner itself.
     *
     * @param ctx context to create an adapter
     * @param spinner where the adapter must be set
     * @param type type of resource to use for adapter creation
     * @param position ordinal of the enum value
     */
    public static void initSpinner(Context ctx, Spinner spinner, int type, int position) {
        ArrayAdapter<CharSequence> adapter = SpinnerUtils.setUpAdapter(ctx, type);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
    }
}
