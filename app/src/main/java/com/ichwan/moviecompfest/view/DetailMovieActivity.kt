package com.ichwan.moviecompfest.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ichwan.moviecompfest.databinding.ActivityDetailMovieBinding
import com.ichwan.moviecompfest.model.MovieItem
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.impl.CartActivity
import java.text.NumberFormat
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var item: MovieItem
    private lateinit var binding: ActivityDetailMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showMovieData()

        val name = MainActivity.User.name
        val username = MainActivity.User.username
        val age = MainActivity.User.age

        binding.btnOrder.setOnClickListener {

            if (age.toString().toInt() < item.ageRating) {
                Toast.makeText(this, "Your old isn't enough! $age", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CartActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showMovieData() {
        val data = intent.getStringExtra("movie_data")
        item = Gson().fromJson(data, MovieItem::class.java)

        val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        formatRp.minimumFractionDigits = 0

        Glide.with(this).load(item.poster).into(binding.posterMovieDetail)
        binding.titleMovieDetail.text = item.title
        GlobalData.titleMovie = item.title
        binding.descMovieDetail.text = GlobalData.descMovie
        binding.releaseMovieDetail.text = "Release : ${GlobalData.releaseMovie}"
        binding.ageMovieDetail.text = "For ${item.ageRating} years above"
        binding.priceMovieDetail.text = formatRp.format(item.price)
        GlobalData.priceMovie = item.price
    }
}