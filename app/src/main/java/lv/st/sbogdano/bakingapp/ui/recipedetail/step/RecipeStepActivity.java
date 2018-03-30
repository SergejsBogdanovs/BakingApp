package lv.st.sbogdano.bakingapp.ui.recipedetail.step;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String EXTRA_STEPS = "STEPS";
    public static final String EXTRA_POSITION = "POSITION";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager)
    ViewPager mPager;

    private List<StepEntry> mStepEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        mStepEntries = getIntent().getParcelableArrayListExtra(EXTRA_STEPS);
        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setTitle(mStepEntries.get(position).getShortDescription());
        }

        StepFragmentPagerAdapter adapter =
                new StepFragmentPagerAdapter(getSupportFragmentManager(), mStepEntries);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(position);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (ab != null) {
                    ab.setTitle(mStepEntries.get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
