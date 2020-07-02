package com.example.productdelivery.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.productdelivery.data.model.Category
import com.example.productdelivery.databinding.ItemCategoryBinding

class CategoriesAdapter(private val clickListener: CategoryCellListener) :
    RecyclerView.Adapter<CategoryCellViewHolder>() {

    private var data = ArrayList<Category>()

    fun setData(data: List<Category>?) {
        this.data = ArrayList()
        if (data != null) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCellViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)

        return CategoryCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryCellViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(clickListener, currentItem)
    }
}

class CategoryCellListener(val clickListener: (id: Long?) -> Unit) {
    fun onClick(id: Long?) = clickListener(id)
}

class CategoryCellViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: CategoryCellListener, item: Category) {
        binding.clickListener = clickListener
        binding.item = item
    }
}

