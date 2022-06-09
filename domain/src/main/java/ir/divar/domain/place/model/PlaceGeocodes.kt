package ir.divar.domain.place.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceGeocodes(val main: PlaceGeocode) : Parcelable


@Parcelize
data class PlaceGeocode(val latitude: Double, val longitude: Double) : Parcelable