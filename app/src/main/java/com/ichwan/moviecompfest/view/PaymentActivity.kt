package com.ichwan.moviecompfest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ichwan.moviecompfest.databinding.ActivityPaymentBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.impl.AddBalanceActivity
import java.text.NumberFormat
import java.util.*

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.totalPayment.text = formatRp.format(GlobalData.totalPayment)
        binding.currentBalance.text = formatRp.format(AddBalanceActivity.Withdraw.balances)

        binding.btnPayCash.setOnClickListener {
            if (binding.inputAmount.text.toString().toInt() < GlobalData.totalPayment){
                Toast.makeText(this, "Your cash is less than total payment", Toast.LENGTH_SHORT).show()
            } else {
                DialogPaymentSuccessFragment().show(supportFragmentManager, "DialogPaymentSuccess")
            }
        }
    }
}