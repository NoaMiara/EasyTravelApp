package com.app.easy.travel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.helpers.FLIGHT_URI
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.view.flight.FileFlightActivity
import com.app.easy.travel.model.Flight

class FlightRecyclerViewAdapter(
    private val travelUri: String,
    private val recyclerViewInterface: RecyclerViewInterface,
    private val flightList: ArrayList<Flight> = ArrayList()
) : RecyclerView.Adapter<FlightRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flight_card_view, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flightViewModel = flightList[position]

        holder.arrivalFlightDate.text =
            "Arrival Date: ${flightViewModel.arrivalDate}"
        holder.arrivalFlightFrom.text =
            "Arrival: ${flightViewModel.arrival}"
        holder.arrivalFlightTime.text =
            "Arrival Time: ${flightViewModel.arrivalTime}"
        holder.arrivalFlightAirline.text =
            "Arrival Airline: ${flightViewModel.arrivalAirline}"
        holder.arrivalGate.text = "Arrival Gate: ${flightViewModel.arrivalGate}"

        holder.departureFlightFrom.text =
            "Departure: ${flightViewModel.departure}"
        holder.departureFlightDate.text =
            "Departure Date: ${flightViewModel.departureDate}"
        holder.departureFlightTime.text =
            "Departure Time: ${flightViewModel.departureTime}"
        holder.departureFlightAirline.text =
            "Departure Airline: ${flightViewModel.departureAirline}"
        holder.departureGate.text = "Departure Gate: ${flightViewModel.departureGate}"

        holder.flightNumber.text =
            "Flight Number: ${flightViewModel.arrivalFlightNumber}"

        holder.ticketPrice.text = "Ticket Price: ${flightViewModel.ticketPrice}"

        holder.file.setOnClickListener {

            var flightUri = flightViewModel.pid
            Intent(it.context, FileFlightActivity::class.java).apply {
                putExtra(FLIGHT_URI, flightUri)
                putExtra(TRAVEL_URI, travelUri)


                it.context.startActivity(this)
            }

        }


    }

    override fun getItemCount(): Int {
        return flightList.size
    }

    fun updateFlight(flight: List<Flight>) {
        this.flightList.clear()
        this.flightList.addAll(flight)
        notifyDataSetChanged()

    }

    fun removeFlight(position: Int) {
        notifyItemRemoved(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener,
        View.OnClickListener {
        val arrivalFlightFrom: TextView = itemView.findViewById(R.id.arrival_flight_from)
        val arrivalFlightDate: TextView = itemView.findViewById(R.id.arrival_flight_date)
        val arrivalFlightTime: TextView = itemView.findViewById(R.id.arrival_flight_time)
        val arrivalFlightAirline: TextView = itemView.findViewById(R.id.arrival_flight_airline)
        val arrivalGate: TextView = itemView.findViewById(R.id.arrival_gate)


        val departureFlightFrom: TextView = itemView.findViewById(R.id.departure_flight_from)
        val departureFlightDate: TextView = itemView.findViewById(R.id.departure_flight_date)
        val departureFlightTime: TextView = itemView.findViewById(R.id.departure_flight_time)
        val departureFlightAirline: TextView = itemView.findViewById(R.id.departure_flight_airline)
        val departureGate: TextView = itemView.findViewById(R.id.departure_gate)


        val flightNumber: TextView = itemView.findViewById(R.id.flight_number)
        val ticketPrice: TextView = itemView.findViewById(R.id.ticket_price)

        val file: TextView = itemView.findViewById(R.id.file_view)


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