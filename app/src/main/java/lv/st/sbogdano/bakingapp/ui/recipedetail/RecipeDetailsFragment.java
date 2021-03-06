package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.ui.recipedetail.step.RecipeStepActivity;
import lv.st.sbogdano.bakingapp.ui.recipedetail.step.RecipeStepFragment;
import lv.st.sbogdano.bakingapp.util.ActivityUtils;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepsAdapterOnItemClickHandler {

    private static final String ARGUMENT_RECIPE = "RECIPE";
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout_state";

    @BindView(R.id.ingredients_label)
    TextView mIngredientsLabel;

    @BindView(R.id.ingredients_recyclerView)
    RecyclerView mIngredientsRecyclerView;

    @BindView(R.id.steps_recyclerView)
    RecyclerView mStepsRecyclerView;

    Unbinder unbinder;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    private boolean mTwoPane = false;

    private RecipeEntry mRecipeEntry;
    private RecipeDetailsViewModel mDetailsViewModel;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;

    private List<StepEntry> mStepEntries = new ArrayList<>();
    private OnIngredientDataPass mOnIngredientDataPass;

    private static Bundle mBundleRecyclerViewState;
    private Parcelable mState;

    public RecipeDetailsFragment() {

    }

    public interface OnIngredientDataPass {
        void onDataPass(List<IngredientEntry> ingredientEntries);
    }

    public static RecipeDetailsFragment newInstance(RecipeEntry recipeEntry) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_RECIPE, recipeEntry);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (view.findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

        mDetailsViewModel = RecipeDetailsActivity.obtainViewModel(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mRecipeEntry = getArguments().getParcelable(ARGUMENT_RECIPE);
        }

        // Restore scroll position
        if (savedInstanceState != null) {
            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if(position != null)
                mNestedScrollView.post(() -> mNestedScrollView.scrollTo(position[0], position[1]));
        }

        setupIngredientsAdapter();
        setupStepsAdapter();

        subscribeDataStream();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnIngredientDataPass = (OnIngredientDataPass) context;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Saving scroll position
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{ mNestedScrollView.getScrollX(), mNestedScrollView.getScrollY()});
    }

    private void setupIngredientsAdapter() {
        mIngredientsRecyclerView.setHasFixedSize(true);
        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
    }

    private void setupStepsAdapter() {
        mStepsRecyclerView.setHasFixedSize(true);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStepsAdapter = new StepsAdapter(new ArrayList<>(), this);
        mStepsRecyclerView.setAdapter(mStepsAdapter);
    }

    private void subscribeDataStream() {

        mDetailsViewModel.getIngredients(mRecipeEntry.getId()).observe(getActivity(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case SUCCESS:
                        if (listResource.data != null) {
                            showIngredientsInUI(listResource.data);
                            mOnIngredientDataPass.onDataPass(listResource.data);
                        }
                }
            }
        });

        mDetailsViewModel.getSteps(mRecipeEntry.getId()).observe(getActivity(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case SUCCESS:
                        if (listResource.data != null) {
                            showStepsInUI(listResource.data);
                        }
                }
            }
        });
    }

    private void showIngredientsInUI(List<IngredientEntry> data) {
        mIngredientsAdapter.setRecipes(data);
    }

    private void showStepsInUI(List<StepEntry> data) {
        mStepsAdapter.setSteps(data);
        mStepEntries.addAll(data);
        //mStepsRecyclerView.getLayoutManager().onRestoreInstanceState(mState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStepItemClick(int position) {
        if (mTwoPane) {
            RecipeStepFragment stepPagerFragment
                    = RecipeStepFragment.newInstance(mStepEntries.get(position));
            ActivityUtils.replaceFragmentToActivity(
                    getActivity().getSupportFragmentManager(),
                    stepPagerFragment,
                    R.id.recipe_detail_container);
        } else {
            Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
            intent.putParcelableArrayListExtra(
                    RecipeStepActivity.EXTRA_STEPS, (ArrayList<? extends Parcelable>) mStepEntries);
            intent.putExtra(RecipeStepActivity.EXTRA_POSITION, position);
            startActivity(intent);
        }
    }
}
