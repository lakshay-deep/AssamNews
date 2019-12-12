package com.sasuke.demo.assamnews.activity;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.adapter.DetailAdapter;
import com.sasuke.demo.assamnews.adapter.EntertainmentAdapter;
import com.sasuke.demo.assamnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class TemActivity extends AppCompatActivity {

    private static final String EXTRA_VIDEO_URL = "EXTRA_VIDEO_URL";
    private static final String EXTRA_VIDEO_POSITION = "EXTRA_VIDEO_POSITION";

    private Boolean isInPipMode = false;
    private String mUrl;
    private SimpleExoPlayer player;
    private Long videoPosition = 0L;
    private Boolean isPIPModeEnabled = true;
    private PlayerView playerView;

    private RecyclerView recyclerView;
    private List<News> detailList = new ArrayList<>();
    private DetailAdapter detailAdapter;


    //Try implementing or using butterknife here if it doesn't work

    public static Intent newIntent(Context context, String videoUrl) {
        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra(EXTRA_VIDEO_URL, videoUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        playerView = findViewById(R.id.playerView);
        mUrl = "https://editorji.akamaized.net/cue_ts/Airtel/NationalPlaylist.m3u8";

        recyclerView = findViewById(R.id.rvDetail);
        detailAdapter = new DetailAdapter(detailList);
        LinearLayoutManager eLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailAdapter);
        recyclerView.addItemDecoration(new ItemDecorator(10)) ;
        prepareDetailData();
        initListener();

//        if (getIntent().getExtras() == null || !getIntent().hasExtra(EXTRA_VIDEO_URL)) {
//            finish();
//        }
//
//        if (getIntent() != null) {
//            mUrl = getIntent().getStringExtra(EXTRA_VIDEO_URL);
//        }

        if (savedInstanceState != null) {
            videoPosition = savedInstanceState.getLong(EXTRA_VIDEO_POSITION);
        }
    }

    private  void  initListener(){
        detailAdapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(TemActivity.newIntent(getApplicationContext(),""));
            }
        });
    }

    private void prepareDetailData() {
        News detail;
        detail = new News("DY365", R.drawable.dy365_medium);
        detailList.add(detail);
        detail = new News("Aaj Tak", R.drawable.aaj_tak_medium);
        detailList.add(detail);
        detail = new News("PratedinTime", R.drawable.pratedin_medium);
        detailList.add(detail);
        detail = new News("PragNews", R.drawable.prag_news_medium);
        detailList.add(detail);
        detail = new News("DY365", R.drawable.dy365_medium);
        detailList.add(detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());

        playerView.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, getApplicationInfo().loadLabel(getPackageManager()).toString())
        );
        int switchVal = Util.inferContentType(Uri.parse(mUrl));
        switch (switchVal) {
            case C.TYPE_HLS: {
                MediaSource mediaSource = new
                        HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mUrl));
                player.prepare(mediaSource);
            }
            break;

            case C.TYPE_OTHER: {
                MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mUrl));
                player.prepare(mediaSource);
            }
            break;

            default: {
                //This is to catch SmoothStreaming and DASH types which are not supported currently
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
            break;
        }
        final boolean[] returnResultOnce = {true};

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_READY && returnResultOnce[0]) {
                    setResult(Activity.RESULT_OK);
                    returnResultOnce[0] = false;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                setResult(Activity.RESULT_CANCELED);
                finishAndRemoveTask();
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true);

        MediaSessionCompat mediaSession = new MediaSessionCompat(this, getPackageName());
        MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSession);
        mediaSessionConnector.setPlayer(player, null);
        mediaSession.setActive(true);
    }

    @Override
    public void onPause() {
        videoPosition = player.getCurrentPosition();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPosition > 0L && !isInPipMode) {
            player.seekTo(videoPosition);
        }
        //Makes sure that the media controls pop up on resuming and when going between PIP and non-PIP states.
        playerView.setUseController(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        //PIPmode activity.finish() does not remove the activity from the recents stack.
        //Only finishAndRemoveTask does this.
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            finishAndRemoveTask();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        videoPosition = savedInstanceState.getLong(EXTRA_VIDEO_POSITION);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
                && isPIPModeEnabled
        ) {
            enterPIPMode();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {

        if (newConfig != null) {
            videoPosition = player.getCurrentPosition();
            isInPipMode = !isInPictureInPictureMode;
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    @Override
    protected void onUserLeaveHint() {
        //Called when the user touches the Home or Recents button to leave the app.
        super.onUserLeaveHint();
        enterPIPMode();
    }

    void enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            videoPosition = player.getCurrentPosition();
            playerView.setUseController(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
                this.enterPictureInPictureMode(params.build());
            } else {
                this.enterPictureInPictureMode();
            }
            new Handler().postDelayed(this::checkPIPPermission, 30);
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    void checkPIPPermission() {
        isPIPModeEnabled = isInPictureInPictureMode();
        if (!isInPictureInPictureMode()) {
            onBackPressed();
        }
    }
}
