package com.example.movienow.ui.main.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movienow.R

/**
 * Created by n7 on 8/8/20.
 */
class MovieListAdapter :
    ListAdapter<MovieListingUIModel, MovieListViewHolder>(
        MovieDiffItemCallaback()
    ) {


    class MovieDiffItemCallaback : DiffUtil.ItemCallback<MovieListingUIModel>() {
        override fun areItemsTheSame(
            oldItem: MovieListingUIModel,
            newItem: MovieListingUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListingUIModel,
            newItem: MovieListingUIModel
        ): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.summary == newItem.summary
                    && oldItem.imageUrl == newItem.imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_movie_listing, parent, false)
        return MovieListViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }
}


