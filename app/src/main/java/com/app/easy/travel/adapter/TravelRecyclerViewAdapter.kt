package com.app.easy.travel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.model.Travel
import com.app.easy.travel.main.home.HomeFragment

class TravelRecyclerViewAdapter(
    private val recyclerViewInterface: HomeFragment,
    private val TravelList: ArrayList<Travel> = ArrayList()
) : RecyclerView.Adapter<TravelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.travel_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val travelViewModel = TravelList[position]
        // sets the text to the textview from our itemHolder class
        holder.travelName.text = "My Travel to : ${travelViewModel.name}"
        holder.date.text = "on ${travelViewModel.date}"

    }

    override fun getItemCount(): Int {
        return TravelList.size
    }

    fun updateTravels(travelList: List<Travel>) {
        this.TravelList.clear()
        this.TravelList.addAll(travelList)
        notifyDataSetChanged()

    }

    fun removeTravels(position: Int) {
        notifyItemRemoved(position)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener,
        View.OnClickListener {
        val travelName: TextView = itemView.findViewById(R.id.tv_name)
        val date: TextView = itemView.findViewById(R.id.tv_date)

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