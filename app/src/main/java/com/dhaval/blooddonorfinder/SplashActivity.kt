package com.dhaval.blooddonorfinder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 2 second (2000 millisecond) delay ke baad MainActivity pe jaayenge
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // SplashActivity ko close kar do taaki back button dabane pe wapas splash pe na aaye
        }, 2000)
    }
}