package lv.st.sbogdano.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lv.st.sbogdano.bakingapp.matchers.RecyclerViewMatcher;
import lv.st.sbogdano.bakingapp.matchers.ToolbarViewMatcher;
import lv.st.sbogdano.bakingapp.ui.recipes.RecipesActivity;
import lv.st.sbogdano.bakingapp.ui.recipes.RecipesFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    private static final String RECIPE_TITLE = "Nutella Pie";
    private static final String STEP_TITLE = "Recipe Introduction";
    private static final String RECIPE_SERVINGS = "8";
    private static final String RECIPE_INGREDIENT = "Graham Cracker crumbs";
    private static final String RECIPE_STEP_SHORT_DESC = "Recipe Introduction";
    private static final String RECIPE_STEP_DESCRIPTION = "Recipe Introduction";

    private IdlingResource mIdlingResource;
    private IdlingRegistry mIdlingRegistry;

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipesActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = RecipesFragment.getIdlingResource();
        mIdlingRegistry = IdlingRegistry.getInstance();
        mIdlingRegistry.register(mIdlingResource);
    }

    @Test
    public void recipesActivityTest() {

        onView(withRecyclerView(R.id.recipes_recycler_view).atPosition(0)).check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.recipes_recycler_view).atPositionOnView(0, R.id.recipe_name))
                .check(matches(isDisplayed()))
                .check(matches(withText(RECIPE_TITLE)));

        onView(withRecyclerView(R.id.recipes_recycler_view).atPositionOnView(0, R.id.servings))
                .check(matches(isDisplayed()))
                .check(matches(withText(RECIPE_SERVINGS)));
    }

    @Test
    public void recipeDetailsTest() {
        onView(withRecyclerView(R.id.recipes_recycler_view).atPosition(0))
                .perform(click());

        ToolbarViewMatcher.matchToolbarTitle(RECIPE_TITLE);

        onView(withRecyclerView(R.id.ingredients_recyclerView).atPositionOnView(0, R.id.ingredient))
                .check(matches(isDisplayed()))
                .check(matches(withText(RECIPE_INGREDIENT)));

        onView(withRecyclerView(R.id.steps_recyclerView).atPositionOnView(0, R.id.short_description))
                .check(matches(isDisplayed()))
                .check(matches(withText(RECIPE_STEP_SHORT_DESC)));
    }

    @Test
    public void stepDetailsTest() {
        onView(withRecyclerView(R.id.recipes_recycler_view).atPosition(0))
                .perform(click());

        onView(withRecyclerView(R.id.steps_recyclerView).atPosition(0))
                .perform(click());

        ToolbarViewMatcher.matchToolbarTitle(STEP_TITLE);

        onView(allOf(withId(R.id.description), isDisplayed())).check(matches(withText(RECIPE_STEP_DESCRIPTION)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            mIdlingRegistry.unregister(mIdlingResource);
        }
    }

    private RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
