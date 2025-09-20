package com.example.movieappp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
    private val nowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=$apiKey"

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        val client = AsyncHttpClient()
        client.get(nowPlayingUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.d("MovieViewModel", "Successfully fetched movies: ${json.jsonObject.toString()}")
                val gson = Gson()
                try {
                    val movieApiResponse = gson.fromJson(json.jsonObject.toString(), MovieApiResponse::class.java)
                    _movies.postValue(movieApiResponse.results)
                    Log.d("MovieViewModel", "Movies loaded: ${movieApiResponse.results.size}")
                } catch (e: Exception) {
                    Log.e("MovieViewModel", "Error parsing JSON", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e("MovieViewModel", "Failed to fetch movies: $statusCode, $response", throwable)
                // Optionally, post an error state to LiveData to be observed by the UI
            }
        })
    }
}