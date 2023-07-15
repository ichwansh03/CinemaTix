package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ichwan.moviecompfest.databinding.ItemTicketBinding
import com.ichwan.moviecompfest.model.TicketItem
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TicketAdapter(var context: Context, var list: ArrayList<TicketItem>) : RecyclerView.Adapter<TicketAdapter.MyAdapterTicket>() {

    class MyAdapterTicket(val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        private val formatRp = NumberFormat.getCurrencyInstance(Locale("id","ID"))

        @SuppressLint("SetTextI18n")
        fun adapter(name: String, title: String, seat: Int, cost: Int){
            binding.nameTicket.text = name
            binding.titleTicket.text = title
            binding.numberSeat.text = "Number $seat"
            binding.costTicket.text = formatRp.format(cost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterTicket {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapterTicket(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyAdapterTicket, position: Int) {
        holder.adapter(
            list[position].name,
            list[position].title,
            list[position].seats,
            list[position].total
        )
    }
}