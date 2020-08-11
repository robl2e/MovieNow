package com.example.movienow.ui.main.movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.R

/**
 * Created by n7 on 8/8/20.
 */
class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
    private val summaryTextView: TextView = itemView.findViewById(R.id.text_body)
    private val posterImageView: ImageView = itemView.findViewById(R.id.image_poster)

    fun bindItem(uiModel: MovieListingUIModel) {
        renderTitle(uiModel)
        renderSummary(uiModel)
        renderPosterImage(uiModel)
    }

    private fun renderPosterImage(uiModel: MovieListingUIModel) {
        val finalImageUrl = getFinalImageUrl(uiModel.imageUrl)
        finalImageUrl?.let {
            Glide.with(posterImageView)
                .load(it)
                .into(posterImageView)
        }
    }


    private fun getFinalImageUrl(originalUrl: String?): String? {
        val baseUrl = "https://image.tmdb.org/t/p/w500/"
        return originalUrl?.let {
            return baseUrl + it
        }
    }

    private fun renderSummary(uiModel: MovieListingUIModel) {
        summaryTextView.text = uiModel.summary

    }

    private fun renderTitle(uiModel: MovieListingUIModel) {
        titleTextView.text = uiModel.name
    }

}