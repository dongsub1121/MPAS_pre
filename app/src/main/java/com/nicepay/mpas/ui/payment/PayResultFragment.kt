package com.nicepay.mpas.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicepay.mpas.R

class PayResultFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = PayResultFragment()

    }

    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}