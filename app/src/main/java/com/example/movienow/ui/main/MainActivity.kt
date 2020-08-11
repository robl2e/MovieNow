package com.example.movienow.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.movienow.R
import com.example.movienow.data.movielist.MovieListingRepositoryImpl
import com.example.movienow.data.remote.MovieDBClient
import com.example.movienow.ui.main.movielist.MovieListPresenter
import com.example.movienow.ui.main.movielist.MovieListProxyView
import com.example.movienow.ui.main.movielist.MovieListViewContract
import com.example.movienow.ui.main.movielist.MovieListingUIModel

class MainActivity : AppCompatActivity(), MovieListViewContract.ViewListener {

    private lateinit var proxyView: MovieListViewContract.View
    private val presenter: MovieListPresenter by viewModels {
        //TODO: DI
        MovieListPresenter.Factory(
            MovieListingRepositoryImpl(MovieDBClient.nowPlayingService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val componentMovieListView = findViewById<View>(R.id.component_view_movie_list)
        proxyView = createProxyView(componentMovieListView
            , presenter.items // address orientation changes
            , presenter.currentPage) // orientation change
        presenter.attachView(proxyView)

        presenter.process(MovieListViewContract.ViewEvent.InitialLoad)
    }

    override fun onStart() {
        super.onStart()
        proxyView.registerListener(this)
    }

    override fun onStop() {
        proxyView.unregisterListener(this)
        super.onStop()
    }

    private fun createProxyView(
        rootView: View,
        items: MutableList<MovieListingUIModel>,
        currentPage: Int
    ): MovieListProxyView {
        return MovieListProxyView(rootView, items, currentPage)
    }

    override fun onEvent(event: MovieListViewContract.ViewEvent) {
        presenter.process(event)
    }
}