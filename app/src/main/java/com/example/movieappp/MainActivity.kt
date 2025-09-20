package com.example.movieappp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels // Import for by viewModels()
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer // Import for Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var moviesList: MutableList<Movie> = mutableListOf()

    // Get a reference to the MovieViewModel
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        moviesRecyclerView = findViewById(R.id.movie_list)
        movieAdapter = MovieAdapter(this, moviesList) // Initialize with an empty list first
        moviesRecyclerView.adapter = movieAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observe the LiveData from the ViewModel
        movieViewModel.movies.observe(this, Observer { fetchedMovies ->
            Log.d("MainActivity", "Movies LiveData updated, count: ${fetchedMovies.size}")
            moviesList.clear()
            moviesList.addAll(fetchedMovies)
            movieAdapter.notifyDataSetChanged()
        })
    }
}