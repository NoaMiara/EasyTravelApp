package com.app.easy.travel.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.easy.travel.databinding.FragmentHomeTravelBinding
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.view.MainViewActivity

class HomeTravelFragment : Fragment() {
    private var _binding: FragmentHomeTravelBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeTravelViewModel
    private lateinit var userEmail: String
    private lateinit var travelUri: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        viewModel = ViewModelProvider(this)[HomeTravelViewModel::class.java]
        _binding = FragmentHomeTravelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        preferencesManager()
        viewModel.loadTravel(userEmail,travelUri)
        observeManager()


    }

    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }

    private fun observeManager() {
        viewModel.myTravelOb.observe(viewLifecycleOwner, Observer {
            binding.tvName.text="Trip to ${it.name}"
            binding.tvDate.text="on ${it.date}"
        })
        viewModel.flightTotalPrice.observe(viewLifecycleOwner, Observer {
            binding.flightPrice.text="Flights total price: $it$"
        })
        viewModel.hotelTotalPrice.observe(viewLifecycleOwner, Observer {
            binding.hotelPrice.text= "Hotels total price: $it$"
        })
        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            binding.totalPrice.text="Total price: $it$"
        })    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}