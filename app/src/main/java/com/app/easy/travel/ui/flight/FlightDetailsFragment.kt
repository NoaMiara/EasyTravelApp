package com.app.easy.travel.ui.flight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easy.travel.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_flight_details.flight_details_text
import kotlinx.android.synthetic.main.fragment_flight_details.flight_number_input
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FlightDetailsFragment : Fragment() {

    private val client = OkHttpClient()
    private val API_ENDPOINT = "https://opensky-network.org/states/all"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set up any additional UI elements or listeners here
        val getFlightDetailsButton = view.findViewById<FloatingActionButton>(R.id.get_flight_details_button)
        getFlightDetailsButton.setOnClickListener(onClickListener)
    }



    private val onClickListener = View.OnClickListener {
        val flightNumber = flight_number_input.text.toString()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val url = "$API_ENDPOINT?icao24=$flightNumber&date=$currentDate"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // handle error
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Log.d("FlightDetailsFragment", "Response data: $responseData")
                val json = JSONObject(responseData)
                Log.d("FlightDetailsFragment", "Request URL: $url")

                Log.d("FlightDetailsFragment", "JSON response: $responseData")

                val flightDetails = json.getJSONArray("states").optString(0)
                Log.d("FlightDetailsFragment", "JSON response: $responseData")


                activity?.runOnUiThread {
                    flight_details_text.text = flightDetails
                }
            }
        })
    }


}
