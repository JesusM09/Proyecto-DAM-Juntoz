package com.example.proyecto_dam_juntoz

data class EntidadProducto(
    val nombre: String = "",
    val descripcion: String = "",
    val especificaciones: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0,
    val imagen: String = "",
    val marca: String = ""
)