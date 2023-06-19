package com.app.easy.travel.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.easy.travel.adapter.AttractionsAdapter
import com.app.easy.travel.databinding.FragmentHomeTravelBinding
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.ui.MainViewActivity

class HomeTravelFragment : Fragment() {
    private var _binding: FragmentHomeTravelBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeTravelViewModel
    private lateinit var userEmail: String
    private lateinit var travelUri: String
    private var destination: String = ""
    private val adapter: AttractionsAdapter by lazy { AttractionsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        viewModel = ViewModelProvider(this)[HomeTravelViewModel::class.java]
        _binding = FragmentHomeTravelBinding.inflate(inflater, container, false)
        binding.recyclerViewAttractions.adapter = adapter
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
        viewModel.myTravelOb.observe(viewLifecycleOwner) {
            destination = it.name.toString()
            if (destination.isNotEmpty()) {
                Log.d("Places", "Destination ==> $destination")
                viewModel.getAttractions(destination)
            }
            binding.tvName.text = "Trip to ${it.name}"
            binding.tvDate.text = "on ${it.date}"
        }
        viewModel.flightTotalPrice.observe(viewLifecycleOwner) {
            binding.flightPrice.text = "Flights total price: $it$"
        }
        viewModel.hotelTotalPrice.observe(viewLifecycleOwner) {
            binding.hotelPrice.text = "Hotels total price: $it$"
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.text = "Total price: $it$"
        }

        viewModel.attractions.observe(viewLifecycleOwner) {
            Log.d("Places", "Places observed ==> ${it?.size}")
            adapter.submitList(it)
            Log.d("Places", "Adapter item count ==> ${adapter.itemCount}")
            binding.recyclerViewAttractions.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}