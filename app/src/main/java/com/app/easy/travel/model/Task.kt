package com.app.easy.travel.model

import com.app.easy.travel.R

data class Task(
    var task:String?="",
    var pid:String?="",
    var completed:Boolean =false
    ){

    fun imageResource():Int = if(completed) R.drawable.ic_baseline_check_circle_outline_24 else
        R.drawable.ic_baseline_radio_button_unchecked_24

}
