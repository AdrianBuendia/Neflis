package com.neflis.neflis.ui.fragments.home

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.neflis.neflis.R
import com.neflis.neflis.cases.video.VideoViewModel
import com.neflis.neflis.cases.video.VideoViewModelFactory
import com.neflis.neflis.core.Constants
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMovie
import com.neflis.neflis.core.util.Status
import com.neflis.neflis.databinding.FragmentMovieDetailBinding
import com.neflis.neflis.ui.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieDetailFragment : BaseFragment() {

    private val videoViewModel: VideoViewModel by viewModels {
        VideoViewModelFactory(requireContext())
    }
    private var mostPopularMovie: MostPopularMovie? = null
    private lateinit var _binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            mostPopularMovie = it.get("mostPopularMovies") as MostPopularMovie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_movie_detail, container, false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        videoViewModel.getMostPopularMovies(mostPopularMovie?.id.toString())
        videoViewModel.videoResource.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { res ->
                when (res.status) {
                    Status.LOADING -> {
                        beginLoadingBehavior()
                    }
                    Status.ERROR -> {
                        endLoadingBehavior()
                        showError(
                            getErrorMessageFromResource(res)
                        )
                    }
                    Status.SUCCESS -> {
                        endLoadingBehavior()
                        res.data?.let { videoResponse ->
                            if (!videoResponse.results.isNullOrEmpty()) {
                                initVideo(videoResponse.results[0].key)
                            } else {
                                showError(getString(R.string.empty_videos))
                                _binding.youtubePlayerView.visibility = View.GONE
                            }

                        }
                    }
                }
            }
        })
    }

    private fun endLoadingBehavior() {
        _binding.youtubePlayerView.visibility = View.VISIBLE
        _binding.loader.visibility = View.GONE
    }

    private fun beginLoadingBehavior() {
        _binding.loader.visibility = View.VISIBLE
        _binding.youtubePlayerView.visibility = View.GONE
    }

    private fun initView() {
        _binding.item = mostPopularMovie
        initImageMovie()
    }

    private fun initImageMovie() {
        val uri: Uri =
            Uri.parse("${Constants.BASE_URL_IMAGES}${Constants.W500_RESOLUTION}${mostPopularMovie?.posterPath}")
        val draweeView = _binding.imgMovie
        draweeView.setImageURI(uri)
    }

    private fun initVideo(videID: String) {
        lifecycle.addObserver(_binding.youtubePlayerView)
        _binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videID, 0f)
                youTubePlayer.pause()
            }
        })
    }

}