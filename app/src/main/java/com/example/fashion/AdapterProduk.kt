package com.example.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.response.produk.ProdukResponse

class AdapterProduk : AppCompatActivity() {
    class AdapterProduk(private val listProduk:ArrayList<ProdukResponse>): RecyclerView.Adapter<AdapterProduk.ViewHolder>() {
        inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
            val imgProduct: ImageView = v.findViewById(R.id.imageProduct)
            val textProduct: TextView = v.findViewById(R.id.textProductName)
            val textPrice: TextView = v.findViewById(R.id.textProductPrice)

            fun bind(response: ProdukResponse) {
                val name = "${response.nama_produk}"
                val harga = "${response.harga}"
                val image = "${response.gambar_produk}"

                textProduct.text = name
                textPrice.text = harga
                var url = "http://172.20.10.8:80/fashion_api/image_product/" + image
                Picasso.get().load(url).into(imgProduct)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(listProduk[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_layout_produk, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = listProduk.size
    }
}