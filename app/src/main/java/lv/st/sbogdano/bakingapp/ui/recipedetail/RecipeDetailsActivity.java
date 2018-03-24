package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.ViewModelFactory;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.model.Ingredient;
import lv.st.sbogdano.bakingapp.data.model.Recipe;
import lv.st.sbogdano.bakingapp.ui.widget.RecipeWidgetManager;
import lv.st.sbogdano.bakingapp.util.ActivityUtils;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnIngredientDataPass{

    public static final String EXTRA_RECIPE = "RECIPE";
    public static final String KEY_TITLE = "TITLE";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RecipeEntry mRecipeEntry;
    private List<IngredientEntry> mIngredientEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            mRecipeEntry = getIntent().getParcelableExtra(EXTRA_RECIPE);
        }

        setupToolbar();

        RecipeDetailsFragment recipeDetailsFragment = findOrCreateViewFragment();

        ActivityUtils.replaceFragmentToActivity(
                getSupportFragmentManager(),
                recipeDetailsFragment,
                R.id.contentFrame);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_widget:
                RecipeWidgetManager recipeWidgetManager = new RecipeWidgetManager();
                recipeWidgetManager.updateRecipeWidget(mRecipeEntry, mIngredientEntries);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataPass(List<IngredientEntry> ingredientEntries) {
        mIngredientEntries = new ArrayList<>();
        mIngredientEntries.addAll(ingredientEntries);
    }
}
