package com.example.proyecto_dam_juntoz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_dam_juntoz.databinding.ActivityOrderConfirmationBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del intent
        val orderId = intent.getStringExtra("orderId") ?: ""
        val clientName = intent.getStringExtra("clientName") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val total = intent.getDoubleExtra("total", 0.0)

        // Mostrar los detalles
        binding.tvOrderNumber.text = "Número de orden: $orderId"
        binding.tvClientName.text = "Cliente: $clientName"
        binding.tvDeliveryAddress.text = "Dirección de envío: $address"
        binding.tvOrderTotal.text = "Total: $${String.format("%.2f", total)}"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        binding.tvOrderDate.text = "Fecha: ${sdf.format(Date())}"

        binding.btnBackToHome.setOnClickListener {
            // Limpiar el carrito antes de volver al inicio
            CarritoManager.limpiarCarrito()
            finish()
        }
    }
}