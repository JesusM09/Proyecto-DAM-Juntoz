package com.example.proyecto_dam_juntoz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_dam_juntoz.databinding.FragmentOrderBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderAdapter: OrderAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadOrders()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(emptyList())
        binding.recyclerViewOrders.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }

    private fun loadOrders() {
        binding.progressBar.visibility = View.VISIBLE

        db.collection("orders")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val ordersList = documents.mapNotNull { document ->
                    try {
                        Order(
                            orderId = document.getString("orderId") ?: "",
                            clientName = document.getString("clientName") ?: "",
                            email = document.getString("email") ?: "",
                            deliveryAddress = document.getString("deliveryAddress") ?: "",
                            total = document.getDouble("total") ?: 0.0,
                            date = document.getLong("date") ?: 0L,
                            status = document.getString("status") ?: "Confirmado"
                        )
                    } catch (e: Exception) {
                        null
                    }
                }

                orderAdapter.updateOrders(ordersList)
                binding.progressBar.visibility = View.GONE

                if (ordersList.isEmpty()) {
                    Toast.makeText(context, "No hay pedidos registrados", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Error al cargar los pedidos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}