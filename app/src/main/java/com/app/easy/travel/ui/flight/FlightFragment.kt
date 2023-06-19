package com.app.easy.travel.ui.flight

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.FlightRecyclerViewAdapter
import com.app.easy.travel.add.flight.AddFlightActivity
import com.app.easy.travel.databinding.FragmentFlightBinding
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.ui.MainViewActivity

class FlightFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentFlightBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerview: RecyclerView
    private lateinit var flightViewModel: FlightViewModel
    private lateinit var flightRecyclerViewAdapter: FlightRecyclerViewAdapter
    private lateinit var userEmail: String
    private lateinit var travelUri: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        _binding = FragmentFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        preferencesManager()
        recyclerview = binding.flightRecyclerview
        recyclerviewManager()
        binding.btnAddMoreFlight.setOnClickListener {
            Intent(context, AddFlightActivity::class.java).apply {
                putExtra(TRAVEL_URI, travelUri)
                startActivity(this)

            }

        }


    }

    private fun recyclerviewManager(){
        recyclerview.apply {

            flightViewModel.loadData(travelUri,userEmail)

            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            recyclerview.setHasFixedSize(true)
            flightRecyclerViewAdapter = FlightRecyclerViewAdapter(travelUri,this@FlightFragment)

            // set the custom adapter to the RecyclerView
            recyclerview.adapter = flightRecyclerViewAdapter
            flightViewModel.publicAllFlight.observe(viewLifecycleOwner, Observer {
                flightRecyclerViewAdapter.updateFlight(it)
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

        pictureDialog.setTitle("Would you like to remove this flight?")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                flightViewModel.remove(position,travelUri,userEmail)
                flightRecyclerViewAdapter.removeFlight(position)


            }.show()
    }

    override fun onItemClick(delete: Boolean, position: Int) {
        if (delete) {
            alertDialogForImageDelete(position)
        }


    }

}