package com.dhaval.blooddonorfinder

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SearchActivity : AppCompatActivity() {

    private lateinit var spinnerBloodGroup: Spinner
    private lateinit var etArea: EditText
    private lateinit var btnSearch: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        spinnerBloodGroup = findViewById(R.id.spinnerSearchBloodGroup)
        etArea = findViewById(R.id.etSearchArea)
        btnSearch = findViewById(R.id.btnSearch)

        // Spinner ko blood_groups_array se connect karna
        ArrayAdapter.createFromResource(
            this,
            R.array.blood_groups_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBloodGroup.adapter = adapter
        }

        btnSearch.setOnClickListener {
            val bloodGroup = spinnerBloodGroup.selectedItem.toString()
            val area = etArea.text.toString().trim().lowercase()

            if (bloodGroup == "Select Blood Group") {
                Toast.makeText(this, "Please select a blood group", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (area.isEmpty()) {
                Toast.makeText(this, "Please enter an area", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ResultsActivity ko bloodGroup aur area bhejna
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("bloodGroup", bloodGroup)
            intent.putExtra("area", area)
            startActivity(intent)
        }
    }
}