package com.ichwan.moviecompfest.service

interface LoginRepository {

    fun login(username: String, password: String)
}