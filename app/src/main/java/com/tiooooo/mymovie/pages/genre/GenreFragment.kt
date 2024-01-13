package com.tiooooo.mymovie.pages.genre

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.tiooooo.core.base.BaseFragment
import com.tiooooo.core.extensions.getLaunch
import com.tiooooo.data.movie.api.model.list.Genre
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.FragmentGenreBinding
import com.tiooooo.mymovie.pages.detail.movie.DetailMovieActivity
import com.tiooooo.mymovie.pages.list.ListMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


private const val EXTRA_GENRE = "EXTRA GENRE"
private const val EXTRA_POSITION = "EXTRA POSITION"

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding, GenreActivity>(R.layout.fragment_genre) {
    companion object {
        @JvmStatic
        fun newInstance(genres: ArrayList<Genre>? = null, position: Int) =
            GenreFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_GENRE, genres)
                    putInt(EXTRA_POSITION, position)
                }
            }
    }

    private var listCategory: List<Genre>? = listOf()
    private var position: Int = 0
    private val genreViewModel: GenreViewModel by viewModels()
    private lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listCategory = it.getParcelableArrayList(EXTRA_GENRE)
            position = it.getInt(EXTRA_POSITION, 0)
        }
    }

    override fun initView() {
        listCategory?.let {
            val categoryId = it[position].id.toString()
            genreViewModel.getMoviesByType(categoryId)

            listMovieAdapter = ListMovieAdapter().apply {
                onItemClick = {
                    val intent = Intent(requireActivity(), DetailMovieActivity::class.java).apply {
                        putExtra(DetailMovieActivity.EXTRA_ID, it)
                    }
                    startActivity(intent)
                }
            }
            binding.rvMovie.apply {
                this.adapter = listMovieAdapter
                layoutManager = GridLayoutManager(requireActivity(), 2)
            }
        }
    }

    override fun setSubscribeToLiveData() {
        genreViewModel.movies.observe(this) {
            getLaunch {
                listMovieAdapter.submitData(it)
            }
        }

        getLaunch {
            listMovieAdapter.loadStateFlow.collectLatest {
                with(binding) {
                    progressBar.isVisible = it.refresh is LoadState.Loading
                    progressBarLoadMore.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }
}
