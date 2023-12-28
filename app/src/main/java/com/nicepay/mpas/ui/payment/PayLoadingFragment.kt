package com.nicepay.mpas.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicepay.mpas.R
import com.nicepay.mpas.databinding.FragmentPayLoadingBinding
import com.nicepay.mpas.ui.home.HomeViewModel

class PayLoadingFragment : Fragment() {

    private var binding:FragmentPayLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayLoadingBinding.inflate(inflater,container,false)
        val viewModel = ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
        Log.e("loading","viewModel.authorization")
        viewModel.authorization()

        findNavController().navigate(R.id.action_payRoadingFragment_to_payResultFragment)

        //Log.e("98955555555555", lo.msg)

        return binding!!.root
    }

}