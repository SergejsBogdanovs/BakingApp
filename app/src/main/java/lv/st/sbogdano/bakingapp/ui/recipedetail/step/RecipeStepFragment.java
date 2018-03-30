package lv.st.sbogdano.bakingapp.ui.recipedetail.step;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends Fragment {

    private static final String ARGUMENT_STEP = "STEP";

    private static final String BUNDLE_POSITION = "POSITION";
    private static final String BUNDLE_PLAY_WHEN_READY = "READY";

    @BindView(R.id.exo_player_view)
    PlayerView mExoPlayerView;

    @BindView(R.id.description)
    TextView mDescription;

    @BindView(R.id.step_image)
    ImageView mStepImage;

    Unbinder unbinder;

    private SimpleExoPlayer mPlayer;
    private StepEntry mStepEntry;

    private long mPlayerPosition = 0;
    private boolean mPlayWhenReady = true;


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

        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(BUNDLE_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(BUNDLE_PLAY_WHEN_READY);
        }

        Log.v(TAG, "onCreateView: " + mStepEntry.getDescription());
        // Set description
        mDescription.setText(mStepEntry.getDescription());

        if (!mStepEntry.getThumbnailURL().isEmpty()) {
            Glide.with(this)
                    .load(mStepEntry.getThumbnailURL())
                    .into(mStepImage);
            mStepImage.setVisibility(View.VISIBLE);
        }

        return view;
    }


    private void initializePlayer() {
        if (mPlayer == null) {

            Uri videoUri = Uri.parse(mStepEntry.getVideoURL());

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mExoPlayerView.setPlayer(mPlayer);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    getContext(),
                    Util.getUserAgent(getContext(), "BakingApp"),
                    defaultBandwidthMeter);

            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            mPlayer.prepare(videoSource);

            if (mPlayerPosition != 0) {
                mPlayer.seekTo(mPlayerPosition);
            }

            mPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(BUNDLE_POSITION, mPlayerPosition);
        outState.putBoolean(BUNDLE_PLAY_WHEN_READY, mPlayWhenReady);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mStepEntry.getVideoURL())) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            mPlayWhenReady = mPlayer.getPlayWhenReady();

            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
