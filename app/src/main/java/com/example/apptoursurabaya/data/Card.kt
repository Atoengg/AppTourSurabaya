package com.example.apptoursurabaya.data

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val id: Int,
    val name: String,
    val desc: String,
    val loc: String,
    val image_url_small: String,
    val latLng: LatLng
) : Parcelable