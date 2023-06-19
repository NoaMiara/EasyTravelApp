package com.app.easy.travel.main.home

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
import com.app.easy.travel.ui.MainViewActivity
import com.app.easy.travel.adapter.TravelRecyclerViewAdapter
import com.app.easy.travel.databinding.FragmentHomeBinding
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.intarface.RecyclerViewInterface

class HomeFragment : Fragment() , RecyclerViewInterface {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var recyclerview: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var travelRecyclerViewAdapter: TravelRecyclerViewAdapter
    private lateinit var userEmail: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         homeViewModel =ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()


        recyclerview = binding.recyclerview



        recyclerview.apply {
            homeViewModel.loadTravels(userEmail)

            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            recyclerview.setHasFixedSize(true)
            travelRecyclerViewAdapter = TravelRecyclerViewAdapter(this@HomeFragment)

            // set the custom adapter to the RecyclerView
            recyclerview.adapter = travelRecyclerViewAdapter
            homeViewModel.publicAllTravels.observe(viewLifecycleOwner, Observer {
                travelRecyclerViewAdapter.updateTravels(it)
            })
        }
    }




    private fun alertDialogForImageDelete(position: Int) {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Would you like to remove this travel?")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                homeViewModel.remove(position,userEmail)
                travelRecyclerViewAdapter.removeTravels(position)
            }.show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(toDelete: Boolean, position: Int) {
        if (toDelete) {
            alertDialogForImageDelete(position)
        }
        else{
            Intent(context, MainViewActivity::class.java).apply {
                var uri = homeViewModel.getTravel(position)!!.pid
                putExtra(TRAVEL_URI,uri)
                startActivity(this)
            }

        }
    }
}