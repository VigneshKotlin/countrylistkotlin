package com.interviewtask.app.weather.view

import android.app.Activity
import android.app.Dialog
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.interviewtask.app.R

class WeatherDialog {

    companion object{
        fun showAlert(activity: Activity, tempVal: String, weatherVal: String, humidityVal: String){
            val dialogView = Dialog(activity)
            dialogView.setContentView(R.layout.dialog_weather)
            val tempTv = dialogView.findViewById<TextView>(R.id.tempTV)
            val weatherTv = dialogView.findViewById<TextView>(R.id.weatherTV)
            val humidityTv = dialogView.findViewById<TextView>(R.id.humidityTV)
            val weatherImage = dialogView.findViewById<ImageView>(R.id.weatherIcon)
            val closeBtn = dialogView.findViewById<Button>(R.id.cancelButton)
            dialogView.setCancelable(false)

            if (weatherVal.toLowerCase() == "rain") {
                weatherImage.setImageResource(R.drawable.ic_rain)
            } else if (weatherVal.toLowerCase() == "mist") {
                weatherImage.setImageResource(R.drawable.ic_clouds)
            } else {
                weatherImage.setImageResource(R.drawable.ic_sun)
            }

            tempTv.text = "$tempVal \u2103"
            weatherTv.text = weatherVal
            humidityTv.text = humidityVal

            closeBtn.setOnClickListener { dialogView.dismiss() }
            dialogView.show()
        }
    }
}