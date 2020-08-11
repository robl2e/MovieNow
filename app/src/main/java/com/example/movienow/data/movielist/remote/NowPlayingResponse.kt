package com.example.movienow.data.movielist.remote

import com.example.movienow.data.movielist.MovieListing

/**
 * Created by n7 on 8/8/20.
 */
data class NowPlayingResponse(val page: Int, val results: List<MovieListing>?)