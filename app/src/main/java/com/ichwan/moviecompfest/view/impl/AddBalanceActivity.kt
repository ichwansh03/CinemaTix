package com.ichwan.moviecompfest.view.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ActivityAddBalanceBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.InsertData
import com.ichwan.moviecompfest.view.MainActivity
import com.ichwan.moviecompfest.view.PaymentActivity

class AddBalanceActivity : AppCompatActivity(), InsertData {

    private lateinit var binding: ActivityAddBalanceBinding
    private var username: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBalanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activity = MainActivity()
        activity.getData()

        username = MainActivity.User.username

        binding.wd50.setOnClickListener { binding.etBalance.setText("50000") }
        binding.wd100.setOnClickListener { binding.etBalance.setText("100000") }
        binding.wd250.setOnClickListener { binding.etBalance.setText("250000") }
        binding.wd500.setOnClickListener { binding.etBalance.setText("500000") }

        insert(this, GlobalData.BASE_URL +"balance/withdraw.php")

    }

    override fun insert(context: Context, url: String) {
        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Method.POST, url, Response.Listener {
            Toast.makeText(this, "Balance succesfully added", Toast.LENGTH_SHORT).show()
            finish()
        }, Response.ErrorListener {
                    error ->
                Log.d("error manage balance ",error.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username.toString()
                params["balance"] = binding.etBalance.toString()
                return params
            }
        }
        queue.add(request)
    }

}