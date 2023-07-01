package com.ichwan.moviecompfest.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.service.GlobalData
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tx_login.setOnClickListener { finish() }

        btn_register.setOnClickListener {
            if (et_name.text.toString().isEmpty() && et_age.text.toString().isEmpty() && et_username.text.toString().isEmpty() && et_password.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "You must have complete all data", Toast.LENGTH_SHORT).show()
            } else {
                val queue = Volley.newRequestQueue(applicationContext)
                val request = object : StringRequest(Method.POST, GlobalData.BASE_URL+"register.php", Response.Listener {
                    _ ->
                    Toast.makeText(applicationContext, "Registered account succesfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }, Response.ErrorListener {
                    error ->
                    Log.d("error register ",error.message.toString())
                })
                {
                    override fun getParams(): MutableMap<String, String> {
                        val param = HashMap<String, String>()
                        param["name"] = et_name.text.toString()
                        param["age"] = et_age.text.toString()
                        param["username"] = et_username.text.toString()
                        param["password"] = et_password.text.toString()
                        return param
                    }
                }
                queue.add(request)
            }
        }
    }
}