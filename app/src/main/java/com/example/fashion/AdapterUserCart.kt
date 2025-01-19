package com.example.fashion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.UserCartResponse
import com.squareup.picasso.Picasso

class AdapterUserCart(private val listCart:ArrayList<UserCartResponse>,
                      private val onCheckboxChanged: (Boolean, Int, List<String>) -> Unit
): RecyclerView.Adapter<AdapterUserCart.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgProduct: ImageView = v.findViewById(R.id.product_image)
        val productName: TextView = v.findViewById(R.id.product_title)
        val productSize: TextView = v.findViewById(R.id.product_size)
        val priceProduct: TextView = v.findViewById(R.id.product_price)
        val productCheckbox: CheckBox = v.findViewById(R.id.checkbox)

        fun bind(response: UserCartResponse, position: Int) {
            productName.text = response.nama_produk
            priceProduct.text = "Rp ${response.harga}"
            productSize.text = "Size: ${response.size}"
            val url = RetrofitClient.URL_PRODUCT + response.gambar_produk
            Picasso.get().load(url).into(imgProduct)

            Log.d("Adapter", "Binding Product: ${response.nama_produk}, ID: ${response.id_produk}")

            productCheckbox.setOnCheckedChangeListener(null)
            productCheckbox.isChecked = response.isChecked

            productCheckbox.setOnCheckedChangeListener { _, isChecked ->
                response.isChecked = isChecked

                val selectedIds = getSelectedProductIds()
                val total = calculateTotalPrice()

                Log.d("Adapter", "Selected IDs: $selectedIds, Total Price: $total")

                onCheckboxChanged(selectedIds.isNotEmpty(), total, selectedIds)
            }
        }
    }

    private fun calculateTotalPrice(): Int {
        return listCart.filter { it.isChecked }.sumOf { it.harga.toInt() }
    }

    private fun getSelectedProductIds(): List<String> {
        return listCart.filter { it.isChecked }.map { it.id_produk }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCart[position], position)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }
}