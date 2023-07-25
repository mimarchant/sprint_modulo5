package com.example.sprint_modulo5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sprint_modulo5.databinding.FragmentShoeDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeDetailFragment : Fragment() {
    lateinit var binding : FragmentShoeDetailBinding
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: Int? = null

    private var addToCartListener: AddToCartListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddToCartListener) {
            addToCartListener = context
        } else {
            throw RuntimeException("$context debe implementar AddToCartListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("name")
            param2 = it.getString("description")
            param3 = it.getString("price")
            param4 = it.getString("image")
            param5 = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        binding.shoeDetailName.text = param1
        binding.shoeDetailDescription.text = param2?.truncate(180)
        binding.shoeDetailPrice.text = param3
        Glide.with(binding.root)
            .load(param4)
            .into(binding.shoeDetailImage)

        binding.addToCart.setOnClickListener {
            val id = param5
            val name = param1
            val price = param3?.replace("$", "")?.toInt()
            val description = param2
            val image = param4

            if (id != null && name != null && price != null && description != null && image != null) {
                val shoe = Shoe(id, name, price, description, image)
                addToCartListener?.onAddToCart(shoe)
                Toast.makeText(context, "Item añadido a la lista", Toast.LENGTH_SHORT).show()
            } else {
                // Show an error message or do something else
                Toast.makeText(context, "Error al añadir el item a la lista", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoeDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoeDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun String.truncate(maxLength: Int): String {
        return if (this.length > maxLength) {
            this.substring(0, maxLength) + "..."
        } else {
            this
        }
    }
}