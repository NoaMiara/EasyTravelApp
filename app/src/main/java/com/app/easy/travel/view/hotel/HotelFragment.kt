package com.app.easy.travel.view.hotel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.adapter.HotelRecyclerViewAdapter
import com.app.easy.travel.add.hotel.AddHotelActivity
import com.app.easy.travel.databinding.FragmentHotelBinding
import com.app.easy.travel.view.MainViewActivity

class HotelFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerview: RecyclerView
    private lateinit var userEmail: String
    private lateinit var travelUri: String
    private lateinit var hotelRecyclerViewAdapter: HotelRecyclerViewAdapter
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        hotelViewModel = ViewModelProvider(this)[HotelViewModel::class.java]
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        preferencesManager()
        recyclerview = binding.hotelRecyclerview
        recyclerviewManager()
        binding.btnAddMoreHotel.setOnClickListener {
            Intent(context, AddHotelActivity::class.java).apply {
                putExtra(TRAVEL_URI, travelUri)
                startActivity(this)

            }

        }


    }

    private fun recyclerviewManager() {

        recyclerview.apply {
            hotelViewModel.loadData(travelUri, userEmail)

            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            recyclerview.setHasFixedSize(true)
            hotelRecyclerViewAdapter = HotelRecyclerViewAdapter(travelUri, this@HotelFragment)

            // set the custom adapter to the RecyclerView
            recyclerview.adapter = hotelRecyclerViewAdapter
            hotelViewModel.publicAllHotel.observe(viewLifecycleOwner, Observer {
                hotelRecyclerViewAdapter.updateHotel(it)
            })
        }


    }

    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun alertDialogForImageDelete(position: Int) {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Would you like to remove this hotel?")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                hotelViewModel.remove(position, travelUri, userEmail)
                hotelRecyclerViewAdapter.removeHotel(position)


            }.show()
    }

    override fun onItemClick(delete: Boolean, position: Int) {
        if (delete) {
            alertDialogForImageDelete(position)
        }


    }


}