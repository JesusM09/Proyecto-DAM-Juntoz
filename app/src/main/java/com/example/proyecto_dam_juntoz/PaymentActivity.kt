package com.example.proyecto_dam_juntoz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_dam_juntoz.databinding.ActivityPaymentBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val total = intent.getDoubleExtra("total", 0.0)
        supportActionBar?.title = "Pago Total: $${String.format("%.2f", total)}"

        binding.btnConfirmarPago.setOnClickListener {
            if (validarFormulario()) {
                guardarOrden()
            }
        }
    }

    private fun validarFormulario(): Boolean {
        val nombre = binding.etNombre.text.toString()
        val email = binding.etEmail.text.toString()
        val direccion = binding.etDireccion.text.toString()
        val tarjeta = binding.etTarjeta.text.toString()

        if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || tarjeta.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }


        return true
    }

    private fun guardarOrden() {

        binding.btnConfirmarPago.isEnabled = false


        val orderId = UUID.randomUUID().toString()
        val order = Order(
            orderId = orderId,
            clientName = binding.etNombre.text.toString(),
            email = binding.etEmail.text.toString(),
            deliveryAddress = binding.etDireccion.text.toString(),
            products = CarritoManager.obtenerProductos(),
            total = intent.getDoubleExtra("total", 0.0),
            date = System.currentTimeMillis(),
            status = "Confirmado"
        )

        // Guardar en Firestore
        db.collection("orders")
            .document(orderId)
            .set(order.toMap())
            .addOnSuccessListener {
                // Navegar a la pantalla de confirmación
                val intent = Intent(this, OrderConfirmationActivity::class.java).apply {
                    putExtra("orderId", orderId)
                    putExtra("clientName", order.clientName)
                    putExtra("address", order.deliveryAddress)
                    putExtra("total", order.total)
                }
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                binding.btnConfirmarPago.isEnabled = true
                Toast.makeText(
                    this,
                    "Error al procesar el pedido: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}