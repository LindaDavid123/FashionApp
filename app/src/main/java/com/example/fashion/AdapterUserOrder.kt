package com.example.fashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.UserOrderResponse
import com.squareup.picasso.Picasso

class AdapterUserOrder(private val listOrder:ArrayList<UserOrderResponse>): RecyclerView.Adapter<AdapterUserOrder.ViewHolder>() {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val imgProduct: ImageView = v.findViewById(R.id.imgProduct)
        val productName: TextView = v.findViewById(R.id.productName)
        val productSize: TextView = v.findViewById(R.id.productSize)
        val priceProduct: TextView = v.findViewById(R.id.productPrice)

        fun bind(response: UserOrderResponse) {
            productName.text = response.nama_produk
            priceProduct.text = "Rp ${response.harga}"
            productSize.text = "Size: ${response.size}"
            var url = RetrofitClient.URL_PRODUCT + response.gambar_produk
            Picasso.get().load(url).into(imgProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUserOrder.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterUserOrder.ViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }
}