package ir.divar.data.place.remote.model

import com.google.gson.annotations.SerializedName
import ir.divar.domain.place.model.PlaceCategory
import ir.divar.domain.place.model.PlaceGeocodes
import ir.divar.domain.place.model.PlaceLocation

data class PlaceRemoteModel(
    @SerializedName("fsq_id")
    val id: String,
    val name: String,
    val categories: List<PlaceCategory>,
    val distance: Int,
    val geocodes: PlaceGeocodes,
    val location: PlaceLocation
)