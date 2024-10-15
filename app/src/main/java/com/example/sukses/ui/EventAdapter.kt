package com.example.sukses.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sukses.data.response.ListEventsItem
import com.example.sukses.databinding.EventItemBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var eventList = mutableListOf<ListEventsItem>()

    fun submitList(events: List<ListEventsItem>) {
        eventList.clear()
        eventList.addAll(events)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = eventList.size

    inner class EventViewHolder(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.eventName.text = event.name

            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.eventImage)

            itemView.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java)
                intent.putExtra(
                    DetailEventActivity.EXTRA_EVENT_ID,
                    event.id.toString()
                )
                context.startActivity(intent)
            }
        }
    }
}
