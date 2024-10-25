package com.example.proyecto_dam_juntoz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.proyecto_dam_juntoz.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del producto desde el intent
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val especificaciones = intent.getStringExtra("especificaciones")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val stock = intent.getIntExtra("stock", 0)
        val marca = intent.getStringExtra("marca")
        val imagen = intent.getStringExtra("imagen")

        // Configurar la UI con los datos del producto
        binding.tvNombreProductoDetalle.text = nombre
        binding.tvDescripcionProductoDetalle.text = "Descripcion: ${descripcion}"
        binding.tvEspecificacionesProductoDetalle.text = "Especificaciones: ${especificaciones}"
        binding.tvPrecioProductoDetalle.text = "Precio: $${precio}"
        binding.tvStockProductoDetalle.text = "Stock: ${stock}"
        binding.tvMarcaProductoDetalle.text = "Marca: ${marca}"

        // Cargar la imagen con Glide
        Glide.with(this)
            .load(imagen)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(binding.imgProductoDetalle)

        // Configurar el botón para agregar al carrito (aún no hace nada)
        binding.btnAgregarCarrito.setOnClickListener {
            // Aquí puedes agregar lógica para añadir al carrito
        }

        // Configurar el botón de atrás
        binding.btnAtras.setOnClickListener {
            finish() // Finaliza la actividad y vuelve a la anterior
        }

        binding.btnAgregarCarrito.setOnClickListener {
            val producto = EntidadProducto(
                nombre = nombre ?: "",
                descripcion = descripcion ?: "",
                especificaciones = especificaciones ?: "",
                precio = precio,
                stock = stock,
                imagen = imagen ?: "",
            )
            CarritoManager.agregarProducto(producto)
            Toast.makeText(this, "${producto.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
        }
    }
}