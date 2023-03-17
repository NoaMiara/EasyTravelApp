package com.app.easy.travel.model

import android.util.Log

data class Flight(
    var pid: String? = "",


    var arrival: String? = "",
    var arrivalDate: String? = "",
    var arrivalTime: String? = "",
    var arrivalAirline: String? = "",
    var arrivalFlightNumber: String? = "",
    var arrivalGate: String? = "",

    var departure: String? = "",
    var departureDate: String? = "",
    var departureTime: String? = "",
    var departureAirline: String? = "",
    var departureFlightNumber: String? = "",
    var departureGate: String? = "",

    var file : MutableMap<String,String>? = mutableMapOf(),

    var ticketPrice: Double = 0.0


) {
    fun copy(): Flight {
        return Flight(
            pid,
            arrival,
            arrivalDate,
            arrivalTime,
            arrivalAirline,
            arrivalFlightNumber,
            arrivalGate,
            departure,
            departureDate,
            departureTime,
            departureAirline,
            departureFlightNumber,
            departureGate,
            createCopyFile(),
            ticketPrice

        )

    }

    private fun createCopyFile(): MutableMap<String,String>? {
        var newFile: MutableMap<String,String>? = mutableMapOf()

        if (file != null) {
            file?.forEach {
                newFile?.put(it.key,it.value);

            }
        }
        Log.d("copy", newFile.toString())

        return newFile
    }

    fun removeAll() {
        pid = ""
        arrival = ""
        arrivalDate = ""
        arrivalTime = ""
        arrivalAirline = ""
        arrivalFlightNumber = ""
        arrivalGate = ""
        departure = ""
        departureDate = ""
        departureTime = ""
        departureAirline = ""
        departureFlightNumber = ""
        departureGate = ""
        file?.clear()
        ticketPrice = 0.0
    }
}


