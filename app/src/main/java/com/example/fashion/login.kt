package com.example.fashion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.account.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                postLogin(username, password)
            }
        }
    }

    private fun postLogin(username: String, password: String) {
        RetrofitClient.instance.postLogin(username, password).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@login, "Login Berhasil", Toast.LENGTH_SHORT).show()

                        val userId = loginResponse?.data?.user_id
                        val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user_id", userId)
                        editor.apply()

                        val intent = Intent(this@login, Home::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@login, loginResponse?.message ?: "Login Gagal", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@login, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", "Response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@login, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
                Log.e("LoginError", "Request failed: ${t.message}")
            }
        })
    }
}