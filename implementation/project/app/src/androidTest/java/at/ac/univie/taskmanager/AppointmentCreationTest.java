package at.ac.univie.taskmanager;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.ac.univie.taskmanager.views.AddFinalTaskView;

@RunWith(AndroidJUnit4.class)
public class AppointmentCreationTest {


    
    @Rule
    public ActivityScenarioRule<AddFinalTaskView> activityScenarioRule =
            new ActivityScenarioRule<>(
                    new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), AddFinalTaskView.class)
                            .putExtra("TASK_TYPE", "Appointment")
            );

    @Test
    public void createTaskAndCheck() {
        fillEditFields();
    }

    private void fillEditFields() {
        // edit title and description
        onView(withId(R.id.edit_title_id))
                .perform(typeText("Test Appointment"));

        onView(withId(R.id.edit_description_id))
                .perform(typeText("unit test"));

        // hide the keyboard to enable clicks on spinners
        onView(isRoot()).perform(closeSoftKeyboard());

        // choose spinner values
        onView(withId(R.id.choose_statusSpinner_id))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("TODO")))
                .perform(click());

        onView(withId(R.id.choose_notificationSpinner_id))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("EMAIL")))
                .perform(click());

        onView(withId(R.id.choose_notifySpinner_id))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("TEN_MIN")))
                .perform(click());

        onView(withId(R.id.choose_colorSpinner_id))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("RED")))
                .perform(click());

        onView(withId(R.id.priority_layout_id))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("HIGH")))
                .perform(click());

        // enter the location
        onView(withId(R.id.edittext_location))
                .perform(typeText("Vienna"));

        // hide keyboard again
        onView(isRoot()).perform(closeSoftKeyboard());
    }

}
