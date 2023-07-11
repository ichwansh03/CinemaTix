package com.ichwan.moviecompfest.view.impl

import android.annotation.SuppressLint
import android.content.Context
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
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class AddBalanceActivity : AppCompatActivity(), InsertData {

    private lateinit var binding: ActivityAddBalanceBinding
    private var username: String? = null
    private var balance: Int? = 0
    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBalanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra("username")
        balance = intent.getIntExtra("balance",0)

        binding.currentBalance.text = formatRp.format(balance)

        binding.wd50.setOnClickListener { binding.etBalance.setText("50000") }
        binding.wd100.setOnClickListener { binding.etBalance.setText("100000") }
        binding.wd250.setOnClickListener { binding.etBalance.setText("250000") }
        binding.wd500.setOnClickListener { binding.etBalance.setText("500000") }

        binding.btnWithdraw.setOnClickListener {
            insert(this, GlobalData.BASE_URL +"balance/withdraw.php")
        }
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
                params["balance"] = binding.etBalance.text.toString()
                return params
            }
        }
        queue.add(request)
    }

}