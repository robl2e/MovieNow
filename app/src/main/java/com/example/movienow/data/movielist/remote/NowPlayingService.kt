package com.example.movienow.data.movielist.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by n7 on 8/8/20.
 */

interface NowPlayingService {
    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<NowPlayingResponse>
}