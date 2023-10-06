package at.ac.univie.taskmanager.utilities;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class DefaultTextViewStyler implements Styler {
    private final int color = Color.BLACK;
    private final float textSize = 20;
    private final int textWidth = 280;
    private final int align = View.TEXT_ALIGNMENT_TEXT_END;

    @Override
    public void styleTextView(TextView view, int textResource) {
        view.setText(textResource);
        view.setTextColor(color);
        view.setTextSize(textSize);
        view.setWidth(textWidth);
        view.setTextAlignment(align);

    }

    @Override
    public int getWidth() {
        return textWidth;
    }
}
