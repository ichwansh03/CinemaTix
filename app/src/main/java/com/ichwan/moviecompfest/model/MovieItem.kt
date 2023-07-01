package com.ichwan.moviecompfest.model

data class MovieItem (
    var id: Int,
    var title: String,
    var description: String,
    var release: String,
    var poster: String,
    var ageRating: Int,
    var price: Int
)