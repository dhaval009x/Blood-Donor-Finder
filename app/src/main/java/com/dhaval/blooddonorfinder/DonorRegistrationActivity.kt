package com.dhaval.blooddonorfinder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DonorRegistrationActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var spinnerBloodGroup: Spinner
    private lateinit var etArea: EditText
    private lateinit var btnSave: MaterialButton

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_registration)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etName = findViewById(R.id.etDonorName)
        etPhone = findViewById(R.id.etDonorPhone)
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup)
        etArea = findViewById(R.id.etDonorArea)
        btnSave = findViewById(R.id.btnSaveDonor)

        // Spinner ko blood_groups_array se connect karna
        ArrayAdapter.createFromResource(
            this,
            R.array.blood_groups_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBloodGroup.adapter = adapter
        }

        btnSave.setOnClickListener {
            saveDonorDetails()
        }
    }

    private fun saveDonorDetails() {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val bloodGroup = spinnerBloodGroup.selectedItem.toString()
        val area = etArea.text.toString().trim().lowercase()
        // Validation checks
        if (name.isEmpty() || phone.isEmpty() || area.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.length != 10) {
            Toast.makeText(this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
            return
        }

        if (bloodGroup == "Select Blood Group") {
            Toast.makeText(this, "Please select your blood group", Toast.LENGTH_SHORT).show()
            return
        }

        // Current logged-in user ki unique ID
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Donor ka data ek Map (key-value pairs) mein taiyar karna
        val donorData = hashMapOf(
            "name" to name,
            "phone" to phone,
            "bloodGroup" to bloodGroup,
            "area" to area,
            "available" to true
        )

        // Firestore ke "donors" collection mein save karna
        db.collection("donors").document(userId)
            .set(donorData)
            .addOnSuccessListener {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                // Abhi ke liye khali, baad mein Home ya Search screen pe bhej sakte hain
                // finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}