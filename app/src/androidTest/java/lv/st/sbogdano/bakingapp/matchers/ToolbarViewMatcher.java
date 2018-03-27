package lv.st.sbogdano.bakingapp.matchers;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;

public class ToolbarViewMatcher {

    public static ViewInteraction matchToolbarTitle(String title) {
        return onView(ViewMatchers.isAssignableFrom(Toolbar.class))
                .check(ViewAssertions.matches(withToolbarTitle(Matchers.is(title))));
    }

    private static Matcher<Object> withToolbarTitle(Matcher<CharSequence> title) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title");
                title.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar item) {
                return title.matches(item.getTitle());
            }
        };
    }
}
