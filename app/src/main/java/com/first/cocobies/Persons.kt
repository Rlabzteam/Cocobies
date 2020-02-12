package com.first.cocobies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Persons : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Person")
    }
    override
    fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
