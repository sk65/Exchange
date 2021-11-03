package com.yefimoyevhen.exchange.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yefimoyevhen.exchange.R
import com.yefimoyevhen.exchange.databinding.FragmentExchangeListBinding
import com.yefimoyevhen.exchange.model.ExchangeListDTO
import com.yefimoyevhen.exchange.util.COUNTRY_KEY
import com.yefimoyevhen.exchange.util.DataState
import com.yefimoyevhen.exchange.view.adapter.ExchangeAdapter
import com.yefimoyevhen.exchange.viewmodel.ExchangeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeListFragment : Fragment() {

    private var _adapter: ExchangeAdapter? = null
    private val adapter
        get() = _adapter!!

    private var _binding: FragmentExchangeListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var exchangeListViewModel: ExchangeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exchangeListViewModel =
            ViewModelProvider(requireActivity()).get(ExchangeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExchangeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        exchangeListViewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        context,
                        dataState.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DataState.Success<*> -> {
                    hideProgressBar()
                    adapter.differ.submitList(dataState.data as MutableList<ExchangeListDTO>?)
                }
                is DataState.Loading -> showProgressBar()
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        _adapter = ExchangeAdapter().apply {
            onItemClickListener = { openExchangeDetails(it) }
        }
        binding.exchangeList.apply {
            adapter = this@ExchangeListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun openExchangeDetails(countryCode: String) {
        val bundle = Bundle().apply {
            putSerializable(COUNTRY_KEY, countryCode)
        }
        findNavController().navigate(
            R.id.action_exchangeListFragment_to_exchangeDetailsFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        _binding = null
        _adapter = null
        super.onDestroyView()
    }
}