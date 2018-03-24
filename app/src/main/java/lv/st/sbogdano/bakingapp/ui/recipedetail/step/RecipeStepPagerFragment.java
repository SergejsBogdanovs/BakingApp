package lv.st.sbogdano.bakingapp.ui.recipedetail.step;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

public class RecipeStepPagerFragment extends Fragment {

    private static final String ARGUMENT_STEPS = "STEPS";
    private static final String ARGUMENT_POSITION = "POSITION";

    @BindView(R.id.pager)
    ViewPager mPager;

    Unbinder unbinder;

    private List<StepEntry> mStepEntries;
    private int mPagerPosition;

    public static RecipeStepPagerFragment newInstance(List<StepEntry> stepEntries, int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARGUMENT_STEPS, (ArrayList<? extends Parcelable>) stepEntries);
        arguments.putInt(ARGUMENT_POSITION, position);
        RecipeStepPagerFragment fragment = new RecipeStepPagerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStepEntries = getArguments().getParcelableArrayList(ARGUMENT_STEPS);
        mPagerPosition = getArguments().getInt(ARGUMENT_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_step_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SmartFragmentStatePagerAdapter fragmentPagerAdapter
                = new StepPager(getChildFragmentManager());
        mPager.setAdapter(fragmentPagerAdapter);
        mPager.setCurrentItem(mPagerPosition);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mStepEntries.get(position).getShortDescription());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class StepPager extends SmartFragmentStatePagerAdapter {

        public StepPager(FragmentManager fm) {
            super(fm);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
