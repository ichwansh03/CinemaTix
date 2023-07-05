package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ichwan.moviecompfest.databinding.ItemOrdersBinding
import com.ichwan.moviecompfest.model.OrderItem
import com.ichwan.moviecompfest.view.DetailOrderActivity

class OrderAdapter(var context: Context, var list: ArrayList<OrderItem>) : RecyclerView.Adapter<OrderAdapter.MyAdapterOrder>() {

    class MyAdapterOrder(val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun adapter(context: Context, names: String, titles: String, quantity: Int){
            binding.nameOrders.text = names
            binding.titleOrders.text = titles
            binding.quantityOrders.text = "$quantity tickets"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterOrder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapterOrder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyAdapterOrder, position: Int) {
        holder.adapter(context, list[position].name, list[position].title, list[position].quantity)
        holder.binding.cvFrameOrder.setOnClickListener {
            val intent = Intent(context, DetailOrderActivity::class.java)
            context.startActivity(intent)
        }
    }
}