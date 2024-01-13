package com.tiooooo.mymovie.pages.videoPlayer

import android.content.pm.ActivityInfo
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.getLifeCycle
import com.tiooooo.mymovie.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : BaseActivity<ActivityVideoPlayerBinding>() {
    companion object {
        const val EXTRA_KEY = "EXTRA_KEY"
    }

    override fun getViewBinding() = ActivityVideoPlayerBinding.inflate(layoutInflater)
    private lateinit var youTubePlayer: YouTubePlayer

    private var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun initView() {
        val extraKey = intent.getStringExtra(EXTRA_KEY) ?: ""
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        binding.apply {
            getLifeCycle().addObserver(youtubePlayerView)
            val iFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1) // enable full screen button
                .build()

            youtubePlayerView.enableAutomaticInitialization = false

            youtubePlayerView.addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    isFullscreen = true

                    // the video will continue playing in fullscreenView
                    youtubePlayerView.visibility = View.GONE
                    fullScreenViewContainer.visibility = View.VISIBLE
                    fullScreenViewContainer.addView(fullscreenView)

                    // optionally request landscape orientation
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }

                override fun onExitFullscreen() {
                    isFullscreen = false

                    // the video will continue playing in the player
                    youtubePlayerView.visibility = View.VISIBLE
                    fullScreenViewContainer.visibility = View.GONE
                    fullScreenViewContainer.removeAllViews()
                }
            })

            youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@VideoPlayerActivity.youTubePlayer = youTubePlayer
                    youTubePlayer.loadVideo(extraKey, 0f)
                    youTubePlayer.toggleFullscreen()
                }
            }, iFramePlayerOptions)
        }
    }
}
