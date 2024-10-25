package com.example.proyecto_dam_juntoz

import AdaptadorProductos
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_dam_juntoz.databinding.FragmentProductsBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val categoryMap = mutableMapOf<String, DocumentReference>()
    private val listaProductos = mutableListOf<EntidadProducto>()
    private lateinit var adaptadorProductos: AdaptadorProductos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        binding.recyclerViewProductos.layoutManager = LinearLayoutManager(requireContext())
        adaptadorProductos = AdaptadorProductos(listaProductos)
        binding.recyclerViewProductos.adapter = adaptadorProductos

        // Cargar las categorías desde Firestore y configurar el Spinner
        loadCategories()
    }

    private fun loadCategories() {
        db.collection("categorias")
            .get()
            .addOnSuccessListener { resultado ->
                val categoryNames = mutableListOf<String>()
                for (documento in resultado) {
                    val nombreCategoria = documento.getString("nombreCategoria") ?: "Sin nombre"
                    categoryMap[nombreCategoria] = documento.reference
                    categoryNames.add(nombreCategoria)
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategories.adapter = adapter

                // Detectar cambios en la selección del Spinner
                binding.spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val selectedCategory = categoryNames[position]
                        val categoryRef = categoryMap[selectedCategory]

                        // Actualizar el texto del TextView con la categoría seleccionada
                        binding.tvSelectedCategory.text = "Categoría: $selectedCategory"

                        if (categoryRef != null) {
                            loadProductsByCategory(categoryRef)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                    }
                }
            }
            .addOnFailureListener {
                binding.tvConsulta.text = "No se pudieron cargar las categorías"
            }
    }

    private fun loadProductsByCategory(categoryRef: DocumentReference) {
        db.collection("productos")
            .whereEqualTo("categoria", categoryRef)
            .get()
            .addOnSuccessListener { resultado ->
                listaProductos.clear()
                for (documento in resultado) {
                    val producto = documento.toObject(EntidadProducto::class.java)
                    listaProductos.add(producto)
                }
                adaptadorProductos.notifyDataSetChanged() // Actualizar el RecyclerView
            }
            .addOnFailureListener {
                binding.tvConsulta.text = "No se pudieron cargar los productos"
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}