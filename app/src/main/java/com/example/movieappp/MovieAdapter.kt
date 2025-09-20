package com.example.movieappp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieDescription: TextView = itemView.findViewById(R.id.movie_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieView = inflater.inflate(R.layout.item_movie, parent, false)
        return ViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        holder.movieTitle.text = movie.title
        holder.movieDescription.text = movie.overview

        if (!movie.posterPath.isNullOrEmpty()) {
            val imageUrl = imageBaseUrl + movie.posterPath
            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Optional: a placeholder image
                .error(R.drawable.ic_launcher_foreground) // Optional: an error image
                .into(holder.moviePoster)
        } else {
            // Optionally, set a default image if posterPath is null or empty
            holder.moviePoster.setImageResource(R.drawable.ic_launcher_background) // Example placeholder
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}