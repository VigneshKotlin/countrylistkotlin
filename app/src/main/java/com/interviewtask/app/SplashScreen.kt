package com.interviewtask.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.interviewtask.app.countrylist.view.HomeParentActivity

class SplashScreen : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HomeParentActivity::class.java)
        startActivity(intent)
        finish()
    }



}