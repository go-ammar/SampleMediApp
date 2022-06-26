package com.example.sampleapp.presentation.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sampleapp.MainActivity
import com.example.sampleapp.R
import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.data.remote.medicine.AssociatedDrug
import com.example.sampleapp.data.remote.medicine.Labs
import com.example.sampleapp.data.remote.medicine.Medicines
import com.example.sampleapp.data.remote.medicine.Problems
import com.example.sampleapp.databinding.FragmentHomeBinding
import com.example.sampleapp.presentation.home.adapter.MedicinesAdapter
import com.example.sampleapp.presentation.splash.SplashActivity
import com.example.sampleapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var medicines: Medicines
    private lateinit var adapter: MedicinesAdapter

    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpObservers()
        startTimer()

        binding.heading.headingTitle.text = getString(R.string.home_page)
        return binding.root

    }

    private fun setUpObservers() {

        lifecycleScope.launch {
            viewModel.listApi.collect {
                if (it != null) {
                    binding.emptyState.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    medicines = it

                    var local: MedicineModel
                    val localList = ArrayList<MedicineModel>()

                    for (item in it.problems) {
                        local = MedicineModel(
                            0,
                            item.disease.toString(),
                            item.medications,
                            item.labs
                        )
                        localList.add(local)
                    }

                    viewModel.setLocalMedicines(localList)
                    setAdapter()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getFlow().collect {
                if (it != null) {
                    binding.emptyState.visibility = View.GONE
                    binding.progress.visibility = View.GONE

                    val probList = ArrayList<Problems>()
                    for (item in it) {
                        val prob =
                            Problems(
                                item.disease,
                                ArrayList(item.medicines),
                                ArrayList(item.labs)
                            )
                        probList.add(prob)
                    }
                    medicines = Medicines(probList)
                    setAdapter()
                    adapter.submitList(probList)
                }

            }
        }

        viewModel.listLocal.observe(viewLifecycleOwner) {
            if (it != null) {
                val probList = ArrayList<Problems>()
                for (item in it) {
                    val prob =
                        Problems(
                            item.disease,
                            item.medicines as ArrayList<AssociatedDrug>,
                            item.labs as ArrayList<Labs>
                        )
                    probList.add(prob)
                }

                medicines = Medicines(probList)
            }

        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.progress.visibility = View.GONE
                binding.emptyState.visibility = View.VISIBLE
            }
        }

        binding.logoutImg.setOnClickListener {
            lifecycleScope.launch {
                (activity as MainActivity).preferenceStorage.removeUser()
            }

            val intent = Intent(requireContext(), SplashActivity::class.java)
            intent.putExtra("fromHome", true)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            (activity as MainActivity).finish()
        }

        binding.apply {

            heading.searchImageView.setOnClickListener {
                showSearchBar(true)
            }

            heading.searchCross.setOnClickListener {
                showSearchBar(false)
                heading.tilSearch.setText("")
            }


            heading.tilSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {
                        if (::adapter.isInitialized)
                            adapter.filterSearchResult(it.toString())
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        if (::adapter.isInitialized)
                            adapter.filterSearchResult(it.toString())
                    }
                }

            })

        }
    }

    private fun setAdapter() {

        adapter = MedicinesAdapter(requireContext()) { position, problem ->

            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                position,
                problem
            )

            findNavController().navigate(action)

        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(medicines.problems)

    }

    private fun startTimer() {

        lifecycleScope.launch {
            binding.name = (activity as MainActivity).preferenceStorage.getUserName()
        }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                binding.time = formatted
            }
        }, 0, 1000)


    }

    private fun showSearchBar(show: Boolean) {

        val transition: Transition = Slide(Gravity.END)
        transition.duration = 500
        transition.addTarget(binding.heading.searchLayout)
        TransitionManager.beginDelayedTransition(binding.mainConstraint, transition)
        binding.heading.searchLayout.visibility = if (show) View.VISIBLE else View.GONE

        if (show) {
            binding.heading.tilSearch.requestFocus()
            binding.heading.tilSearch.isEnabled = true

            Utils.showSoftKeyboard(requireActivity(), binding.root)

        } else {
            binding.heading.headingTitle.visibility = View.VISIBLE
            binding.heading.searchImageView.visibility = View.VISIBLE
            Utils.hideSoftKeyboard(requireActivity(), binding.root)
        }

    }

}