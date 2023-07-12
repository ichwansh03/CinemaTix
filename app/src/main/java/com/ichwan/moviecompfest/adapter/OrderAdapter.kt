package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.databinding.ItemOrdersBinding
import com.ichwan.moviecompfest.model.OrderItem
import com.ichwan.moviecompfest.service.DeleteDataOrder
import com.ichwan.moviecompfest.service.GlobalData
import com.ichwan.moviecompfest.view.DetailOrderActivity

class OrderAdapter(var context: Context, var list: ArrayList<OrderItem>, var deleteOrder: DeleteDataOrder) : RecyclerView.Adapter<OrderAdapter.MyAdapterOrder>() {

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
        val order = list[position]
        holder.adapter(context, order.name, order.title, order.quantity)

        holder.binding.btnCancelOrder.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Remove Order")
            alertDialog.setMessage("Do you remove ticket order? ")
            alertDialog.setPositiveButton("Yes") {_, _ ->
                if (list.contains(order)) {
                    deleteOrder(order)
                    deleteOrder.onDelete(position)
                }
            }
        }

        holder.binding.cvFrameOrder.setOnClickListener {
            val intent = Intent(context, DetailOrderActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun deleteOrder(item: OrderItem){
        val queue = Volley.newRequestQueue(context)
        val url = GlobalData.BASE_URL+"order/deleteorder.php?id="+item.id

        val request = StringRequest(Request.Method.DELETE, url, { _ ->
            Toast.makeText(context, "Order ticket has been removed!", Toast.LENGTH_SHORT).show()
        },{
            error ->
            Log.d("error ",error.message.toString())
        })

        queue.add(request)
    }
}