package com.ichwan.moviecompfest.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ActivityForgotPasswordBinding
import com.ichwan.moviecompfest.service.GlobalData
import org.json.JSONException
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            if (binding.etPassword.text.toString() == binding.etConfirmPassword.text.toString()){
                updatePassword()
            }
        }
    }

    private fun updatePassword() {
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, GlobalData.BASE_URL+"auth/update_password.php", Response.Listener { 
            response ->

            try {
                val json = JSONObject(response)
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()

                if (json.getString("status") == "OK") {
                    finish()
                }
            } catch (e: JSONException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        }, {
            error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = binding.etUsername.text.toString()
                params["password"] = binding.etPassword.text.toString()
                return params

            }
        }

        queue.add(request)
    }
}