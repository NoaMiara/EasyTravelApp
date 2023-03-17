package com.app.easy.travel.model

import com.app.easy.travel.model.Flight
import com.app.easy.travel.model.Hotel
import com.app.easy.travel.model.Task

data class Travel(
    var name: String? = null,
    var date: String? = null,
    var pid: String? = null,
    var flights: HashMap<String, Flight> = HashMap(),
    var hotels: HashMap<String, Hotel> = HashMap(),
    var tasks: HashMap<String, Task> = HashMap(),
    ) {


    fun clear() {
        name = null
        pid = null
        flights.clear()
        hotels.clear()
        tasks.clear()


    }
}