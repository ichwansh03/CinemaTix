package com.ichwan.moviecompfest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ichwan.moviecompfest.databinding.ActivityDetailOrderBinding

class DetailOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}