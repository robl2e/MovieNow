package com.example.movienow.ui.main.movielist

import com.example.movienow.ui.arch.ArchPresenter
import com.example.movienow.ui.arch.ArchView
import com.example.movienow.util.observable.Observable
import java.lang.Exception

/**
 * Created by n7 on 8/8/20.
 */

interface MovieListViewContract {

    interface View : ArchView, Observable<ViewListener> {
        fun render(viewState: ViewState)
    }

    interface Presenter : ArchPresenter<View> {
        fun process(viewEvent: ViewEvent)
    }

    interface ViewListener {
        fun onEvent(event: ViewEvent)
    }

    sealed class ViewState {
        data class DataState(val movieListings: MutableList<MovieListingUIModel>) : ViewState()
        data class ErrorState(val exception: Exception) : ViewState()
        data class LoadMoreDataState(
            val movieListings: MutableList<MovieListingUIModel>
        ) : ViewState()
    }

    sealed class ViewEvent {
        object InitialLoad : ViewEvent()
        data class LoadMore(val page: Int) : ViewEvent()
    }
}
