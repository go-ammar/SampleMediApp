package com.example.sampleapp.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.R
import com.example.sampleapp.base.BaseAdapter
import com.example.sampleapp.data.remote.medicine.Problems
import com.example.sampleapp.databinding.ItemMedicinesBinding

class MedicinesAdapter(
    private val mContext: Context,
    private val onClickListener: (position: Int, item: Problems) -> Unit
) :
    BaseAdapter<Problems>(
        diffCallback = object : DiffUtil.ItemCallback<Problems>() {

            override fun areItemsTheSame(
                oldItem: Problems,
                newItem: Problems
            ): Boolean {
                return oldItem.disease == newItem.disease
            }

            override fun areContentsTheSame(
                oldItem: Problems,
                newItem: Problems
            ): Boolean {
                return oldItem == newItem
            }

        }) {

    var query: String = ""


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_medicines,
            parent,
            false
        )

    }

    override fun bind(binding: ViewDataBinding, item: Problems, position: Int) {
        when (binding) {
            is ItemMedicinesBinding -> {

                binding.diseaseTv.text = item.disease


                val childAdapter = MedicinesItemAdapter {
                       onClickListener.invoke(it, item)
                }



                binding.childRv.adapter = childAdapter
                childAdapter.submitList(item.medications)

                if (!query.isNullOrEmpty())
                    childAdapter.filter.filter(query)
            }
        }
    }

    fun filterSearchResult(query: String) {
        this.query = query
        notifyDataSetChanged()
    }


}
