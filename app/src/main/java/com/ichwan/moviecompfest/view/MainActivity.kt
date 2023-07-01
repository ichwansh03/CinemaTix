package com.ichwan.moviecompfest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
}