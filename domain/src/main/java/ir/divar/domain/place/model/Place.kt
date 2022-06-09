package ir.divar.domain.place.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Place(
    val id: String,
    val name: String,
    val categories: String,
    val distance: Int,
    val geocode: PlaceGeocode,
    val location: PlaceLocation
) : Parcelable