package com.example.sprint_modulo5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sprint_modulo5.databinding.FragmentShoppingCartBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShoppingCart : Fragment(), OnShoeRemovedListener {
    lateinit var binding: FragmentShoppingCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)

        initAdapter()
        binding.clearitemsButton.setOnClickListener{clearAllFromCart()}

        return binding.root
    }

    private fun getCartItems(): List<Shoe> {
        val sharedPreferences = activity?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val shoesJsonSet = sharedPreferences?.getStringSet("cart", emptySet()) ?: emptySet()
        val shoes = mutableListOf<Shoe>()
        for (shoeJson in shoesJsonSet) {
            val shoe = gson.fromJson(shoeJson, Shoe::class.java)
            shoes.add(shoe)
        }
        return shoes
    }

    private fun initAdapter() {
        val adapter = Adapter()
        adapter.setFragment(this)
        adapter.onShoeRemovedListener = this  // Asegúrate de que ShoppingCart implemente OnShoeRemovedListener
        val shoes = getCartItems()
        adapter.showDeleteButton = true
        adapter.setData(shoes)
        binding.recyclerViewShared.adapter = adapter
    }

    fun clearAllFromCart() {
        val sharedPreferences = activity?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        initAdapter()
        Toast.makeText(context, "Carrito limpiado", Toast.LENGTH_SHORT).show()
    }

    override fun onShoeRemoved(shoe: Shoe) {
        val sharedPreferences = activity?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        // Obtener el conjunto actual de zapatos almacenados
        val shoesJsonSet = sharedPreferences?.getStringSet("cart", mutableSetOf()) ?: mutableSetOf()

        // Convertir el objeto `Shoe` a su representación JSON
        val gson = Gson()
        val shoeJson = gson.toJson(shoe)

        // Eliminar la representación JSON del zapato del conjunto
        shoesJsonSet.remove(shoeJson)

        // Guardar el nuevo conjunto en SharedPreferences
        editor?.putStringSet("cart", shoesJsonSet)
        editor?.apply()

        // Actualizar la lista mostrada
        initAdapter()
    }
}
