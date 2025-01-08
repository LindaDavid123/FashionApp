package com.example.fashion

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView10: ImageView
    private lateinit var imageView11: ImageView
    private lateinit var buttonCategories: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
//        imageView3 = findViewById(R.id.imageView3)
//        imageView4 = findViewById(R.id.imageView4)
//        imageView6 = findViewById(R.id.imageView6)
//        imageView7 = findViewById(R.id.imageView7)
//        imageView10 = findViewById(R.id.imageView10)
//        imageView11 = findViewById(R.id.imageView11)
//        buttonCategories = findViewById(R.id.button)
//
//        // Set click listeners
//        buttonCategories.setOnClickListener {
//            showToast("Categories clicked!")
//        }
//
//        imageView10.setOnClickListener {
//            showToast("Women's category clicked!")
//        }
//
//        imageView7.setOnClickListener {
//            showToast("Notification clicked!")
//        }
    }

    // Utility function to show a toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
