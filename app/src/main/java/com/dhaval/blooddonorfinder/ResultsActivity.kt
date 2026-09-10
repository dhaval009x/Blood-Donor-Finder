package com.dhaval.blooddonorfinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ResultsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var db: FirebaseFirestore

    // Phone numbers ki list, calling ke liye alag se store karenge
    private val phoneNumbers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        listView = findViewById(R.id.listViewResults)
        db = FirebaseFirestore.getInstance()

        // SearchActivity se bheja hua data receive karna
        val bloodGroup = intent.getStringExtra("bloodGroup") ?: ""
        val area = intent.getStringExtra("area") ?: ""

        searchDonors(bloodGroup, area)
    }

    private fun searchDonors(bloodGroup: String, area: String) {
        db.collection("donors")
            .whereEqualTo("bloodGroup", bloodGroup)
            .whereEqualTo("area", area)
            .whereEqualTo("available", true)
            .get()
            .addOnSuccessListener { documents ->
                val displayList = mutableListOf<String>()
                phoneNumbers.clear()

                if (documents.isEmpty) {
                    Toast.makeText(this, "No donors found", Toast.LENGTH_SHORT).show()
                } else {
                    for (doc in documents) {
                        val name = doc.getString("name") ?: "Unknown"
                        val phone = doc.getString("phone") ?: ""
                        displayList.add("$name\n$bloodGroup | $area")
                        phoneNumbers.add(phone)
                    }
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayList
                )
                listView.adapter = adapter

                // List ke item pe click karte hi call dialer khulega
                listView.setOnItemClickListener { _, _, position, _ ->
                    val phone = phoneNumbers[position]
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}