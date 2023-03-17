package com.app.easy.travel.model
data class Hotel(
    var pid: String? = "",
    var hotelName:String? ="",
    var location:String? ="",
    var date:String?="",
    var checkIn:String?="",
    var checkOut:String?="",
    var file : MutableMap<String,String>? = mutableMapOf(),
    var price: Double = 0.0
) {
    fun copy(): Hotel {
        return Hotel(
            pid,
            hotelName,
            location,
            date,
            checkIn,
            checkOut,
            createCopyFile(),
            price

        )
    }
    private fun createCopyFile(): MutableMap<String,String>? {
        var newFile: MutableMap<String,String>? = mutableMapOf()

        if (file != null) {
            file?.forEach {
                newFile?.put(it.key,it.value);

            }
        }
        return newFile
    }
    fun removeAll() {
        pid = ""
        hotelName=""
        location=""
        date=""
        checkIn=""
        checkOut=""
        file?.clear()
        price =0.0


    }

    }