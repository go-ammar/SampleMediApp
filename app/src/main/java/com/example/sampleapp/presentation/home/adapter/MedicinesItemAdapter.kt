package com.example.sampleapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.R
import com.example.sampleapp.base.BaseAdapter
import com.example.sampleapp.data.remote.medicine.AssociatedDrug
import com.example.sampleapp.databinding.ItemMedicineChildBinding

class MedicinesItemAdapter(
    private val onClickListener: (medicine: Int) -> Unit
) :
    BaseAdapter<AssociatedDrug>(
        diffCallback = object : DiffUtil.ItemCallback<AssociatedDrug>() {

            override fun areItemsTheSame(
                oldItem: AssociatedDrug,
                newItem: AssociatedDrug
            ): Boolean {
                return false
//                return oldItem.disease == newItem.disease
            }

            override fun areContentsTheSame(
                oldItem: AssociatedDrug,
                newItem: AssociatedDrug
            ): Boolean {
                return oldItem == newItem
            }

        }), Filterable {


    override fun getItemCount(): Int {
        return filteredTransactionList.size
    }

    var transactionList: ArrayList<AssociatedDrug> = ArrayList()
    var filteredTransactionList: ArrayList<AssociatedDrug> = ArrayList()

    override fun submitList(list: List<AssociatedDrug>?) {
        super.submitList(list)
        transactionList = list as ArrayList<AssociatedDrug>
        filteredTransactionList = transactionList
        notifyDataSetChanged()
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_medicine_child,
            parent,
            false
        )

    }

    override fun bind(binding: ViewDataBinding, item: AssociatedDrug, position: Int) {
        when (binding) {
            is ItemMedicineChildBinding -> {

                binding.doseTv.text = item.dose
                binding.nameTv.text = item.name
                binding.strengthTv.text = item.strength

                binding.item.setOnClickListener {
                    onClickListener.invoke(position)
                }

                if (position == 0) {
                    binding.view.visibility = View.GONE
                }

            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filteredList = ArrayList<AssociatedDrug>()
                val charString =
                    p0?.toString() ?: ""
                if (charString == "")
                    filteredList.addAll(transactionList)
                else {

                    transactionList.filter {
                        (it.name!!.contains(
                            p0!!,
                            ignoreCase = true
                        ))
                    }.forEach { filteredList.add(it) }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTransactionList =
                    if (p1?.values == null)
                        ArrayList()
                    else
                        p1.values as ArrayList<AssociatedDrug>

                notifyDataSetChanged()
            }
        }
    }


}
