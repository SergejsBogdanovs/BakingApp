package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.data.model.Step;
import lv.st.sbogdano.bakingapp.ui.recipes.RecipesAdapter;

class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    private List<StepEntry> mSteps;

    private final StepsAdapterOnItemClickHandler mClickHandler;

    public interface StepsAdapterOnItemClickHandler {
        void onStepItemClick(int position);
    }

    public StepsAdapter(List<StepEntry> steps, StepsAdapterOnItemClickHandler clickHandler) {
        mSteps = steps;
        mClickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View stepView = inflater.inflate(R.layout.step_item, parent, false);
        return new ViewHolder(stepView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StepEntry stepEntry = mSteps.get(position);
        holder.mStepId.setText(String.format("%s.", String.valueOf(stepEntry.getStepId())));
        holder.mShortDescription.setText(stepEntry.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setSteps(List<StepEntry> steps) {
        mSteps.clear();
        mSteps.addAll(steps);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_id)
        TextView mStepId;
        @BindView(R.id.short_description)
        TextView mShortDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            //StepEntry stepEntry = mSteps.get(getAdapterPosition());
            mClickHandler.onStepItemClick(getAdapterPosition());
        }
    }
}
