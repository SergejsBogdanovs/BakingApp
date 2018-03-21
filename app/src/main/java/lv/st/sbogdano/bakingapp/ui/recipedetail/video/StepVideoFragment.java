package lv.st.sbogdano.bakingapp.ui.recipedetail.video;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.ui.recipedetail.RecipeDetailsFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepVideoFragment extends Fragment {

    private static final String ARGUMENT_STEP = "STEP";

    public static StepVideoFragment newInstance(StepEntry stepEntry) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_STEP, stepEntry);
        StepVideoFragment fragment = new StepVideoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public StepVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_video, container, false);
    }

}
