package com.example.sprint_modulo5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sprint_modulo5.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), AddToCartListener, OnShoeRemovedListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //navigation controller
        //val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        setContentView(binding.root)
    }

    override fun onAddToCart(shoe: Shoe) {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val shoeJson = gson.toJson(shoe)
        val shoes = sharedPreferences.getStringSet("cart", mutableSetOf()) ?: mutableSetOf()
        shoes.add(shoeJson)
        editor.putStringSet("cart", shoes)
        editor.apply()
    }

    override fun onShoeRemoved(shoe: Shoe) {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Obtener el conjunto actual de zapatos almacenados
        val shoesJsonSet = sharedPreferences.getStringSet("cart", mutableSetOf()) ?: mutableSetOf()

        // Convertir el objeto `Shoe` a su representación JSON
        val gson = Gson()
        val shoeJson = gson.toJson(shoe)

        // Eliminar la representación JSON del zapato del conjunto
        shoesJsonSet.remove(shoeJson)

        // Guardar el nuevo conjunto en SharedPreferences
        editor.putStringSet("cart", shoesJsonSet)
        editor.apply()
    }



}