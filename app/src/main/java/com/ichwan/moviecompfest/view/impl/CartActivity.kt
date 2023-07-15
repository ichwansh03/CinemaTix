package com.ichwan.moviecompfest.view.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ActivityCartBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.InsertDataOrder
import com.ichwan.moviecompfest.view.PaymentActivity
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class CartActivity : AppCompatActivity(), InsertDataOrder {

    private lateinit var binding: ActivityCartBinding
    private lateinit var addFieldSeat: LinearLayout
    private lateinit var nameOrder: TextView
    private lateinit var titleMovieOrder: TextView
    private lateinit var priceOrder: TextView
    private lateinit var amountOrder: TextView
    private lateinit var totalPay: TextView

    private var quantities: Int = 0
    private var total: Int = 0

    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        showDataOrder()

        binding.btnAdd.setOnClickListener {
            quantities++
            amountOrder.text = quantities.toString()

            addInputTicketView()

            if (quantities >= 6){
                binding.btnAdd.isEnabled = false
                Toast.makeText(this, "You can't buy ticket more than 6", Toast.LENGTH_SHORT).show()
            }

            total = GlobalData.priceMovie * quantities
            updateTotalPay()
        }

        binding.btnMin.setOnClickListener {

            removeInputTicketView()

            if (quantities > 0){
                quantities--
                amountOrder.text = quantities.toString()
            }
            total = GlobalData.priceMovie * quantities
            updateTotalPay()
        }

        binding.btnOrder.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }

    private fun init(){
        val name = intent.getStringExtra("name")
        nameOrder = binding.nameOrder
        nameOrder.text = name.toString()

        titleMovieOrder = binding.titleMovieOrder
        priceOrder = binding.priceMovieOrder
        amountOrder = binding.txCount
        totalPay = binding.totalPay

        addFieldSeat = binding.layoutList
    }

    fun insertTicketOrder(context: Context, url: String) {
        val inputTicketView = LayoutInflater.from(context).inflate(R.layout.item_add_seats, null, false)
        val seats = inputTicketView.findViewById<EditText>(R.id.et_seats_number)

        for (i in 0 until addFieldSeat.childCount) {
            if (seats.text.toString().isNotEmpty()){

                val queue = Volley.newRequestQueue(context)
                val request = object : StringRequest(Method.POST, url, Response.Listener {
                    Toast.makeText(this, "Ticket successfully ordered!", Toast.LENGTH_SHORT).show()
                },
                    Response.ErrorListener { error ->
                        Log.d("error insert ticket ", error.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String> {

                        val params = HashMap<String, String>()
                        params["name"] = nameOrder.toString()
                        params["title"] = titleMovieOrder.toString()
                        params["total"] = GlobalData.priceMovie.toString()
                        params["seat"] = seats.toString()
                        return params
                    }
                }
                queue.add(request)

            } else {
                Toast.makeText(this, "Fill number of seat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeInputTicketView() {
        val inputTicketView = LayoutInflater.from(this).inflate(R.layout.item_add_seats, null, false)

        addFieldSeat.removeView(inputTicketView)
    }

    private fun addInputTicketView() {
        val inputTicketView = LayoutInflater.from(this).inflate(R.layout.item_add_seats, null, false)

        addFieldSeat.addView(inputTicketView)
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPay() {
        totalPay.text = "Pay (${formatRp.format(total)})"
        GlobalData.totalPayment = total
    }

    private fun showDataOrder() {
        formatRp.minimumFractionDigits = 0
        titleMovieOrder.text = GlobalData.titleMovie
        priceOrder.text = formatRp.format(GlobalData.priceMovie)
    }

    override fun insert(context: Context, url: String) {
        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Method.POST, url, Response.Listener {

        },
            Response.ErrorListener { error ->
                Log.d("error insert cart ", error.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = nameOrder.toString()
                params["title"] = titleMovieOrder.toString()
                params["price"] = GlobalData.priceMovie.toString()
                params["quantity"] = quantities.toString()
                params["total"] = total.toString()
                return params
            }
        }
        queue.add(request)
    }
}