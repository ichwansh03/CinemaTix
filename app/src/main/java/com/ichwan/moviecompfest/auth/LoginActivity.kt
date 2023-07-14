package com.ichwan.moviecompfest.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ActivityLoginBinding
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.LoginRepository
import com.ichwan.moviecompfest.view.MainActivity

class LoginActivity : AppCompatActivity(), LoginRepository {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            login(
                username = binding.etUsernameLogin.text.toString(),
                password = binding.etPasswordLogin.text.toString()
            )
        }

        binding.txRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun login(username: String, password: String) {
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val request = StringRequest(Request.Method.GET, GlobalData.BASE_URL+"auth/login.php?username="+username+"&password="+password,
            { response ->
                if (response.equals("0")) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                }
            },
            {
                Toast.makeText(applicationContext,"Username or password is wrong!", Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

}