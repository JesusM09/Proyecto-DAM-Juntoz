import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_dam_juntoz.EntidadProducto
import com.example.proyecto_dam_juntoz.ProductDetailActivity
import com.example.proyecto_dam_juntoz.R

class AdaptadorProductos(
    private val listaProductos: List<EntidadProducto>,
    private val mostrarDetalle: Boolean = true // Variable para manejar si se abre el detalle o no
) : RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        val tvStock: TextView = itemView.findViewById(R.id.tvStockProducto)
        val tvMarca: TextView = itemView.findViewById(R.id.tvMarcaProducto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "Precio: $${producto.precio}"
        holder.tvStock.text = "Stock: ${producto.stock}"
        holder.tvMarca.text = producto.marca


        // Usar Glide para cargar la imagen desde la URL
        Glide.with(holder.itemView.context)
            .load(producto.imagen)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(holder.imgProducto)

        // Manejar el clic para mostrar detalles del producto solo si es necesario
        if (mostrarDetalle) {
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("nombre", producto.nombre)
                intent.putExtra("descripcion", producto.descripcion)
                intent.putExtra("especificaciones", producto.especificaciones)
                intent.putExtra("precio", producto.precio)
                intent.putExtra("stock", producto.stock)
                intent.putExtra("marca", producto.marca)
                intent.putExtra("imagen", producto.imagen)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }
}