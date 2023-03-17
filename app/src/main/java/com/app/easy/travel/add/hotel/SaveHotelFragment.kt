package com.app.easy.travel.add.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.app.easy.travel.databinding.FragmentSaveHotelBinding
import com.app.easy.travel.helpers.*
import com.app.easy.travel.repository.AddHotelRepository
import java.util.*


class SaveHotelFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSaveHotelBinding? = null

    private val binding get() = _binding!!
    private lateinit var userEmail: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferencesManager()
        _binding = FragmentSaveHotelBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        updateView()
        _binding?.btnSaveAndMore?.setOnClickListener(this)
        _binding?.btnSaveDone?.setOnClickListener(this)

    }

    private fun modeAddMoreForTravel() =
        (activity as AddHotelActivity?)?.isAddMoreForTravel == true


    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }


    override fun onClick(view: View?) {
        when (view) {
            _binding?.btnSaveAndMore -> {
                hotelManagement()
                activity?.onBackPressed()
            }
            _binding?.btnSaveDone -> {
                hotelManagement()
                activity?.finish()
            }

        }

    }


    private fun hotelManagement() {
        val id = "${UUID.randomUUID()}"
        hotel.pid = id
        hotel.price = price()
        if (modeAddMoreForTravel()) fireBaseRepository(id) else tempRepository(id)
        clearFun()

    }


    private fun clearFun() {
        hotel.removeAll()
        imageData.clear()
    }

    private fun tempRepository(id: String) {
        travel.hotels[id] = hotel.copy()
        imageData.forEach { image ->
            imagesCurrentTravel.add(image)
        }
    }

    private fun price(): Double {
        return if (_binding?.etTextPrice?.text.toString().isEmpty()) 0.0
        else _binding?.etTextPrice?.text.toString().toDouble()
    }

    private fun fireBaseRepository(id: String) {
        val travelUri = (activity as AddHotelActivity?)?.uriTravel
        val addHotelRepository = AddHotelRepository(travelUri!!, id, userEmail)
        addHotelRepository.saveOnFireBase()
        addHotelRepository.saveOnStorageFireBase()
    }


    private fun updateView() {
        if (!hotel.hotelName.isNullOrEmpty()) {
            _binding?.hotelNameSave?.text = "Hotel Name : ${hotel.hotelName} "

        }
        if (!hotel.location.isNullOrEmpty()) {
            _binding?.hotelLocationSave?.text = "Hotel Location : ${hotel.location} "
        }


        if (!hotel.date.isNullOrEmpty()) {
            _binding?.hotelDateSave?.text = "Hotel Date : ${hotel.date} "

        }


    }


}