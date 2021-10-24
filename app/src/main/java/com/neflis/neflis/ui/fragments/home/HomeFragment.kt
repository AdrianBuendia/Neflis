package com.neflis.neflis.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.neflis.neflis.R
import com.neflis.neflis.cases.login.LoginViewModel
import com.neflis.neflis.cases.login.LoginViewModelFactory
import com.neflis.neflis.cases.mostPopularMovies.MostPopularMoviesViewModel
import com.neflis.neflis.cases.mostPopularMovies.MostPopularMoviesViewModelFactory
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMovie
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMoviesResponse
import com.neflis.neflis.core.util.Status
import com.neflis.neflis.databinding.FragmentHomeBinding
import com.neflis.neflis.ui.adapters.MostPopularMoviesListAdapter
import com.neflis.neflis.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(requireContext())
    }
    private val mostPopularMoviesViewModel: MostPopularMoviesViewModel by activityViewModels {
        MostPopularMoviesViewModelFactory(requireContext())
    }
    private val mostPopularMoviesAdapter: MostPopularMoviesListAdapter by lazy {
        MostPopularMoviesListAdapter()
    }
    private lateinit var _binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home, container, false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateUserLoggedAndReturnScreen()

        loginViewModel.loginResource.observe(viewLifecycleOwner, {
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
                        res.data?.let { loginResponse ->
                            _binding.rvMostPopularMovies.adapter = mostPopularMoviesAdapter
                            mostPopularMoviesViewModel.getMostPopularMovies("1")
                        }
                    }
                }
            }
        })

        mostPopularMoviesViewModel.mostPopularMoviesResource.observe(viewLifecycleOwner, {
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
                        res.data?.let { mostPopularMoviesResponse ->
                            initViewMostPopularMovies(mostPopularMoviesResponse)
                        }
                    }
                }
            }
        })

    }

    private fun validateUserLoggedAndReturnScreen() {
        if(!TokenManager(requireContext()).isTokenAuthenticated()){
            loginViewModel.login()
        }else if (TokenManager(requireContext()).isTokenAuthenticated() &&
            mostPopularMoviesViewModel.getMostpopularMoviesResponse() == null) {
            _binding.rvMostPopularMovies.adapter = mostPopularMoviesAdapter
            mostPopularMoviesViewModel.getMostPopularMovies("1")
        }else if (mostPopularMoviesViewModel.getMostpopularMoviesResponse() != null) {
            _binding.rvMostPopularMovies.adapter = mostPopularMoviesAdapter
            mostPopularMoviesAdapter.submitList(
                mostPopularMoviesViewModel.getMostPopularMovies()
            )
            initPaginationMostPopularMovies()
            onSelectedMovie()
        }

    }

    private fun initViewMostPopularMovies(mostPopularMoviesResponse: MostPopularMoviesResponse) {

        if (mostPopularMoviesResponse.page == 1)
            mostPopularMoviesAdapter.submitList(
                mostPopularMoviesResponse.results
            )
        else mostPopularMoviesAdapter.submitList(
            mostPopularMoviesAdapter.currentList + mostPopularMoviesResponse.results
        )

        initPaginationMostPopularMovies()
        onSelectedMovie()
    }

    private fun onSelectedMovie() {
        mostPopularMoviesAdapter.onItemClickListener = { popularMovie ->
            safeNav(
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                    popularMovie
                )
            )
        }
    }

    private fun initPaginationMostPopularMovies() {
        _binding.rvMostPopularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //end Scroll
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mostPopularMoviesViewModel.getMostpopularMoviesResponse()?.totalPages != mostPopularMoviesViewModel.getMostpopularMoviesResponse()?.page) {

                        val nextPage =
                            mostPopularMoviesViewModel.getMostpopularMoviesResponse()?.page?.plus(
                                1
                            )

                        mostPopularMoviesViewModel.getMostPopularMovies(
                            nextPage.toString()
                        )
                    }
                }
                //start Scroll
                if (!recyclerView.canScrollHorizontally(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun endLoadingBehavior() {
        _binding.loader.visibility = View.GONE
    }

    private fun beginLoadingBehavior() {
        _binding.loader.visibility = View.VISIBLE
    }

}