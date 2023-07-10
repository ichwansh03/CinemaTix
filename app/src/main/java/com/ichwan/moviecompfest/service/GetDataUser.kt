package com.ichwan.moviecompfest.service

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import javax.security.auth.callback.Callback

interface GetDataUser {

    fun getData(context: Context, url: String, callback: (username: String, name: String, age: Int) -> Unit){
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, null) { response ->
            try {
                val jsonArray = JSONArray(response.toString())
                for (i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val username = jsonObject.getString("username")
                    val name = jsonObject.getString("name")
                    val age = jsonObject.getInt("age")
                    callback(username, name, age)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        queue.add(request)
    }
}