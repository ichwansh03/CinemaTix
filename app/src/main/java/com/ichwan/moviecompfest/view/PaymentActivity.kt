package com.ichwan.moviecompfest.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ActivityPaymentBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.InsertData
import com.ichwan.moviecompfest.view.impl.MovieFragment
import java.text.NumberFormat
import java.util.*

class PaymentActivity : AppCompatActivity(), InsertData {

    private lateinit var binding: ActivityPaymentBinding
    private var username: String? = null
    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))
    private var prevBalance: Int? = 0
    private var currBalance: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prevBalance = MovieFragment.Balance.amount

        binding.totalPayment.text = formatRp.format(GlobalData.totalPayment)
        binding.currentBalance.text = formatRp.format(MovieFragment.Balance.amount)

        binding.btnPayCash.setOnClickListener {
            checkPayment()
        }
    }

    private fun checkPayment() {
        if (binding.inputAmount.text.toString().toInt() < GlobalData.totalPayment){
            Toast.makeText(this, "Your cash is less than total payment", Toast.LENGTH_SHORT).show()
        } else {
            currBalance = prevBalance.toString().toInt() - GlobalData.totalPayment
            insert(this, GlobalData.BASE_URL+"balance/withdraw.php")
            DialogPaymentSuccessFragment().show(supportFragmentManager, "DialogPaymentSuccess")
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
                params["balance"] = currBalance.toString()
                return params
            }
        }
        queue.add(request)
    }
}