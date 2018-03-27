package lv.st.sbogdano.bakingapp.ui.recipedetail.step;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends Fragment {

    private static final String ARGUMENT_STEP = "STEP";

    @BindView(R.id.exo_player_view)
    PlayerView mExoPlayerView;

    @BindView(R.id.description)
    TextView mDescription;

    Unbinder unbinder;

    private SimpleExoPlayer mPlayer;
    private StepEntry mStepEntry;


    public static RecipeStepFragment newInstance(StepEntry stepEntry) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_STEP, stepEntry);
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepEntry = getArguments().getParcelable(ARGUMENT_STEP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set description
        mDescription.setText(mStepEntry.getDescription());

        // Set ExoPlayer if video exist
        if (mStepEntry.getVideoURL() == null || mStepEntry.getVideoURL().isEmpty()) {
            mExoPlayerView.setVisibility(View.GONE);
        } else {
            initializePlayer();
        }
    }

    private void initializePlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory
                = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mExoPlayerView.setPlayer(mPlayer);

        Uri videoUri = Uri.parse(mStepEntry.getVideoURL());

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                getContext(),
                Util.getUserAgent(getContext(), "BakingApp"),
                defaultBandwidthMeter);

        //DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

//        ExtractorMediaSource videoSource = new ExtractorMediaSource(
//                videoUri,
//                dataSourceFactory,
//                extractorsFactory,
//                null,
//                null);

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);

        mPlayer.prepare(videoSource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
