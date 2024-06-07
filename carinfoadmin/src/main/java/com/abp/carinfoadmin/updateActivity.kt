package com.abp.carinfoadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abp.carinfoadmin.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.UpdateButton.setOnClickListener {
            val referenceVehicleNumber = binding.UpdateVehicleNumber.text.toString()
            val updateOwnerName = binding.UpdateOwnerName.text.toString()
            val updateVehicleBrand = binding.UpdateVehicleBrand.text.toString()
            val updateVehicleType = binding.UpdateVehicleType.text.toString()
            val updateVehicleArea = binding.UpdateVehicleArea.text.toString()

            updateData(referenceVehicleNumber, updateOwnerName, updateVehicleBrand, updateVehicleType, updateVehicleArea)
        }
    }

    private fun updateData(vehicleNumber: String, ownerName: String, vehicleBrand: String, vehicleType: String, vehicleArea: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        val vehicleData = mapOf(
            "ownerName" to ownerName,
            "vehicleType" to vehicleType,
            "vehicleBrand" to vehicleBrand,
            "vehicleArea" to vehicleArea
        )

        databaseReference.child(vehicleNumber).updateChildren(vehicleData)
            .addOnSuccessListener {
                binding.UpdateVehicleNumber.text.clear()
                binding.UpdateOwnerName.text.clear()
                binding.UpdateVehicleBrand.text.clear()
                binding.UpdateVehicleType.text.clear()
                binding.UpdateVehicleArea.text.clear()
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Unable To Update", Toast.LENGTH_SHORT).show()
            }
    }
}
