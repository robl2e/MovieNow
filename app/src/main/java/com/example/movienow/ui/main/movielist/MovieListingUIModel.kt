package com.example.movienow.ui.main.movielist

import com.example.movienow.data.movielist.MovieListing

/**
 * Created by n7 on 8/8/20.
 */
data class MovieListingUIModel(
    val id: String,
    val name: String?,
    val summary: String?,
    val imageUrl: String?
)

fun MovieListing.to(): MovieListingUIModel {
    return MovieListingUIModel(
        id = id.toString(),
        name = title,
        summary = overview,
        imageUrl = posterPath
    )
}