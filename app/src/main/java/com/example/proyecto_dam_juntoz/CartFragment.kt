package com.example.proyecto_dam_juntoz

import AdaptadorProductos
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_dam_juntoz.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adaptador: AdaptadorProductos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerView()
        actualizarTotal()
        configurarBotones()
    }

    private fun configurarRecyclerView() {
        adaptador = AdaptadorProductos(CarritoManager.obtenerProductos(), false)
        binding.recyclerViewCarrito.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adaptador
        }
    }

    private fun actualizarTotal() {
        val total = CarritoManager.obtenerTotal()
        binding.tvTotalCarrito.text = "Total: $${String.format("%.2f", total)}"
    }

    private fun configurarBotones() {
        binding.btnIrAPagar.setOnClickListener {
            if (CarritoManager.obtenerProductos().isEmpty()) {
                Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("total", CarritoManager.obtenerTotal())
            startActivity(intent)
        }

        binding.btnVaciarCarrito.setOnClickListener {
            CarritoManager.limpiarCarrito()
            actualizarTotal()
            adaptador.notifyDataSetChanged()
            Toast.makeText(context, "El carrito ha sido vaciado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
        actualizarTotal()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}