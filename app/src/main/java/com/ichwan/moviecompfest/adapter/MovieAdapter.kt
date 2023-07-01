package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.model.MovieItem
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.DetailMovieActivity
import kotlinx.android.synthetic.main.item_movies.view.*

class MovieAdapter(var context: Context, var list: ArrayList<MovieItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyAdapterMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun adapter(context: Context, titles: String, prices: Int, rating: Int, poster: String){
            itemView.title_movie.text = titles
            itemView.price_movie.text = prices.toString()
            itemView.age_rating.text = "$rating+"
            Glide.with(context).load(poster).placeholder(R.drawable.ic_launcher_background).into(itemView.img_movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)

        return MyAdapterMovie(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyAdapterMovie).adapter(
            context,
            list[position].title,
            list[position].price,
            list[position].ageRating,
            list[position].poster
        )
        (holder).itemView.material_cv.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            GlobalData.id = list[position].id
            GlobalData.titleMovie = list[position].title
            GlobalData.descMovie = list[position].description
            GlobalData.priceMovie = list[position].price
            GlobalData.ageRatingMovie = list[position].ageRating
            GlobalData.releaseMovie = list[position].release
            GlobalData.posterMovie = list[position].poster
            val str = Gson().toJson(list[position], MovieItem::class.java)
            intent.putExtra("movie_data", str)
            context.startActivity(intent)
        }
    }
}