package org.d3if3008.floraapp.ui.tumbuhan

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3008.floraapp.MainActivity
import org.d3if3008.floraapp.databinding.FragmentTumbuhanBinding
import org.d3if3008.floraapp.network.ApiStatus

class TumbuhanFragment : Fragment() {

    private val viewModel: TumbuhanViewModel by lazy {
        ViewModelProvider(this)[TumbuhanViewModel::class.java]
    }

    private lateinit var binding: FragmentTumbuhanBinding
    private lateinit var myAdapter: TumbuhanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTumbuhanBinding.inflate(layoutInflater, container, false)
        myAdapter = TumbuhanAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) {
            myAdapter.updateData(it)
        }

        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }

        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                Log.d("ApiStatus","cek loading")
                binding.progressBar.visibility = View.VISIBLE
            }

            ApiStatus.SUCCES -> {
                Log.d("ApiStatus","cek berhasil")
                binding.progressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }

            ApiStatus.FAILED -> {
                Log.d("ApiStatus","cek berhasil")
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }
}