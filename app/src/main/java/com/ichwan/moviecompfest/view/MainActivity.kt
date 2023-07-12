package com.ichwan.moviecompfest.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ActivityMainBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.impl.MovieFragment
import com.ichwan.moviecompfest.view.impl.OrderFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var usernames: String? = null
    object User {
        var username: String? = null
        var name: String? = null
        var age: Int? = 0
    }

    interface UserCallback {
        fun onUserReceived(name: String?, age: Int?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usernames = intent.getStringExtra("username")

        getDataUser(this, usernames.toString(), object : UserCallback {
            override fun onUserReceived(name: String?, age: Int?) {
                User.username = usernames
                User.name = name
                User.age = age
            }
        })

        if (savedInstanceState == null){
            binding.chipbar.setItemSelected(R.id.movies, true)
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieFragment()).commit()
        }

        itemSelectedFragment()

    }

    private fun itemSelectedFragment() {
        binding.chipbar.setOnItemSelectedListener { id ->
            var fragment: Fragment? = null
            when(id){
                R.id.movies -> fragment = MovieFragment()
                R.id.ticket -> fragment = OrderFragment()
            }

            if (fragment != null){
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            }
        }
    }

    private fun getDataUser(context: Context, user: String, callback: UserCallback) {
        val queue = Volley.newRequestQueue(context)
        val request = JsonArrayRequest(Request.Method.GET, GlobalData.BASE_URL+"getuser.php?username=$user", null,
            { response ->
                for (i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val age = jsonObject.getInt("age")

                    callback.onUserReceived(name, age)
                }
            }, {
                    error ->
                Log.d("error get data user ",error.toString())
            })
        queue.add(request)
    }
}