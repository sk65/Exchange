package com.yefimoyevhen.exchange.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yefimoyevhen.exchange.R
import com.yefimoyevhen.exchange.databinding.ItemExchangePreviewBinding
import com.yefimoyevhen.exchange.model.ExchangeListDTO
import com.yefimoyevhen.exchange.util.round


class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.ExchangeViewHolder>() {
    inner class ExchangeViewHolder(val binding: ItemExchangePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ExchangeListDTO>() {
        override fun areItemsTheSame(oldItem: ExchangeListDTO, newItem: ExchangeListDTO): Boolean {
            return oldItem.baseCountryCode == newItem.baseCountryCode
        }

        override fun areContentsTheSame(
            oldItem: ExchangeListDTO,
            newItem: ExchangeListDTO
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExchangeViewHolder(
            ItemExchangePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        val entry = differ.currentList[position]
        holder.binding.apply {
            if (position % 2 == 0) {
                root.setBackgroundColor(Color.WHITE)
            } else {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.gray))
            }
            root.setOnClickListener {
                onItemClickListener?.let { it(entry.countryCode) }
            }
            countryCode.text = entry.countryCode
            exchangeRate.text = entry.exchangeRate.round(2).toString()
        }
    }

    override fun getItemCount() = differ.currentList.size
}