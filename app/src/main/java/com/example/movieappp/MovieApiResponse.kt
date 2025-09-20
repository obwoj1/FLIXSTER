package com.example.movieappp

import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    @SerializedName("results") val results: List<Movie>
)