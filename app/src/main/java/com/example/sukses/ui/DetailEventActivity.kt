package com.example.sukses.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.sukses.data.response.EventResponse
import com.example.sukses.data.response.ListEventsItem
import com.example.sukses.data.retrofit.ApiConfig
import com.example.sukses.databinding.ActivityDetailEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailEventBinding

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideEventDetails()

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID) ?: return

        fetchEventDetail(eventId)
    }

    private fun fetchEventDetail(eventId: String) {

        binding.progressBar.visibility = View.VISIBLE

        val client = ApiConfig.getApiService().getDetailEvent(eventId)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {

                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val event = response.body()?.event
                    event?.let { showEventDetail(it) }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {

                binding.progressBar.visibility = View.GONE

            }
        })
    }

    private fun showEventDetail(event: ListEventsItem) {

        Glide.with(this)
            .load(event.mediaCover ?: event.imageLogo)
            .into(binding.eventImage)

        binding.tvEventName.text = event.name

        binding.tvOwnerName.text = event.ownerName

        binding.tvBeginTime.text = event.beginTime

        val remainingQuota = (event.quota ?: 0) - (event.registrants ?: 0)
        binding.tvQuota.text = "Sisa Kuota: $remainingQuota"

        binding.tvDescription.text = HtmlCompat.fromHtml(
            event.description ?: "",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.btnOpenLink.setOnClickListener {
            val eventLink = event.link
            if (eventLink != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                startActivity(intent)
            }
        }
        showEventDetails()
    }
    private fun showEventDetails() {
        binding.tvEventName.visibility = View.VISIBLE
        binding.tvOwnerName.visibility = View.VISIBLE
        binding.tvBeginTime.visibility = View.VISIBLE
        binding.tvQuota.visibility = View.VISIBLE
        binding.tvDescription.visibility = View.VISIBLE
        binding.btnOpenLink.visibility = View.VISIBLE
    }

    private fun hideEventDetails() {
        binding.tvEventName.visibility = View.GONE
        binding.tvOwnerName.visibility = View.GONE
        binding.tvBeginTime.visibility = View.GONE
        binding.tvQuota.visibility = View.GONE
        binding.tvDescription.visibility = View.GONE
        binding.btnOpenLink.visibility = View.GONE
    }
}