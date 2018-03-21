package lv.st.sbogdano.bakingapp.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import lv.st.sbogdano.bakingapp.ui.recipedetail.RecipeDetailsActivity;

public class RecipesFragment extends Fragment
        implements RecipesAdapter.RecipesAdapterOnItemClickHandler{

    public static final String TAG = RecipesFragment.class.getName();

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

    public RecipesFragment() {
    }

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecipesViewModel = RecipesActivity.obtainViewModel(getActivity());

        mRecipesViewModel.start();

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
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecipesAdapter = new RecipesAdapter(new ArrayList<>(), this);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);
    }

    private void subscribeDataStream() {
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

}
