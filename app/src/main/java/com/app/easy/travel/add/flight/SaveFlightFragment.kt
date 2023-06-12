package com.app.easy.travel.add.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.easy.travel.databinding.FragmentSaveFlightBinding
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.helpers.flight
import com.app.easy.travel.helpers.imageData
import com.app.easy.travel.helpers.imagesCurrentTravel
import com.app.easy.travel.helpers.travel
import com.app.easy.travel.repository.AddFlightRepository
import java.util.UUID


class SaveFlightFragment : Fragment(), View.OnClickListener {


    private var _binding: FragmentSaveFlightBinding? = null

    private val binding get() = _binding!!
    private lateinit var userEmail: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferencesManager()
        _binding = FragmentSaveFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }

    private fun modeAddMoreForTravel()=(activity as AddFlightActivity?)?.isAddMoreForTravel == true




    override fun onResume() {
        super.onResume()
        updateView()
        _binding?.btnSaveAndMore?.setOnClickListener(this)
        _binding?.btnSaveDone?.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view) {
            _binding?.btnSaveAndMore -> {
                flightManagement()
                activity?.onBackPressed()
            }
            _binding?.btnSaveDone -> {
                flightManagement()
                activity?.finish()
            }

        }

    }

    private fun flightManagement() {
        val id = "${UUID.randomUUID()}"
        flight.pid = id
        flight.ticketPrice=price()
        if (modeAddMoreForTravel()) fireBaseRepository(id) else tempRepository(id)
        clearFun()
    }

    private fun clearFun() {
        flight.removeAll()
        imageData.clear()
    }

    private fun tempRepository(id: String) {
        travel.flights[id] = flight.copy()
        imageData.forEach { image ->
            imagesCurrentTravel.add(image)
        }    }

    private fun price(): Double {
        return if(_binding?.etTextPrice?.text.toString().isEmpty()) 0.0
        else _binding?.etTextPrice?.text.toString().toDouble()
    }

    private fun fireBaseRepository(id: String) {
        val travelUri = (activity as AddFlightActivity?)?.uriTravel
        val addFlightRepository = AddFlightRepository(travelUri!!, id, userEmail)
        addFlightRepository.saveOnFireBase()
        addFlightRepository.saveOnStorageFireBase()
    }

    private fun updateView() {
        if (!flight.arrival.isNullOrEmpty()) {
            _binding?.arrivalFlightSave?.text = "Arrival Flight : ${flight.arrival} "

        }
        if (!flight.arrivalDate.isNullOrEmpty()) {
            _binding?.arrivalFlightDateSave?.text = "Arrival Date : ${flight.arrivalDate} "
        }


        if (!flight.departure.isNullOrEmpty()) {
            _binding?.departureFlightSave?.text = "Departure Flight : ${flight.departure} "

        }
        if (!flight.departureDate.isNullOrEmpty()) {
            _binding?.departureFlightDateSave?.text = "Departure Date : ${flight.departureDate} "
        }


    }


}