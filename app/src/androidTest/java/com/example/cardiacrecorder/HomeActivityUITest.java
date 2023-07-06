package com.example.cardiacrecorder;

import android.content.Context;

import androidx.annotation.ContentView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityUITest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testAll(){
        useAppContext();
        addMes();
        updateMes();
        delMes();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.cardiacrecorder", appContext.getPackageName());
    }


    @Test
    public void addMes() {
        SystemClock.sleep(2000);
        onView(withId(R.id.addNewMeasurement)).perform(click());
        onView(withId(R.id.systolicPressure)).perform(ViewActions.typeText("130"));
        onView(withId(R.id.diastolicPressure)).perform(ViewActions.typeText("85"));
        onView(withId(R.id.heartRate)).perform(ViewActions.typeText("70"));
        onView(withId(R.id.date)).perform(ViewActions.typeText("22/07/2022"));
        onView(withId(R.id.time)).perform(ViewActions.typeText("8:15"));
        onView(withId(R.id.comment)).perform(ViewActions.typeText("New Comment 1"));
        Espresso.pressBack();
        onView(withId(R.id.addM)).perform(click());
        //onView(withId(R.id.bg_item)).check(matches(isDisplayed()));
    }


    @Test
    public void updateMes() {
        SystemClock.sleep(2000);
        onView(withId(R.id.measurementListView)).perform(click());
        onView(withId(R.id.editB)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.systolicPressure)).perform(clearText()).perform(ViewActions.typeText("120"));
        onView(withId(R.id.diastolicPressure)).perform(clearText()).perform(ViewActions.typeText("75"));
        onView(withId(R.id.heartRate)).perform(clearText()).perform(ViewActions.typeText("80"));
        onView(withId(R.id.date)).perform(clearText()).perform(ViewActions.typeText("21/08/2022"));
        onView(withId(R.id.time)).perform(clearText()).perform(ViewActions.typeText("09:06"));
        onView(withId(R.id.comment)).perform(clearText()).perform(ViewActions.typeText("Changed Comment"));
        Espresso.pressBack();
        SystemClock.sleep(2000);
        onView(withId(R.id.updateM)).perform(click());
        SystemClock.sleep(2000);

        //onView(withText("120")).check(matches(isDisplayed()));
    }

    @Test
    public void delMes() {
        SystemClock.sleep(2000);
        onView(withId(R.id.measurementListView)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.deleteB)).perform(click());
        //SystemClock.sleep(2000);
        //onView(withId(R.id.bg_item)).check(doesNotExist());
    }

}
