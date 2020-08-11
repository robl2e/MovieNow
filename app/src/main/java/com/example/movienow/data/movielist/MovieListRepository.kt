package com.example.movienow.data.movielist

import com.example.movienow.data.common.Failure
import com.example.movienow.data.common.Result
import com.example.movienow.data.common.Success
import com.example.movienow.data.movielist.remote.NowPlayingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by n7 on 8/8/20.
 */

interface MovieListRepository {
    suspend fun listing(page: Int = 1): Result<List<MovieListing>, Exception>
}

class MovieListingRepositoryImpl(private val nowPlayingService: NowPlayingService) :
    MovieListRepository {

    override suspend fun listing(page: Int): Result<List<MovieListing>, Exception> =
        withContext(Dispatchers.IO) {
            val response = nowPlayingService.getNowPlaying(page).execute()

            if (response.isSuccessful) {
                val nowPlayingResponse = response.body()
                nowPlayingResponse?.let {
                    it.results?.let { movies ->
                        return@withContext Success<List<MovieListing>, Exception>(
                            movies
                        )
                    }
                }
            }
            return@withContext Failure<List<MovieListing>, Exception>(RuntimeException("Failed to get listing"))
        }
}