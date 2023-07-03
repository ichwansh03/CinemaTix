package com.ichwan.moviecompfest.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.auth.LoginActivity
import com.ichwan.moviecompfest.databinding.ActivityCartBinding
import com.ichwan.moviecompfest.service.GlobalData
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private var quantities: Int = 0
    private var total: Int = 0

    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDataOrder()

        binding.btnAdd.setOnClickListener {
            quantities++
            binding.txCount.text = quantities.toString()

            if (quantities >= 6){
                binding.btnAdd.isEnabled = false
                Toast.makeText(this, "You can't buy ticket more than 6", Toast.LENGTH_SHORT).show()
            }

            total = GlobalData.priceMovie * quantities
            updateTotalPay()
        }

        binding.btnMin.setOnClickListener {
            if (quantities > 0){
                quantities--
                binding.txCount.text = quantities.toString()
            }
            total = GlobalData.priceMovie * quantities
            updateTotalPay()
        }

        binding.btnOrder.setOnClickListener {
            insertDataOrder()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPay() {
        binding.totalPay.text = "Pay (${formatRp.format(total)})"
        GlobalData.totalPayment = total
    }

    private fun showDataOrder() {
        formatRp.minimumFractionDigits = 0
        binding.nameOrder.text = LoginActivity.UserData.name
        binding.titleMovieOrder.text = GlobalData.titleMovie
        binding.priceMovieOrder.text = formatRp.format(GlobalData.priceMovie)
    }

    private fun insertDataOrder() {
        formatRp.minimumFractionDigits = 0

        val url: String = GlobalData.BASE_URL +"order/addorder.php"
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url, Response.Listener { _ ->
            startActivity(Intent(this, PaymentActivity::class.java))
        },
        Response.ErrorListener { error ->
            Log.d("error insert cart ", error.message.toString())
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = binding.nameOrder.text.toString()
                params["title"] = binding.titleMovieOrder.text.toString()
                params["price"] = GlobalData.priceMovie.toString()
                params["quantity"] = quantities.toString()
                params["total"] = total.toString()
                return params
            }
        }
        queue.add(request)
    }
}