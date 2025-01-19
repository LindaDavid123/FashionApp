package com.example.fashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.ProdukResponse
import com.squareup.picasso.Picasso

class AdapterProdukCheckout(private val listProdukCO:ArrayList<ProdukResponse>): RecyclerView.Adapter<AdapterProdukCheckout.ViewHolder>() {
    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val imgProduct: ImageView = v.findViewById(R.id.imageCO)
        val textProduct: TextView = v.findViewById(R.id.nameCO)
        val sizeProduct: TextView = v.findViewById(R.id.sizeCO)

        fun bind(response: ProdukResponse) {
            val name = "${response.nama_produk}"
            val image = "${response.gambar_produk}"

            textProduct.text = name
            sizeProduct.text = response.size
            var url = RetrofitClient.URL_PRODUCT + image
            Picasso.get().load(url).into(imgProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkout_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProdukCO.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listProdukCO[position])
    }
    fun updateData(newData: ArrayList<ProdukResponse>) {
        listProdukCO.clear()
        listProdukCO.addAll(newData)
        notifyDataSetChanged()
    }
}