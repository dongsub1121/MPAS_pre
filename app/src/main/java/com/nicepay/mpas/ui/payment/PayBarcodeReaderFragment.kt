package com.nicepay.mpas.ui.payment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.zxing.Result
import com.nicepay.mpas.R
import me.dm7.barcodescanner.zxing.ZXingScannerView


class PayBarcodeReaderFragment : Fragment() , ZXingScannerView.ResultHandler{

    private lateinit var scannerView: ZXingScannerView
    private lateinit var viewModel: PaymentViewModel

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 101
        @JvmStatic
        fun newInstance() = PayBarcodeReaderFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scannerView = ZXingScannerView(requireContext())
        return scannerView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            scannerView.setResultHandler(this)
            scannerView.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        }
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        if (result != null && result.text != null) {
            var barcode = result.text
            viewModel.setBarcode(barcode)
            Toast.makeText(requireContext(), "Scanned Barcode: $barcode", Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_payBarcodeReaderFragment_to_payRoadingFragment)
        }
    }

}