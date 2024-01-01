package com.nicepay.mpas.ui.payment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicepay.mpas.R
import com.nicepay.mpas.databinding.FragmentPayMethodBinding
import com.nicepay.mpas.util.Pay.Status.*
import com.nicepay.mpas.util.Pay.Service.*

class PaymentMethodFragment : Fragment() {

    private var binding: FragmentPayMethodBinding? = null
    private lateinit var viewModel: PaymentViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        binding = FragmentPayMethodBinding.inflate(inflater, container, false)
        val args = Bundle()

        binding?.card1?.setOnClickListener {
            Log.e("Method", "setTransaction")
            viewModel.setTransaction(PAYPRO, AUTH)
            findNavController().navigate(R.id.to_payAmountFragment, args)
        }



        return binding!!.root

    }
}