package com.example.productdelivery.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.productdelivery.data.model.Category
import com.example.productdelivery.data.model.Product
import com.example.productdelivery.databinding.ItemCategoryBinding
import com.example.productdelivery.databinding.ItemProductBinding

class ProductsAdapter :
    RecyclerView.Adapter<ProductViewHolder>() {

    private var data = ArrayList<Product>()

 fun setData(data: List<Product>?) {
        this.data = ArrayList()
        if (data != null) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }
}

class ProductViewHolder(val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Product) {
        binding.item = item
    }
}

