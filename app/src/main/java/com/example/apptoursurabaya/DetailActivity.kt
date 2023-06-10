package com.example.apptoursurabaya

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.apptoursurabaya.data.Card
import com.example.apptoursurabaya.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cardData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_CARD, Card::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_CARD)
        }

        if (cardData != null) {
            setupCard(cardData)
            setupMap(savedInstanceState, cardData.latLng)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupCard(data: Card) {
        with(binding) {
            tvNamaWisata.text = data.name
            tvDesc.text = data.desc
            tvLokasi.text = data.loc
            Glide.with(this@DetailActivity)
                .load(data.image_url_small)
                .into(imgCard)
        }
    }
    private fun setupMap(savedInstanceState: Bundle?, latLng: LatLng) {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            onMapReadyCallback(map, latLng)
        }

    }

    private fun onMapReadyCallback(map: GoogleMap?, latLng: LatLng) {
        googleMap = map
        googleMap?.apply {
            addMarker(MarkerOptions().position(latLng).title("Marker"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_CARD = "EXTRA_CARD"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val DEFAULT_ZOOM = 15f
    }
}


