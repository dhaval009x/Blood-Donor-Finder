package com.dhaval.blooddonorfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnDonateBlood: MaterialButton
    private lateinit var btnFindBlood: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Buttons ko layout se connect karna
        btnDonateBlood = findViewById(R.id.btnDonateBlood)
        btnFindBlood = findViewById(R.id.btnFindBlood)

        // "Donate Blood" button click listener
        btnDonateBlood.setOnClickListener {

             val intent = Intent(this, LoginActivity::class.java)
             startActivity(intent)
        }

        // "Find Blood" button click listener
        btnFindBlood.setOnClickListener {
            // Abhi ke liye khali rakha hai, baad mein SearchActivity banayenge
             val intent = Intent(this, SearchActivity::class.java)
             startActivity(intent)
        }
    }
}