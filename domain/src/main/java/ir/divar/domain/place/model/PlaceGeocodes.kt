package ir.divar.domain.place.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceGeocodes(val main: LatLng) : Parcelable


@Parcelize
data class LatLng(val latitude: Double, val longitude: Double) : Parcelable