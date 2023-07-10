package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ItemMoviesBinding
import com.ichwan.moviecompfest.model.MovieItem
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.DetailMovieActivity
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter(var context: Context, var list: ArrayList<MovieItem>) : RecyclerView.Adapter<MovieAdapter.MyAdapterMovie>() {

    class MyAdapterMovie(val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

        @SuppressLint("SetTextI18n")
        fun adapter(context: Context, titles: String, prices: Int, rating: Int, poster: String){
            binding.titleMovie.text = titles
            binding.priceMovie.text = formatRp.format(prices)
            binding.ageRating.text = "$rating+"
            Glide.with(context).load(poster).placeholder(R.drawable.ic_launcher_background).into(binding.imgMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterMovie {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapterMovie(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyAdapterMovie, position: Int) {
        holder.adapter(
            context,
            list[position].title,
            list[position].price,
            list[position].ageRating,
            list[position].poster
        )
        holder.binding.materialCv.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            GlobalData.id = list[holder.adapterPosition].id
            GlobalData.titleMovie = list[holder.adapterPosition].title
            GlobalData.descMovie = list[holder.adapterPosition].description
            GlobalData.priceMovie = list[holder.adapterPosition].price
            GlobalData.ageRatingMovie = list[holder.adapterPosition].ageRating
            GlobalData.releaseMovie = list[holder.adapterPosition].release
            GlobalData.posterMovie = list[holder.adapterPosition].poster
            val str = Gson().toJson(list[holder.adapterPosition], MovieItem::class.java)
            intent.putExtra("movie_data", str)
            context.startActivity(intent)
        }
    }
}