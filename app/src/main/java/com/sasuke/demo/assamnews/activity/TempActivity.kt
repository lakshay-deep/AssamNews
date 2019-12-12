package com.sasuke.demo.assamnews.activity

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.session.MediaSessionCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sasuke.demo.assamnews.R
import com.sasuke.demo.assamnews.adapter.DetailAdapter
import com.sasuke.demo.assamnews.adapter.ViewPagerAdapter
import com.sasuke.demo.assamnews.model.News
import kotlinx.android.synthetic.main.activity_temp.*
import kotlinx.android.synthetic.main.fragment_empty.*

class TempActivity : AppCompatActivity() {

    companion object {
        private const val TOTAL_PAGE_COUNT = 3
        private const val EXTRA_VIDEO_URL = "EXTRA_VIDEO_URL"
        private const val EXTRA_VIDEO_POSITION = "EXTRA_VIDEO_POSITION"
        fun newIntent(context: Context, videoUrl: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra(EXTRA_VIDEO_URL, videoUrl)
            return intent
        }
    }

    private val detailList: ArrayList<News> = ArrayList()
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    var isInPipMode: Boolean = false
    lateinit var mUrl: String
    lateinit var player: SimpleExoPlayer
    private var videoPosition: Long = 0L
    var isPIPModeEnabled: Boolean = true //Has the user disabled PIP mode in AppOpps?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        mUrl = "https://editorji.akamaized.net/cue_ts/Airtel/NationalPlaylist.m3u8"
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailAdapter = DetailAdapter(detailList)
        rvDetail.layoutManager = linearLayoutManager
        rvDetail.adapter = detailAdapter
        rvDetail.itemAnimator = DefaultItemAnimator()
        rvDetail.addItemDecoration(ItemDecorator(10))
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, TOTAL_PAGE_COUNT)
        vpAdvertisment.adapter = viewPagerAdapter
        pagerIndicator.setViewPager(vpAdvertisment)
        prepareDetailData()
        initListener()
