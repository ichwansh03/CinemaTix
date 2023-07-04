package com.ichwan.moviecompfest.auth

import android.content.Context
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
import com.ichwan.moviecompfest.service.ShowData
import com.ichwan.moviecompfest.view.MainActivity
import org.json.JSONArray
import org.json.JSONException

class LoginActivity : AppCompatActivity(), ShowData {

    private lateinit var binding: ActivityLoginBinding

    object UserData {
        var username: String = ""
        var name: String = ""
        var age: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            val request = StringRequest(Request.Method.GET, GlobalData.BASE_URL+"login.php?username="+binding.etUsernameLogin.text.toString()+"&password="+binding.etPasswordLogin.text.toString(),
                { response ->
                    if (response.equals("0")) {
                        startActivity(Intent(this, MainActivity::class.java))
                        //belum ke get
                        getData(this, username = binding.etUsernameLogin.text.toString(), url = GlobalData.BASE_URL+"getuser.php?username=")
                    }
                },
                {
                    Toast.makeText(applicationContext,"Username atau password salah", Toast.LENGTH_SHORT).show()
                })
            queue.add(request)
        }
    }

    override fun getData(context: Context, username: String, url: String) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url+username, null) { response ->
            try {
                val jsonArray = JSONArray(response.toString())

                for (i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    UserData.name = jsonObject.getString("name")
                    UserData.age = jsonObject.getInt("age")
                    UserData.username = jsonObject.getString("username")
                }

                startActivity(Intent(this, MainActivity::class.java))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        queue.add(request)
    }
}