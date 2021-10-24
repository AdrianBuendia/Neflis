package com.neflis.neflis.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neflis.neflis.core.Constants
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMovie
import com.neflis.neflis.databinding.ItemMostPopularMovieBinding
import android.net.Uri

class MostPopularMoviesListAdapter :
    ListAdapter<MostPopularMovie, MostPopularMoviesListAdapter.MostPopularMoviesViewHolder>(
        DiffCallback
    ) {

    lateinit var onItemClickListener: (mostPopularMovie: MostPopularMovie) -> Unit

    inner class MostPopularMoviesViewHolder(
        private val binding: ItemMostPopularMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mostPopularMovie: MostPopularMovie) {
            binding.txtMovieTitle.text = mostPopularMovie.title

            val uri: Uri =
                Uri.parse("${Constants.BASE_URL_IMAGES}${Constants.W500_RESOLUTION}${mostPopularMovie.posterPath}")
            val draweeView = binding.imgMovie
            draweeView.setImageURI(uri)

            binding.cardParentContainer.setOnClickListener {
                onItemClickListener(mostPopularMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMoviesViewHolder {
        val binding = ItemMostPopularMovieBinding
            .inflate(
                (LayoutInflater.from(parent.context))
            )
        return MostPopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostPopularMoviesViewHolder, position: Int) {
        val mostPopularMovie = getItem(position)
        holder.bind(mostPopularMovie)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<MostPopularMovie>() {
        override fun areItemsTheSame(
            oldItem: MostPopularMovie,
            newItem: MostPopularMovie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MostPopularMovie,
            newItem: MostPopularMovie
        ): Boolean {
            return oldItem == newItem
        }
    }
}