package com.app.easy.travel.model

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