package com.chow.code_base_sdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chow.code_base_sdk.databinding.CharItemBinding
import com.chow.code_base_sdk.model.domain.DomainCharacter

class BaseAdapter(
    private val dataSet: MutableList<DomainCharacter> = mutableListOf(),
    private val itemClick: (DomainCharacter) -> Unit
) : RecyclerView.Adapter<BaseAdapter.ItemViewHolder>() {

    fun updateChars(newChar: List<DomainCharacter>) {

        val difUtil = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = dataSet.size

            override fun getNewListSize(): Int = newChar.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataSet[oldItemPosition] == newChar[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataSet[oldItemPosition].name == newChar[newItemPosition].name
            }

        }

        val result = DiffUtil.calculateDiff(difUtil)
        dataSet.clear()
        dataSet.addAll(newChar)
        result.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            CharItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClick)
//        val item = dataSet[position]
//        holder.itemView.setOnClickListener {
//            itemClick(item)
//        }

    }

    class ItemViewHolder(
        private val binding: CharItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DomainCharacter, clickItem: (DomainCharacter) -> Unit) {
            binding.charName.text = item.name

            itemView.setOnClickListener {
                clickItem(item)
            }
        }
    }
}
