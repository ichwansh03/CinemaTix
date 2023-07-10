package com.ichwan.moviecompfest.view.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.databinding.ActivityCartBinding
import com.ichwan.moviecompfest.model.SeatItem
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.InsertData
import com.ichwan.moviecompfest.view.PaymentActivity
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CartActivity : AppCompatActivity(), InsertData {

    private lateinit var binding: ActivityCartBinding
    private var name: String? = null
    private var quantities: Int = 0
    private var total: Int = 0

    private var seatList: ArrayList<SeatItem>? = null
    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name")

        showDataOrder()

        binding.btnAdd.setOnClickListener {
            quantities++
            binding.txCount.text = quantities.toString()

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
                binding.txCount.text = quantities.toString()
            }
            total = GlobalData.priceMovie * quantities
            updateTotalPay()
        }

        binding.btnOrder.setOnClickListener {
            //insert data order
            insert(this, GlobalData.BASE_URL +"order/addorder.php")
            //insert number seat order
            if (checkSeatsInput()){
                insertSeatOrder()
            }
        }
    }

    private fun checkSeatsInput(): Boolean {
        seatList?.clear()
        var result = true

        for (i in 0 until binding.layoutList.childCount) {
            val seatView = binding.layoutList.getChildAt(i)
            val etSeat = seatView.findViewById<EditText>(R.id.et_seats_number)

            val item = SeatItem(0, null, null, 0)

            if (etSeat.text.toString().isNotEmpty()){
                item.name = etSeat.text.toString()
            } else {
                result = false
                break
            }

            seatList?.add(item)
        }

        if (seatList?.size == 0){
            return false
            Toast.makeText(this, "Add number of seats first!", Toast.LENGTH_SHORT).show()
        } else if (!result){
            Toast.makeText(this, "Enter all numbers seat!", Toast.LENGTH_SHORT).show()
        }

        return result
    }

    private fun insertSeatOrder() {
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, GlobalData.BASE_URL+"ticket/addticket.php", Response.Listener {
            startActivity(Intent(this, PaymentActivity::class.java))
        },
            Response.ErrorListener { error ->
                Log.d("error insert cart ", error.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name.toString()
                params["title"] = binding.titleMovieOrder.text.toString()
                params["seat"] = seatList!!.joinToString { "," }
                return params
            }
        }
        queue.add(request)
    }

    private fun removeInputTicketView() {
        val inputTicketView = LayoutInflater.from(this).inflate(R.layout.item_add_seats, null, false)

        binding.layoutList.removeView(inputTicketView)
    }

    private fun addInputTicketView() {

        val inputTicketView = LayoutInflater.from(this).inflate(R.layout.item_add_seats, null, false)

        binding.layoutList.addView(inputTicketView)
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPay() {
        binding.totalPay.text = "Pay (${formatRp.format(total)})"
        GlobalData.totalPayment = total
    }

    private fun showDataOrder() {
        formatRp.minimumFractionDigits = 0
        binding.titleMovieOrder.text = GlobalData.titleMovie
        binding.priceMovieOrder.text = formatRp.format(GlobalData.priceMovie)
    }

    override fun insert(context: Context, url: String) {
        formatRp.minimumFractionDigits = 0

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Method.POST, url, Response.Listener {
            startActivity(Intent(this, PaymentActivity::class.java))
        },
            Response.ErrorListener { error ->
                Log.d("error insert cart ", error.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name.toString()
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