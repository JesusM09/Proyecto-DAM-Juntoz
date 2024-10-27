package com.example.proyecto_dam_juntoz

data class Order(
    val orderId: String = "",
    val clientName: String = "",
    val email: String = "",
    val deliveryAddress: String = "",
    val products: List<EntidadProducto> = listOf(),
    val total: Double = 0.0,
    val date: Long = System.currentTimeMillis(),
    val status: String = "Confirmado"
) {
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "orderId" to orderId,
            "clientName" to clientName,
            "email" to email,
            "deliveryAddress" to deliveryAddress,
            "products" to products.map { product ->
                hashMapOf(
                    "nombre" to product.nombre,
                    "precio" to product.precio,
                    "cantidad" to 1
                )
            },
            "total" to total,
            "date" to date,
            "status" to status
        )
    }
}