package com.wegrzyn.marcin.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Marcin Węgrzyn on 05.05.2018.
 * wireamg@gmail.com
 */

@RunWith(AndroidJUnit4.class)
public class ActionBarTest {

    private static final String RECIPE_NAME = "Brownies";
    private static final String BAKING_APP = "BakingApp";
    private static final String RECIPE_INTRODUCTION = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkActionBarTitle(){

       onView(withId(R.id.action_bar))
               .check(matches(actionBarTitle(BAKING_APP)));

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItem(hasDescendant(withText(RECIPE_NAME)), click()));

        onView(withId(R.id.action_bar))
                .check(matches(actionBarTitle(RECIPE_NAME)));

        onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.action_bar))
                .check(matches(actionBarTitle(RECIPE_NAME)));
    }

    public static Matcher<View> actionBarTitle(CharSequence title) {
        return actionBarTitle(is(title));
    }

    public static Matcher<View> actionBarTitle(final Matcher<CharSequence> tMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return tMatcher.matches(toolbar.getTitle());
            }
            @Override
            public void describeTo(Description des) {
                des.appendText("Action Bar Title: ");
                tMatcher.describeTo(des);
            }
        };
    }

}
