package com.example.proyecto_dam_juntoz

import AdaptadorProductos
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView para mostrar productos en el carrito
        val productosEnCarrito = CarritoManager.obtenerProductos()
        binding.recyclerViewCarrito.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCarrito.adapter = AdaptadorProductos(productosEnCarrito, false)

        // Mostrar el total del carrito
        val total = CarritoManager.obtenerTotal()
        binding.tvTotalCarrito.text = "Total: $${total}"

        // Configurar el botón para ir a pagar
        binding.btnIrAPagar.setOnClickListener {
            Toast.makeText(context, "Redirigiendo a la pantalla de pago...", Toast.LENGTH_SHORT).show()
            // Aquí podrías implementar la funcionalidad de pago
        }

        // Configurar el botón para vaciar el carrito
        binding.btnVaciarCarrito.setOnClickListener {
            CarritoManager.limpiarCarrito()
            binding.recyclerViewCarrito.adapter?.notifyDataSetChanged()
            binding.tvTotalCarrito.text = "Total: $0.00"
            Toast.makeText(context, "El carrito ha sido vaciado.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}