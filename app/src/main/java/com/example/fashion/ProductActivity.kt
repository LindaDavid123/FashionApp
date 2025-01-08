package com.example.fashion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.ProdukResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    private val listProduk = ArrayList<ProdukResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        val RVQuiz: RecyclerView = findViewById(R.id.recyclerViewProduk)
        RVQuiz.layoutManager = GridLayoutManager(this@ProductActivity, 2)

        RetrofitClient.instance.getProduk().enqueue(
            object : Callback<ArrayList<ProdukResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<ProdukResponse>>,
                    response: Response<ArrayList<ProdukResponse>>
                ) {
                    listProduk.clear()
                    response.body()?.let {
                        listProduk.addAll(it)
                    }
                    val adapter = AdapterProduk(listProduk) { produk ->
                        val intent = Intent(this@ProductActivity, DetailProductActivity::class.java).apply {
                            putExtra("nama_produk", produk.nama_produk)
                            putExtra("harga", produk.harga)
                            putExtra("deskripsi", produk.deskripsi)
                            putExtra("gambar_produk", "http://192.168.100.221:80/fashion_api/image_product/${produk.gambar_produk}")
                        }
                        startActivity(intent)
                    }
                    RVQuiz.adapter = adapter
                }

                override fun onFailure(call: Call<ArrayList<ProdukResponse>>, t: Throwable) {
                    Log.e("API Error", "Request failed: ${t.message}")
                }
            }
        )
    }
}