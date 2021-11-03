package com.yefimoyevhen.exchange.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.yefimoyevhen.exchange.databinding.FragmentExchangeDetailsBinding
import com.yefimoyevhen.exchange.model.ExchangeDetailsDTO
import com.yefimoyevhen.exchange.util.DataState
import com.yefimoyevhen.exchange.viewmodel.ExchangeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeDetailsFragment : Fragment() {

    private lateinit var viewModel: ExchangeDetailsViewModel
    private var _binding: FragmentExchangeDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val args: ExchangeDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExchangeDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExchangeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeeklySummary(args.countryCode)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    hideProgressBar()
                    Toast.makeText(context, dataState.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    showProgressBar()
                }
                is DataState.Success -> {
                    hideProgressBar()
                    setupBarChart(dataState.data)
                }
            }
        }
    }

    private fun setupBarChart(data: ExchangeDetailsDTO) {
        binding.barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(data.labelsNames)
            position = XAxis.XAxisPosition.BOTTOM
            labelCount = data.labelsNames.size

        }
        binding.barChart.apply {
            animateY(2000)
            this.data = data.barData

        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}