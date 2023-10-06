package at.ac.univie.taskmanager.utilities;

import android.widget.TextView;

public interface Styler {
    void styleTextView(TextView view, int textResource);

    int getWidth();
}
