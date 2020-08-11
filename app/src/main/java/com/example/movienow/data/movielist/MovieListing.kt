package com.example.movienow.data.movielist

import com.google.gson.annotations.SerializedName

/**
 * Created by n7 on 8/8/20.
 */
data class MovieListing(
    @SerializedName("poster_path") val posterPath: String?
    , val id: Int
    , val title: String?
    , val overview: String?
)