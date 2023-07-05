package com.ichwan.moviecompfest.view.impl

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.adapter.OrderAdapter
import com.ichwan.moviecompfest.auth.LoginActivity
import com.ichwan.moviecompfest.databinding.FragmentOrderBinding
import com.ichwan.moviecompfest.model.OrderItem
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.service.ShowData

class OrderFragment : Fragment(), ShowData {

    private var list = ArrayList<OrderItem>()
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.searchBarOrder.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchOrder(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            handler.post {
                getData(requireContext(), LoginActivity.UserData.username, GlobalData.BASE_URL+"order/getorder.php?")
                binding.pbOrder.visibility = View.GONE
            }
        }, 5000)

        return view
    }

    private fun searchOrder(words: String) {
        val filterList = ArrayList<OrderItem>()

        for (item in list) {
            if (item.title.contains(words, true) || item.name.contains(words, true)) {
                filterList.add(item)
            }
        }
        val adapter = OrderAdapter(requireContext(), list)
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
        binding.rvOrder.adapter = adapter

    }

    override fun getData(context: Context, username: String, url: String) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonArrayRequest(Request.Method.GET, url + username, null, {
            response ->

            if (response.length() == 0){
                Toast.makeText(context,"No internet connection!", Toast.LENGTH_SHORT).show()
            } else {
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val title = jsonObject.getString("title")
                    val quantity = jsonObject.getInt("quantity")

                    list.add(OrderItem(id, name, title, quantity))

                    val adapter = OrderAdapter(context, list)
                    binding.rvOrder.layoutManager = LinearLayoutManager(context)
                    binding.rvOrder.adapter = adapter
                }
            }

        }, { error ->
            Log.d("error get data order ",error.message.toString())
        })

        queue.add(request)
    }

}