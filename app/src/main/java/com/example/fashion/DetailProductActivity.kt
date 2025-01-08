package com.example.fashion

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_product)

        val backArrow: ImageView = findViewById(R.id.back_arrow)
        val productImage: ImageView = findViewById(R.id.product_image)
        val productName: TextView = findViewById(R.id.product_name)
        val productPrice: TextView = findViewById(R.id.product_price)
        val productDescription: TextView = findViewById(R.id.product_description)
        val addToCartButton: Button = findViewById(R.id.add_to_cart_button)
        val buyNowButton: Button = findViewById(R.id.buy_now_button)

        val namaProduk = intent.getStringExtra("nama_produk") ?: "No Name"
        val harga = intent.getIntExtra("harga", 0)
        val deskripsi = intent.getStringExtra("deskripsi") ?: "No Description"
        val gambarProduk = intent.getStringExtra("gambar_produk")

        productName.text = namaProduk
        productPrice.text = "$$harga"
        productDescription.text = deskripsi
        if (gambarProduk != null) {
            Picasso.get().load(gambarProduk).into(productImage)
        }

        backArrow.setOnClickListener {
            finish()
        }
    }
}