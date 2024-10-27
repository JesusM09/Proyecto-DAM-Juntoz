package com.example.proyecto_dam_juntoz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(private var orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tvOrderId)
        val tvOrderClientName: TextView = view.findViewById(R.id.tvOrderClientName)
        val tvOrderAddress: TextView = view.findViewById(R.id.tvOrderAddress)
        val tvOrderTotal: TextView = view.findViewById(R.id.tvOrderTotal)
        val tvOrderDate: TextView = view.findViewById(R.id.tvOrderDate)
        val tvOrderStatus: TextView = view.findViewById(R.id.tvOrderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.tvOrderId.text = "Orden #${order.orderId.take(8)}"
        holder.tvOrderClientName.text = "Cliente: ${order.clientName}"
        holder.tvOrderAddress.text = "Dirección: ${order.deliveryAddress}"
        holder.tvOrderTotal.text = "Total: $${String.format("%.2f", order.total)}"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = Date(order.date)
        holder.tvOrderDate.text = "Fecha: ${sdf.format(date)}"
        holder.tvOrderStatus.text = order.status
    }

    override fun getItemCount() = orders.size

    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}