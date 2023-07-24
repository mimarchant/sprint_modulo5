package com.example.sprint_modulo5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sprint_modulo5.databinding.ShoeItemBinding

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {
    var shoes = mutableListOf<Shoe>()

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

    class ViewHolder(private val binding: ShoeItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(shoe: Shoe) {
            val truncatedName = shoe.name.truncate(20)
            val priceWithCurrency = "$${shoe.price}"
            binding.shoeName.text = truncatedName
            binding.shoePrice.text = priceWithCurrency
            Glide.with(itemView.context)
                .load(shoe.image)
                .into(binding.shoeImage)
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