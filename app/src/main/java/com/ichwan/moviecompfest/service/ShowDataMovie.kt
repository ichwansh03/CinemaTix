package com.ichwan.moviecompfest.service

import android.content.Context

interface ShowDataMovie {

    fun getData(context: Context, username: String, url: String)
}