package com.example.movienow.ui.main.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movienow.data.common.Failure
import com.example.movienow.data.common.Success
import com.example.movienow.data.movielist.MovieListRepository
import com.example.movienow.ui.arch.AbstractArchPresenter
import com.example.movienow.ui.main.movielist.MovieListViewContract.*
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * Created by n7 on 8/8/20.
 */
class MovieListPresenter(private val movieListRepository: MovieListRepository) :
    AbstractArchPresenter<View>(), Presenter {

    var currentPage: Int = 1
    var items: MutableList<MovieListingUIModel> = ArrayList()

    override fun process(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.InitialLoad -> {
                // Initial load not needed if list not empty
                if (items.size <= 0) {
                    loadMovies()
                }
            }
            is ViewEvent.LoadMore -> {
                loadMovies(viewEvent.page + 1, true)
            }
        }
    }

    private fun loadMovies(page: Int = 1, append: Boolean = false) {
        launch {
            try {
                currentPage = page

                when (val result = movieListRepository.listing(page)) {
                    is Success -> {
                        val uiModels = mutableListOf<MovieListingUIModel>()
                        for (listing in result.value) {
                            val uiModel = listing.to()
                            uiModels.add(uiModel)
                        }
                        if (append) {
                            items.addAll(uiModels)
                            view?.render(ViewState.LoadMoreDataState(uiModels))
                        } else {
                            items = uiModels
                            view?.render(ViewState.DataState(uiModels))
                        }
                    }
                    is Failure -> {
                        view?.render(ViewState.ErrorState(result.reason))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                view?.render(ViewState.ErrorState(e))
            }
        }
    }

    class Factory(private val movieListRepository: MovieListRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieListPresenter(movieListRepository) as T
        }
    }
}