//
//        if (intent.extras == null || !intent.hasExtra(EXTRA_VIDEO_URL)) {
//            finish()
//        }
//
//        intent?.let {
//            mUrl = it.getStringExtra(EXTRA_VIDEO_URL)!!
//        }

        savedInstanceState?.let { videoPosition = savedInstanceState.getLong(EXTRA_VIDEO_POSITION) }
    }

    private fun prepareDetailData() {
        var detail = News("DY365", R.drawable.dy365_medium)
        detailList.add(detail)
        detail = News("Aaj Tak", R.drawable.aaj_tak_medium)
        detailList.add(detail)
        detail = News("PratedinTime", R.drawable.pratedin_medium)
        detailList.add(detail)
        detail = News("PragNews", R.drawable.prag_news_medium)
        detailList.add(detail)
        detail = News("DY365", R.drawable.dy365_medium)
        detailList.add(detail)
    }

    private fun initListener() {
        detailAdapter.setOnItemClickListener { view, position ->
            //            TODO: Open whatever activity you want to open
//            startActivity()
        }
    }

    override fun onStart() {
        super.onStart()


        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())

        playerView.player = player

        val dataSourceFactory = DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, applicationInfo.loadLabel(packageManager).toString())
        )

        when (Util.inferContentType(Uri.parse(mUrl))) {
            C.TYPE_HLS -> {
                val mediaSource =
                        HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mUrl))
                player.prepare(mediaSource)
            }

            C.TYPE_OTHER -> {
                val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mUrl))
                player.prepare(mediaSource)
            }

            else -> {
                //This is to catch SmoothStreaming and DASH types which are not supported currently
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

        var returnResultOnce: Boolean = true

        player.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}

            override fun onRepeatModeChanged(repeatMode: Int) {}

            override fun onPositionDiscontinuity(reason: Int) {}

            override fun onLoadingChanged(isLoading: Boolean) {}

            override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
            ) {
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}

            override fun onPlayerError(error: ExoPlaybackException?) {
                setResult(Activity.RESULT_CANCELED)
                finishAndRemoveTask()
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_READY && returnResultOnce) {
                    setResult(Activity.RESULT_OK)
                    returnResultOnce = false
                }
            }

            override fun onSeekProcessed() {}
        })
        player.playWhenReady = true

        //Use Media Session Connector from the EXT library to enable MediaSession Controls in PIP.
        val mediaSession = MediaSessionCompat(this, packageName)
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(player, null)
        mediaSession.isActive = true
    }

    override fun onPause() {
        videoPosition = player.currentPosition
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        pagerIndicator.visibility = View.VISIBLE
        if (videoPosition > 0L && !isInPipMode) {
            player.seekTo(videoPosition)
        }
        //Makes sure that the media controls pop up on resuming and when going between PIP and non-PIP states.
        playerView.useController = true
    }

    override fun onStop() {
        super.onStop()
        playerView.player = null
        player.release()
        //PIPmode activity.finish() does not remove the activity from the recents stack.
        //Only finishAndRemoveTask does this.
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            finishAndRemoveTask()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        videoPosition = savedInstanceState!!.getLong(EXTRA_VIDEO_POSITION)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
                && isPIPModeEnabled
        ) {
            enterPIPMode()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPictureInPictureModeChanged(
            isInPictureInPictureMode: Boolean,
            newConfig: Configuration?
    ) {
        if (newConfig != null) {
            videoPosition = player.currentPosition
            isInPipMode = !isInPictureInPictureMode
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
    }

    //Called when the user touches the Home or Recents button to leave the app.
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPIPMode()
    }

    @Suppress("DEPRECATION")
    fun enterPIPMode() {
        pagerIndicator.visibility = View.GONE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            videoPosition = player.currentPosition
            playerView.useController = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val params = PictureInPictureParams.Builder()
                this.enterPictureInPictureMode(params.build())
            } else {
                this.enterPictureInPictureMode()
            }

            Handler().postDelayed({ checkPIPPermission() }, 10)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkPIPPermission() {
        isPIPModeEnabled = isInPictureInPictureMode
        if (!isInPictureInPictureMode) {
            onBackPressed()
        }
    }

    //--------------EMPTY FRAGMENT FOR VIEW PAGER--------------

    class EmptyFragment : Fragment() {

        private var position: Int = 0

        companion object {
            private const val EXTRA_POSITION = "EXTRA_POSITION"
            internal fun newInstance(position: Int): EmptyFragment {
                val fragment = EmptyFragment()
                val bundle = Bundle()
                bundle.putInt(EXTRA_POSITION, position)
                fragment.arguments = bundle
                return fragment
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                position = it.getInt(EXTRA_POSITION, 0)
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_empty, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            when (position) {
                0 -> {
                    ivAdvertisementImage.setImageResource(R.drawable.aaj_tak_medium)
                    tvAdvertisementText.text = "Aaj Tak Sabse Aage"
                    tvAdvertisementText.movementMethod = LinkMovementMethod.getInstance()
                    tvAdvertisementText.highlightColor = Color.BLUE
                }
                1 -> {
                    ivAdvertisementImage.setImageResource(R.drawable.aastha_tv_medium)
                    tvAdvertisementText.text = "Aastha ki prakshta hai dhyan"
                    tvAdvertisementText.movementMethod = LinkMovementMethod.getInstance()
                    tvAdvertisementText.highlightColor = Color.BLUE
                }
                2 -> {
                    ivAdvertisementImage.setImageResource(R.drawable.bhakti_medium)
                    tvAdvertisementText.text = "Bhakti mei hi shakti hai"
                    tvAdvertisementText.movementMethod = LinkMovementMethod.getInstance()
                    tvAdvertisementText.highlightColor = Color.BLUE
                }
            }
        }
    }
}