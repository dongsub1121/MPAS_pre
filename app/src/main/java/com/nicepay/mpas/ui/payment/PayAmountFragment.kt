package com.nicepay.mpas.ui.payment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicepay.mpas.R
import com.nicepay.mpas.databinding.FragmentPayAmountBinding
import com.nicepay.mpas.model.Order


class PayAmountFragment : Fragment() {

    private lateinit var viewModel: PaymentViewModel
    private var binding: FragmentPayAmountBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = arguments

        // Bundle에서 뷰모델을 가져옵니다.
        viewModel =ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
        binding = FragmentPayAmountBinding.inflate(inflater, container, false)

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val amount = binding?.textAmount?.text

        binding?.nextAmount?.setOnClickListener {
            if (amount.isNullOrEmpty() ) {
                Toast.makeText(requireContext(),"금액을 입력해 주세요",Toast.LENGTH_SHORT).show()
            } else if (amount.toString().toInt() > 0){
                Log.e("amount", "$amount")
                val intAmount = amount.toString().toInt()
                viewModel.setPrice(intAmount)
                val order = Order()
                order.price = intAmount
                viewModel.setOrder(order)
                findNavController().navigate(R.id.action_payAmountFragment_to_payBarcodeReaderFragment)
            } else {
                Toast.makeText(requireContext(),"0원 입니다",Toast.LENGTH_SHORT).show()
            }

        }
    }

}