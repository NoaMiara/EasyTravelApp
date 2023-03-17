package com.app.easy.travel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.helpers.HOTEL_URI
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.model.Hotel
import com.app.easy.travel.view.hotel.FileHotelActivity

class HotelRecyclerViewAdapter(private val travelUri: String,
                               private val recyclerViewInterface: RecyclerViewInterface,
                               private val hotelList: ArrayList<Hotel> = ArrayList()
):RecyclerView.Adapter<HotelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotelViewModel = hotelList[position]

        holder.hotelName.text = "Hotel Name : ${hotelViewModel.hotelName}"
        holder.hotelLocation.text = "Hotel Location : ${hotelViewModel.location}"
        holder.hotelDate.text = "Date : ${hotelViewModel.date}"
        holder.hotelCheckIn.text = "Check In : ${hotelViewModel.checkIn}"
        holder.hotelCheckOut.text = "Check Out : ${hotelViewModel.checkOut}"

        holder.ticketPrice.text = "Ticket Price: ${hotelViewModel.price}"

        holder.file.setOnClickListener {

            var hotelUri = hotelViewModel.pid
            Intent(it.context, FileHotelActivity::class.java).apply {
                putExtra(HOTEL_URI, hotelUri)
                putExtra(TRAVEL_URI, travelUri)
                it.context.startActivity(this)
            }

        }


    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    fun updateHotel(hotel: List<Hotel>) {
        this.hotelList.clear()
        this.hotelList.addAll(hotel)
        notifyDataSetChanged()

    }

    fun removeHotel(position: Int) {
        notifyItemRemoved(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener,
        View.OnClickListener {
        val hotelName: TextView = itemView.findViewById(R.id.hotel_name)
        val hotelLocation: TextView = itemView.findViewById(R.id.hotel_location)
        val hotelDate: TextView = itemView.findViewById(R.id.hotel_date)
        val hotelCheckIn: TextView = itemView.findViewById(R.id.hotel_check_in)
        val hotelCheckOut: TextView = itemView.findViewById(R.id.hotel_check_out)

        val ticketPrice: TextView = itemView.findViewById(R.id.ticket_price_hotel)
        val file: TextView = itemView.findViewById(R.id.file_view_hotel)


        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)

        }

        override fun onLongClick(p0: View?): Boolean {
            val pos = bindingAdapterPosition
            recyclerViewInterface.onItemClick(true, pos)
            return true
        }

        override fun onClick(p0: View?) {
            val pos = bindingAdapterPosition
            recyclerViewInterface.onItemClick(false, pos)
        }


    }
}