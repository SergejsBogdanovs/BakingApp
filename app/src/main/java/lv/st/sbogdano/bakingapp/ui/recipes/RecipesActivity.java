package lv.st.sbogdano.bakingapp.ui.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.ViewModelFactory;
import lv.st.sbogdano.bakingapp.util.ActivityUtils;

public class RecipesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set up the toolbar
        setSupportActionBar(mToolbar);

        setupViewFragment();
    }

    public static RecipesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory viewModelFactory
                = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, viewModelFactory).get(RecipesViewModel.class);
    }

    private void setupViewFragment() {
        RecipesFragment recipesFragment =
                (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipesFragment == null) {
            // Create the fragment
            recipesFragment = RecipesFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(
                    getSupportFragmentManager(), recipesFragment, R.id.contentFrame);
        }
    }
}

