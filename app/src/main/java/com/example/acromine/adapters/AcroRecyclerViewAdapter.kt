package com.example.acromine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.acromine.databinding.LayoutDefinitionBinding
import com.example.acromine.datamodels.Longform

class AcroRecyclerViewAdapter(): RecyclerView.Adapter<AcroRecyclerViewAdapter.AcroViewHolder>() {
    var definitions: List<Longform> = ArrayList()
    fun setDefs(defs: List<Longform>) {
        definitions = defs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder {
        val binding = LayoutDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcroViewHolder, position: Int) {
        holder.setViews(definitions[position])
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    inner class AcroViewHolder(private val binding: LayoutDefinitionBinding): RecyclerView.ViewHolder(binding.root) {
        fun setViews(longForm: Longform) {
            binding.tvLongForm.text = longForm.lf
            binding.tvDate.text = longForm.since.toString()
        }
    }
}