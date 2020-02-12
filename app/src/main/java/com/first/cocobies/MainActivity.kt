package com.first.cocobies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

    val l =findViewById<LinearLayout>(R.id.l1)

       val uptodown = AnimationUtils.loadAnimation(this,R.anim.updown);

        l.setAnimation(uptodown);


        Handler().postDelayed(
            Runnable /*
                   * Showing splash screen with a timer. This will be useful when you
                   * want to show case your app logo / company
                   */

            {

                // This method will be executed once the timer is over
                // Start your app main activity
                val i = Intent(applicationContext, Homepage::class.java)
                startActivity(i)

                // close this activity
                finish()
            }, 3000
        )
    }

}
