package at.ac.univie.taskmanager.views;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Abstract class that the activities must implement to enable
 * the option to go to the previous activity.
 */
public abstract class ReturnOptionActivity extends AppCompatActivity {

    /**
     * Adds an option on top of the header. Allows to return to the previous activity
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
