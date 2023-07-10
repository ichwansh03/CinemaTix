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
import com.ichwan.moviecompfest.service.GetDataUser
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.impl.CartActivity
import java.text.NumberFormat
import java.util.*

class DetailMovieActivity : AppCompatActivity(), GetDataUser {

    private lateinit var item: MovieItem
    private lateinit var binding: ActivityDetailMovieBinding
    private val getDataUser: GetDataUser = object : GetDataUser{}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activity = MainActivity()
        activity.getData()

        val age = MainActivity.User.age

        showMovieData()

        binding.btnOrder.setOnClickListener {

            if (age.toString().toInt() < item.ageRating) {
                Toast.makeText(this, "Your old isn't enough!", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CartActivity::class.java))
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