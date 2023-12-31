package com.example.sprint_modulo5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sprint_modulo5.databinding.ShoeItemBinding

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {
    var shoes = mutableListOf<Shoe>()
    private var fragment: Fragment? = null
    var onShoeRemovedListener: OnShoeRemovedListener? = null

    var showDeleteButton = false

    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val binding = ShoeItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val shoe = shoes[position]
        holder.bind(shoe)
    }

    override fun getItemCount(): Int {
        return shoes.size
    }

    fun setData(shoeList: List<Shoe>) {
        this.shoes = shoeList.toMutableList()
    }

    inner class ViewHolder(private val binding: ShoeItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(shoe: Shoe) {
            val truncatedName = shoe.name.truncate(20)
            val priceWithCurrency = "$${shoe.price}"
            binding.shoeName.text = truncatedName
            binding.shoePrice.text = priceWithCurrency

            Glide.with(itemView.context)
                .load(shoe.image)
                .into(binding.shoeImage)

            binding.deleteButton.visibility = if (showDeleteButton) View.VISIBLE else View.GONE

            binding.deleteButton.setOnClickListener {
                onShoeRemovedListener?.onShoeRemoved(shoe)
            }

            binding.shoeItemContainer.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name", shoe.name)
                bundle.putString("description", shoe.description)
                bundle.putString("price", priceWithCurrency)
                bundle.putString("image", shoe.image)
                bundle.putInt("id", shoe.id)
                Navigation.findNavController(binding.root).navigate(R.id.action_shoeListFragment2_to_shoeDetailFragment3, bundle)
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



}