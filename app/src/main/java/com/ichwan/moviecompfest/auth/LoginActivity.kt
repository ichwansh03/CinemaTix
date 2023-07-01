package com.ichwan.moviecompfest.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.R
import com.ichwan.moviecompfest.auth.LoginActivity.UserData.name
import com.ichwan.moviecompfest.auth.LoginActivity.UserData.username
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONException

class LoginActivity : AppCompatActivity() {

    val queue: RequestQueue = Volley.newRequestQueue(this)

    object UserData {
        var username: String = ""
        var name: String = ""
        var age: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tx_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            val request = StringRequest(Request.Method.GET, GlobalData.BASE_URL+"login.php?username="+et_username_login.text.toString()+"&password="+et_password_login.text.toString(),
                { response ->
                    if (response.equals("0")) {
                        getDataUser(username = et_username_login.text.toString())
                    }
                },
                { error ->
                    Toast.makeText(applicationContext,"Username atau password salah", Toast.LENGTH_SHORT).show()
                })
            queue.add(request)
        }
    }

    private fun getDataUser(username: String) {
        val request = StringRequest(Request.Method.GET, GlobalData.BASE_URL+"getuser.php?username=$username", null) { response ->
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