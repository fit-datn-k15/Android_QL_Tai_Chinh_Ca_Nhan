package com.example.quanlythuchi.view.main.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.databinding.Example5EventItemViewBinding
import com.example.quanlythuchi.extension.getColorCompat
import com.example.quanlythuchi.extension.layoutInflater

class Example5FlightsAdapter :
    RecyclerView.Adapter<Example5FlightsAdapter.Example5FlightsViewHolder>() {
    val flights = mutableListOf<Flight>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example5FlightsViewHolder {
        return Example5FlightsViewHolder(
            Example5EventItemViewBinding.inflate(parent.context.layoutInflater, parent, false),
        )
    }

    override fun onBindViewHolder(viewHolder: Example5FlightsViewHolder, position: Int) {
        viewHolder.bind(flights[position])
    }

    override fun getItemCount(): Int = flights.size

    inner class Example5FlightsViewHolder(val binding: Example5EventItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flight: Flight) {
            binding.itemFlightDateText.apply {
                text = flightDateTimeFormatter.format(flight.time)
                setBackgroundColor(itemView.context.getColorCompat(flight.color))
            }

            binding.itemDepartureAirportCodeText.text = flight.departure.code
            binding.itemDepartureAirportCityText.text = flight.departure.city

            binding.itemDestinationAirportCodeText.text = flight.destination.code
            binding.itemDestinationAirportCityText.text = flight.destination.city
        }
    }
}