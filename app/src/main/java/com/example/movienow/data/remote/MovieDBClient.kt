package com.example.movienow.data.remote

import com.example.movienow.data.movielist.remote.NowPlayingService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by n7 on 8/8/20.
 */
class MovieDBClient {
    companion object {
        // Trailing slash is needed
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        private var httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        private var okHttpClient: OkHttpClient
        private var retrofit: Retrofit

        init {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(AuthenticationInterceptor())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val nowPlayingService: NowPlayingService by lazy { retrofit.create(
            NowPlayingService::class.java) }
    }
}