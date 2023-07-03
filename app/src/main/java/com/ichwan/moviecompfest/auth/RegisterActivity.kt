package com.ichwan.moviecompfest.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ActivityRegisterBinding
import com.ichwan.moviecompfest.service.GlobalData

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txLogin.setOnClickListener { finish() }

        binding.btnRegister.setOnClickListener {
            if (binding.etName.text.toString().isEmpty() && binding.etAge.text.toString().isEmpty() && binding.etUsername.text.toString().isEmpty() && binding.etPassword.text.toString().isEmpty()) {
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
                        param["name"] = binding.etName.text.toString()
                        param["age"] = binding.etAge.text.toString()
                        param["username"] = binding.etUsername.text.toString()
                        param["password"] = binding.etPassword.text.toString()
                        return param
                    }
                }
                queue.add(request)
            }
        }
    }
}