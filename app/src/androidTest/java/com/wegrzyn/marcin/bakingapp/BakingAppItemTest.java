package com.wegrzyn.marcin.bakingapp;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Marcin Węgrzyn on 03.05.2018.
 * wireamg@gmail.com
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppItemTest {

    private static final String RECIPE_NAME = "Brownies";
    private static final String RECIPE_INTRODUCTION = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecycleViewItemSelect() {

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RECIPE_NAME)), click()));

        onView(withId(R.id.steps_recycler_view))
                .check(matches(hasDescendant(withText(RECIPE_INTRODUCTION))));

        onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.detail_tv_label))
                .check(matches(withText(RECIPE_INTRODUCTION)));
    }

}