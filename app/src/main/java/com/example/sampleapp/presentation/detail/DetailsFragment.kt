package com.example.sampleapp.presentation.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sampleapp.BuildConfig
import com.example.sampleapp.databinding.FragmentDetailsBinding
import com.example.sampleapp.presentation.detail.adapter.LabsAdapter
import com.example.sampleapp.utils.Utils
import io.github.muddz.quickshot.QuickShot
import java.io.File

class DetailsFragment : Fragment(), QuickShot.QuickShotListener {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        setUpViews()

        return binding.root
    }

    private fun setUpViews() {


        binding.apply {
            diseaseTv.text = args.medicine.disease
            medicineTv.text = args.medicine.medications[args.position].name
            dosageAmountTv.text = args.medicine.medications[args.position].dose
            strengthTv.text = args.medicine.medications[args.position].strength
        }

        args
        val adapter = LabsAdapter()
        binding.labsRv.adapter = adapter


        adapter.submitList(args.medicine.labs)


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shareIv.setOnClickListener {
            binding.shareIv.visibility = View.INVISIBLE
            binding.downloadIv.visibility = View.INVISIBLE
            binding.backBtn.visibility = View.INVISIBLE
            val imageFile = Utils.takeScreenShot(requireView())
            shareScreenShot(imageFile)
            binding.backBtn.visibility = View.VISIBLE
            binding.shareIv.visibility = View.VISIBLE
            binding.downloadIv.visibility = View.VISIBLE
        }

        binding.downloadIv.setOnClickListener {
            binding.shareIv.visibility = View.INVISIBLE
            binding.downloadIv.visibility = View.INVISIBLE
            binding.backBtn.visibility = View.INVISIBLE
            QuickShot.of(requireView()).setResultListener(this).save()
            binding.backBtn.visibility = View.VISIBLE
            binding.shareIv.visibility = View.VISIBLE
            binding.downloadIv.visibility = View.VISIBLE
        }
    }

    override fun onQuickShotSuccess(path: String?) {
        Toast.makeText(requireContext(), "Details has been Save at $path", Toast.LENGTH_LONG).show()
    }

    override fun onQuickShotFailed(path: String?, errorMsg: String?) {
        Toast.makeText(
            requireContext(),
            "$errorMsg, while storing image at $path",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun shareScreenShot(imageFile: File) {

        //Using sub-class of Content provider
        val uri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            imageFile
        )

        //Explicit intent
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_TEXT, "This is your medicine details for sharing")
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        //It will show the application which are available to share Image; else Toast message will Shown.
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No App Available", Toast.LENGTH_SHORT).show()
        }
    }

}