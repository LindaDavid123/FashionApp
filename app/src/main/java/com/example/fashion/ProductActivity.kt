package com.example.fashion

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.ProdukResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductActivity : AppCompatActivity() {
    private val listProduk = ArrayList<ProdukResponse>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        val back: ImageView = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val judulProduct: TextView = findViewById(R.id.titleProduct)

        val category = intent.getStringExtra("category")
        Log.e("category", "${category}")

        val rootLayout: LinearLayout = findViewById(R.id.background)

        val drawable = rootLayout.background as GradientDrawable

        val startColor = ContextCompat.getColor(this, R.color.start_color)
        val endColor = when (category?.lowercase()) {
            "men" -> ContextCompat.getColor(this, R.color.men_end_color)
            "kids" -> ContextCompat.getColor(this, R.color.kids_end_color)
            else -> ContextCompat.getColor(this, R.color.original_end_color)
        }

        drawable.setColors(intArrayOf(startColor, endColor))

        val RVQuiz: RecyclerView = findViewById(R.id.recyclerViewProduk)
        RVQuiz.layoutManager = GridLayoutManager(this@ProductActivity, 2)

        if (category != null) {
            val formattedCategory = category.replaceFirstChar { it.uppercaseChar() }
            judulProduct.text = "$formattedCategory Products"
            RetrofitClient.instance.getProduk(category).enqueue(
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
                                putExtra("gambar_produk", "${produk.gambar_produk}")
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
}