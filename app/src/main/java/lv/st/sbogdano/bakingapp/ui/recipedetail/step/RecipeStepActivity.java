package lv.st.sbogdano.bakingapp.ui.recipedetail.step;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.util.ActivityUtils;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String EXTRA_STEPS = "STEPS";
    public static final String EXTRA_POSITION = "POSITION";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<StepEntry> mStepEntries;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        mStepEntries = getIntent().getParcelableArrayListExtra(EXTRA_STEPS);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setTitle(mStepEntries.get(mPosition).getShortDescription());
        }

        if (savedInstanceState == null) {
            RecipeStepPagerFragment recipeStepPagerFragment = findOrCreateViewFragment();

            ActivityUtils.replaceFragmentToActivity(
                    getSupportFragmentManager(),
                    recipeStepPagerFragment,
                    R.id.contentFrame);
        }
    }

    private RecipeStepPagerFragment findOrCreateViewFragment() {
        RecipeStepPagerFragment recipeStepPagerFragment = (RecipeStepPagerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (recipeStepPagerFragment == null) {
            recipeStepPagerFragment = RecipeStepPagerFragment.newInstance(mStepEntries, mPosition);
        }
        return recipeStepPagerFragment;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
