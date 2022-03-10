package com.example.demo.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.RvTestItemBinding

class SecondRecyclerAdapter(
    private val selectListener: OnItemSelectListener
) : RecyclerView.Adapter<SecondRecyclerAdapter.SampleViewHolder>() {

    private val itemList = mutableListOf<String>()

    interface OnItemSelectListener {
        fun select(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvTestItemBinding.inflate(inflater, parent, false)
        binding.listener = selectListener
        return SampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(item: List<String>) {
        itemList.run {
            clear()
            addAll(item)
            notifyDataSetChanged()
        }
    }

    class SampleViewHolder(val binding: RvTestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.sampleText = text
            binding.executePendingBindings()
        }
    }
}