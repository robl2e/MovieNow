package com.example.movienow.ui.main.movielist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.R
import com.example.movienow.ui.list.LinearEndlessRecyclerViewScrollListener
import com.example.movienow.ui.main.movielist.MovieListViewContract.*
import com.example.movienow.util.observable.Observable

/**
 * Created by n7 on 8/8/20.
 */
class MovieListProxyView(
    rootView: android.view.View,
    items: MutableList<MovieListingUIModel>,
    initialPage: Int = 1
) :
    View, Observable<ViewListener> {

    private val observable: Observable<ViewListener> = Observable.create()
    private val movieListView: RecyclerView = rootView.findViewById(R.id.recycler_view_content)
    private val endlessRecyclerViewScrollListener: LinearEndlessRecyclerViewScrollListener
    private var adapter: MovieListAdapter = MovieListAdapter()

    init {
        adapter.submitList(items) // initialize with current list

        val layoutManager = LinearLayoutManager(rootView.context)
        movieListView.layoutManager = layoutManager
        movieListView.adapter = adapter
        endlessRecyclerViewScrollListener =
            object : LinearEndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    notifyListeners(ViewEvent.LoadMore(page))
                }
            }
        endlessRecyclerViewScrollListener.currentPage = initialPage
        movieListView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    override fun render(viewState: ViewState) {
        when (viewState) {
            is ViewState.DataState -> {
                renderInitialState(viewState.movieListings)
            }
            is ViewState.LoadMoreDataState -> {
                renderLoadMoreState(viewState.movieListings)
            }
        }
    }

    private fun notifyListeners(event: ViewEvent) {
        for (listener in getListeners()) {
            listener.onEvent(event)
        }
    }

    private fun renderInitialState(movieListings: MutableList<MovieListingUIModel>) {
        adapter.submitList(movieListings)
    }

    private fun renderLoadMoreState(movieListings: MutableList<MovieListingUIModel>) {
        val newList = ArrayList(adapter.currentList)
        newList.addAll(movieListings)
        adapter.submitList(newList)
    }


    override fun registerListener(listener: ViewListener) {
        observable.registerListener(listener)
    }

    override fun unregisterListener(listener: ViewListener) {
        observable.unregisterListener(listener)
    }

    override fun getListeners(): Set<ViewListener> {
        return observable.getListeners()
    }
}