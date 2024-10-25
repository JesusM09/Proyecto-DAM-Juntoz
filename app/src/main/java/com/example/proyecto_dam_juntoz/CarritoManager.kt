package com.example.proyecto_dam_juntoz

object CarritoManager {
    private val productosEnCarrito = mutableListOf<EntidadProducto>()

    fun agregarProducto(producto: EntidadProducto) {
        productosEnCarrito.add(producto)
    }

    fun obtenerProductos(): List<EntidadProducto> {
        return productosEnCarrito
    }

    fun limpiarCarrito() {
        productosEnCarrito.clear()
    }

    fun obtenerTotal(): Double {
        return productosEnCarrito.sumOf { it.precio }
    }
}