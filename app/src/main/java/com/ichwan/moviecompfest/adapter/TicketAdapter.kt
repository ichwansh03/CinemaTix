package com.ichwan.moviecompfest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ichwan.moviecompfest.databinding.ItemSeatsNumberBinding
import com.ichwan.moviecompfest.model.SeatItem

class TicketAdapter (var context: Context, var list: ArrayList<SeatItem>) :
    RecyclerView.Adapter<TicketAdapter.MyAdapterTicket>() {

    class MyAdapterTicket(val binding: ItemSeatsNumberBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun adapter(context: Context, seat: Int){
            binding.numberSeat.text = "Number $seat"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterTicket {
        val binding = ItemSeatsNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapterTicket(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyAdapterTicket, position: Int) {
        holder.adapter(context, list[position].seats)
    }
}