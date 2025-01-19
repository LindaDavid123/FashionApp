package com.example.fashion

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion.client.RetrofitClient
import com.example.fashion.response.produk.UserCartResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cart.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var selectedProductIds = listOf<String>()
    private var totalPrice = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)

        val RVOrder: RecyclerView = view.findViewById(R.id.recyclerViewCart)
        val checkoutSection: View = view.findViewById(R.id.checkout_section)
        val totalPriceText: TextView = view.findViewById(R.id.total_price)
        val checkout: Button = view.findViewById(R.id.checkout_button)

        RVOrder.layoutManager = GridLayoutManager(activity, 1)
        checkoutSection.visibility = View.GONE

        if (userId != null) {
            RetrofitClient.instance.getUserCart(userId).enqueue(
                object : Callback<ArrayList<UserCartResponse>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserCartResponse>>,
                        response: Response<ArrayList<UserCartResponse>>
                    ) {
                        val listCart = response.body() ?: arrayListOf()
                        val adapter = AdapterUserCart(listCart) { isAnyChecked, total, productIds ->
                            selectedProductIds = productIds
                            totalPrice = total

                            if (isAnyChecked) {
                                checkoutSection.visibility = View.VISIBLE
                                val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
                                bottomNav.visibility = View.GONE

                                totalPriceText.text = "Total Price: $$totalPrice"
                            } else {
                                checkoutSection.visibility = View.GONE
                                val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
                                bottomNav.visibility = View.VISIBLE
                            }
                        }
                        RVOrder.adapter = adapter
                    }

                    override fun onFailure(call: Call<ArrayList<UserCartResponse>>, t: Throwable) {
                        Log.e("Cart", "Failed to fetch cart items: ${t.message}")
                    }
                }
            )
        }

        checkout.setOnClickListener {
            if (selectedProductIds.isNotEmpty()) {
                Log.d("Cart", "Selected Product IDs: $selectedProductIds")
                val intent = Intent(requireContext(), detailCheckout::class.java).apply {
                    putStringArrayListExtra("SELECTED_PRODUCT_IDS", ArrayList(selectedProductIds))
                    putExtra("TOTAL_PRICE", totalPrice)
                }
                startActivity(intent)
            } else {
                Log.d("Cart", "No products selected for checkout")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Cart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}