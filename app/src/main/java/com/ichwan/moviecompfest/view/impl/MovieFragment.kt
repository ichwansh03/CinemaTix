package com.ichwan.moviecompfest.view.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.adapter.MovieAdapter
import com.ichwan.moviecompfest.databinding.FragmentMovieBinding
import com.ichwan.moviecompfest.model.MovieItem
import com.ichwan.moviecompfest.service.ShowDataMovie

class MovieFragment : Fragment(), ShowDataMovie {

    private var list = ArrayList<MovieItem>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        val username = requireActivity().intent.getStringExtra("username")
        binding.greeting.text = "Welcome, $username"

        binding.topupBalance.setOnClickListener {
            shareUsername(requireContext(), AddBalanceActivity::class.java, username)
        }

        searchBar()

        handlerData()

        return view
    }

    private fun handlerData() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            handler.post {
                getData(requireContext(), "null","https://seleksi-sea-2023.vercel.app/api/movies")
                binding.pbMovie.visibility = View.GONE
            }
        },5000)
    }

    private fun searchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchItem(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun searchItem(key: String) {
        val filterList = ArrayList<MovieItem>()

        for (item in list){
            if (item.title.contains(key, true)){
                filterList.add(item)
            }
        }

        setAdapter(requireContext(), list)
    }

    override fun getData(context: Context, username: String, url: String) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->

            if (response.length() == 0){
                Toast.makeText(context,"No internet connection!",Toast.LENGTH_SHORT).show()
            } else {
                for (i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val title = jsonObject.getString("title")
                    val price = jsonObject.getInt("ticket_price")
                    val age = jsonObject.getInt("age_rating")
                    val poster = jsonObject.getString("poster_url")
                    val description = jsonObject.getString("description")
                    val release = jsonObject.getString("release_date")

                    list.add(MovieItem(id, title, description, release, poster, age, price))

                    setAdapter(context, list)
                }
            }

        }, { error ->
            Log.d("error data movie ",error.toString())
        })
        queue.add(request)
    }

    private fun shareUsername(
        context: Context,
        classes: Class<*>,
        username: String?
    ){
        val intent = Intent(context, classes)
        intent.putExtra("username", username)
        startActivity(intent)
    }

    private fun setAdapter(context: Context, list: ArrayList<MovieItem>){
        val adapter = MovieAdapter(context, list)
        binding.rvMovie.layoutManager = GridLayoutManager(context,2)
        binding.rvMovie.adapter = adapter
    }

}