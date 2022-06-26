package com.example.sampleapp.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.R
import com.example.sampleapp.base.BaseAdapter
import com.example.sampleapp.data.remote.medicine.Labs
import com.example.sampleapp.databinding.ItemLabsBinding

class LabsAdapter :
    BaseAdapter<Labs>(
        diffCallback = object : DiffUtil.ItemCallback<Labs>() {

            override fun areItemsTheSame(
                oldItem: Labs,
                newItem: Labs
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Labs,
                newItem: Labs
            ): Boolean {
                return oldItem == newItem
            }

        }) {


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_labs,
            parent,
            false
        )

    }

    override fun bind(binding: ViewDataBinding, item: Labs, position: Int) {
        when (binding) {
            is ItemLabsBinding -> {

                binding.text.text =item.location

            }
        }
    }


}
