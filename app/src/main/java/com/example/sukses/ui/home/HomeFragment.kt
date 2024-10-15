package com.example.sukses.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sukses.data.response.EventResponse
import com.example.sukses.data.retrofit.ApiConfig
import com.example.sukses.databinding.FragmentHomeBinding
import com.example.sukses.ui.EventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        eventAdapter = EventAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }

        fetchUpcomingEvents()

        return root
    }

    private fun fetchUpcomingEvents() {

        binding.progressBar.visibility = View.VISIBLE

        val client = ApiConfig.getApiService().getActiveEvents(1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {

                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                    eventAdapter.submitList(events)
                } else {

                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
