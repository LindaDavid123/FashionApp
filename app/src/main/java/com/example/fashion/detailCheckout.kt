package com.example.fashion

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.ProdukResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detailCheckout : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduk: AdapterProdukCheckout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_checkout)

        // Ambil data dari intent
        val totalPrice = intent.getIntExtra("TOTAL_PRICE", 0)
        val selectedProductIds = intent.getStringArrayListExtra("SELECTED_PRODUCT_IDS") ?: arrayListOf()

        // Tampilkan subtotal harga
        findViewById<TextView>(R.id.subtotalAmount).text = "Rp$totalPrice"

        // Inisialisasi RecyclerView dan adapter dengan data kosong
        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        adapterProduk = AdapterProdukCheckout(arrayListOf()) // Mulai dengan data kosong
        recyclerView.adapter = adapterProduk

        // Jika ada ID produk yang dipilih, lakukan request API
        if (selectedProductIds.isNotEmpty()) {
            val idsQuery = selectedProductIds.joinToString(",") // Gabungkan ID dengan koma
            fetchProdukByIds(idsQuery)
        } else {
            Log.d("detailCheckout", "Tidak ada produk yang dipilih.")
        }
    }

    private fun fetchProdukByIds(idsQuery: String) {
        RetrofitClient.instance.getProdukByIds(idsQuery).enqueue(object : Callback<ArrayList<ProdukResponse>> {
            override fun onResponse(
                call: Call<ArrayList<ProdukResponse>>,
                response: Response<ArrayList<ProdukResponse>>
            ) {
                if (response.isSuccessful) {
                    val products = response.body() ?: arrayListOf()
                    adapterProduk.updateData(products) // Perbarui data adapter dengan produk baru
                } else {
                    Log.e("API", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ProdukResponse>>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
            }
        })
    }
}