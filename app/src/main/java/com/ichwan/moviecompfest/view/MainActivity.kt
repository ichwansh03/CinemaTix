package com.ichwan.moviecompfest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ActivityMainBinding
import com.ichwan.moviecompfest.service.GetDataUser
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.impl.CartActivity
import com.ichwan.moviecompfest.view.impl.MovieFragment
import com.ichwan.moviecompfest.view.impl.OrderFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val getDataUser: GetDataUser = object : GetDataUser {}

    private var usernames: String? = null
    private var names: String? = null
    private var ages: Int? = 0

    object User {
        var username: String? = null
        var name: String? = null
        var age: Int? = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getData()

        if (savedInstanceState == null){
            binding.chipbar.setItemSelected(R.id.movies, true)
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieFragment()).commit()
        }

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

    fun getData(){
        usernames = intent.getStringExtra("username")
        getDataUser.getData(this, GlobalData.BASE_URL+"getuser.php?username=$usernames") { username, name, age ->
            User.username = username
            User.name = name
            User.age = age
        }
    }
}