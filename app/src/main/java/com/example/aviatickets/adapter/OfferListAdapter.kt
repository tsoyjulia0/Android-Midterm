package com.example.aviatickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ItemOfferBinding
import com.example.aviatickets.model.entity.Offer

class OfferListAdapter : RecyclerView.Adapter<OfferListAdapter.ViewHolder>() {

    private var items: ArrayList<Offer> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Solution for Task 2
     * The replaced method for 'setItems' method
     */
    fun submitList(newList: List<Offer>) {
        val diffResult = DiffUtil.calculateDiff(OfferDiffCallback(items, newList))
        items = ArrayList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Solution for Task 3
     */
    fun sortByPrice() {
        items.sortBy { it.price }
        notifyDataSetChanged()
    }

    fun sortByDuration() {
        items.sortBy { it.flight.duration }
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        private val binding: ItemOfferBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(offer: Offer) {
            val flight = offer.flight

            with(binding) {

                departureTime.text = flight.departureTimeInfo
                arrivalTime.text = flight.arrivalTimeInfo


                val departureCode = flight.departureLocation?.code
                val arrivalCode = flight.arrivalLocation?.code

                route.text = context.getString(
                    R.string.route_fmt,
                    departureCode,
                    arrivalCode
                )

                duration.text = context.getString(
                    R.string.time_fmt,
                    getTimeFormat(flight.duration).first.toString(),
                    getTimeFormat(flight.duration).second.toString()
                )
                direct.text = context.getString(R.string.direct)
                price.text = context.getString(R.string.price_fmt, offer.price.toString())


                Glide.with(context)
                    .load(offer.imageUrl)
                    .placeholder(R.drawable.baseline_connected_tv_24)
                    .into(offerImage)
            }
        }

        private fun getTimeFormat(minutes: Int): Pair<Int, Int> = Pair(
            first = minutes / 60,
            second = minutes % 60
        )
    }


    private class OfferDiffCallback(
        private val oldList: List<Offer>,
        private val newList: List<Offer>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}