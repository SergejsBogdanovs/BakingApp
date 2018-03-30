package lv.st.sbogdano.bakingapp.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.idling.SimpleIdlingResource;
import lv.st.sbogdano.bakingapp.ui.recipedetail.RecipeDetailsActivity;

public class RecipesFragment extends Fragment
        implements RecipesAdapter.RecipesAdapterOnItemClickHandler{

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecyclerView;

    @BindView(R.id.recipesLL)
    LinearLayout mRecipesLL;

    @BindView(R.id.noRecipesIcon)
    ImageView mNoRecipesIcon;

    @BindView(R.id.loading_error)
    TextView mLoadingError;

    @BindView(R.id.noRecipesLL)
    LinearLayout mNoRecipesLL;

    @BindView(R.id.loading_indicator)
    ProgressBar mLoadingIndicator;

    Unbinder unbinder;

    private RecipesViewModel mRecipesViewModel;
    private RecipesAdapter mRecipesAdapter;

    // The Idling Resource which will be null in production.
    @Nullable
    private static SimpleIdlingResource mIdlingResource;


    public RecipesFragment() {
    }

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecipesViewModel = RecipesActivity.obtainViewModel(getActivity());

        mRecipesViewModel.start();

        // Get the IdlingResource instance
        getIdlingResource();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecipesAdapter();
        subscribeDataStream();
    }

    private void setupRecipesAdapter() {
        mRecipesRecyclerView.setHasFixedSize(true);
        int gridColumnsNumber = getResources().getInteger(R.integer.grid_column_number);
        mRecipesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), gridColumnsNumber));
        mRecipesAdapter = new RecipesAdapter(getContext(), new ArrayList<>(), this);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);
    }

    private void subscribeDataStream() {
        // For testing
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        mRecipesViewModel.getRecipes().observe(getActivity(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case SUCCESS:
                        if (listResource.data != null) {
                            showRecipesInUI(listResource.data);
                        } else {
                            showErrorMessage();
                        }
                        break;
                    case ERROR:
                        showErrorMessage();
                        break;
                    case LOADING:
                        showLoading(true);
                }
            }
        });
    }

    private void showRecipesInUI(List<RecipeEntry> data) {
        showLoading(false);
        mRecipesAdapter.setRecipes(data);

        // For testing
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }

    private void showErrorMessage() {
        mRecipesLL.setVisibility(View.GONE);
        mNoRecipesLL.setVisibility(View.VISIBLE);
    }

    private void showLoading(boolean showLoading) {
        if (showLoading) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(RecipeEntry recipeEntry) {
        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipeEntry);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
