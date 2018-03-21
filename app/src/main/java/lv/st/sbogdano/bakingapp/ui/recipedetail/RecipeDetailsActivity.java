package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.ViewModelFactory;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.model.Recipe;
import lv.st.sbogdano.bakingapp.util.ActivityUtils;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "RECIPE";
    public static final String KEY_TITLE = "TITLE";
    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RecipeDetailsViewModel mRecipeViewModel;
    private RecipeEntry mRecipeEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            mRecipeEntry = getIntent().getParcelableExtra(EXTRA_RECIPE);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TITLE)) {
            mRecipeEntry = savedInstanceState.getParcelable(KEY_TITLE);
        }

        setupToolbar();

        RecipeDetailsFragment recipeDetailsFragment = findOrCreateViewFragment();

        ActivityUtils.replaceFragmentToActivity(
                getSupportFragmentManager(),
                recipeDetailsFragment,
                R.id.contentFrame);

        mRecipeViewModel = obtainViewModel(this);
    }

    public static RecipeDetailsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(RecipeDetailsViewModel.class);
    }

    private RecipeDetailsFragment findOrCreateViewFragment() {


        RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (recipeDetailsFragment == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(mRecipeEntry);
        }
        return recipeDetailsFragment;
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle(mRecipeEntry.getName());
    }
}
