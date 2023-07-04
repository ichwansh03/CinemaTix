package com.ichwan.moviecompfest.service

import android.content.Context

interface ShowData {

    fun getData(context: Context, username: String, url: String)
}