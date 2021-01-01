package com.example.acromine.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.acromine.datamodels.Longform

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(view: RecyclerView, adapter: AcroRecyclerViewAdapter) {
        view.adapter = adapter
    }
}