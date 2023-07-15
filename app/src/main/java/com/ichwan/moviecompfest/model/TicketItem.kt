package com.ichwan.moviecompfest.model

data class TicketItem(
    var id: Int,
    var name: String,
    var title: String,
    var seats: Int,
    var total: Int
)