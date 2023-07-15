package com.ichwan.moviecompfest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ichwan.moviecompfest.databinding.FragmentDialogPaymentSuccessBinding

class DialogPaymentSuccessFragment : DialogFragment() {

    private var _binding: FragmentDialogPaymentSuccessBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogPaymentSuccessBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnNoInternet.setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }

}