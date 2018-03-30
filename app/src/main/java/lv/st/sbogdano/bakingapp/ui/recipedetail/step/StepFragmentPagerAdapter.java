package lv.st.sbogdano.bakingapp.ui.recipedetail.step;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

public class StepFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<StepEntry> mStepEntries;

    StepFragmentPagerAdapter(FragmentManager fm, List<StepEntry> stepEntries) {
        super(fm);
        mStepEntries = stepEntries;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepFragment.newInstance(mStepEntries.get(position));
    }

    @Override
    public int getCount() {
        return mStepEntries.size();
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Step " + position;
    }

}
