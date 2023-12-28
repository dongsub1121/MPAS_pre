package com.nicepay.mpas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicepay.mpas.databinding.FragmentHomeBinding
import com.nicepay.mpas.databinding.FragmentPayMethodBinding
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.ui.payment.PaymentViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding!!.root
    }

}