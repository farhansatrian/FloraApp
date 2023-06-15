package org.d3if3008.floraapp.ui.tumbuhan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3008.floraapp.databinding.FragmentTumbuhanBinding

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
        binding = FragmentTumbuhanBinding.inflate(layoutInflater,container,false)
        myAdapter = TumbuhanAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context,
            RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }
}